package com.github.pushkar97.todo.controllers;

import com.github.pushkar97.todo.dtos.TaskDto;
import com.github.pushkar97.todo.errors.EntityNotFoundException;
import com.github.pushkar97.todo.modelAssemblers.TaskModelAssembler;
import com.github.pushkar97.todo.models.Task;
import com.github.pushkar97.todo.models.User;
import com.github.pushkar97.todo.repositories.UserRepository;
import com.github.pushkar97.todo.services.ITaskService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequestMapping("/api/tasks")
@RestController
public class TasksController {

    ITaskService taskService;
    UserRepository userRepository;
    TaskModelAssembler taskModelAssembler;


    public TasksController(ITaskService taskService,
                           UserRepository userRepository,
                           TaskModelAssembler taskModelAssembler) {
        this.taskService = taskService;
        this.userRepository = userRepository;
        this.taskModelAssembler = taskModelAssembler;
    }

    @GetMapping(path = "/", produces = "application/json")
    public ResponseEntity<CollectionModel<EntityModel<TaskDto>>> all(Authentication authentication) {
        List<Task> tasksToReturn = taskService.getTasksByUser(authentication.getPrincipal().toString());
        return ResponseEntity.ok()
                .body(taskModelAssembler.toCollectionModel(tasksToReturn)
                        .add(linkTo(methodOn(TasksController.class).all(null)).withSelfRel())
                );
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    @PostAuthorize("@taskRepository.findById(#id).get().user.email == authentication.principal")
    public ResponseEntity<EntityModel<TaskDto>> one(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok()
                .body(
                        taskService.getById(id).map(t -> taskModelAssembler.toModel(t))
                        .orElseThrow(() -> new EntityNotFoundException(Task.class, "id", id.toString()))
                );
    }

    @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<EntityModel<TaskDto>> add(@RequestBody Task task, Authentication authentication) {
        task.setUser(userRepository.findByEmail(authentication.getPrincipal().toString())
                .orElseThrow(() -> new EntityNotFoundException(User.class, "email", authentication.getPrincipal().toString())));
        return ResponseEntity
                .created(URI.create("/tasks/" + task.getId()))
                .body(taskModelAssembler.toModel(taskService.save(task)));
    }

    @PutMapping(path = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<EntityModel<TaskDto>> update(@RequestBody Task task, Authentication authentication) {
        Task taskInDB = taskService.getById(task.getId())
                .orElseThrow(() -> new EntityNotFoundException(Task.class, "id", task.getId().toString()));
        if(taskInDB.getUser().getEmail().equals(authentication.getPrincipal())) {
            return ResponseEntity.ok()
                    .body(taskModelAssembler.toModel(taskService.update(task)));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping(path = "/{id}", produces = "application/json")
    @PreAuthorize("authentication.getPrincipal() == @taskRepository.findById(#id).get().user.email")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id, Authentication authentication) {
            taskService.delete(id);
            return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/{id}/toggleCheck")
    public ResponseEntity<EntityModel<TaskDto>> toggleChecked(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok()
                .body(taskModelAssembler.toModel(taskService.toggleCheck(id)));
    }
}
