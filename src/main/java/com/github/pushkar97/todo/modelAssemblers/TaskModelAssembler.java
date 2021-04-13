package com.github.pushkar97.todo.modelAssemblers;

import com.github.pushkar97.todo.controllers.TasksController;
import com.github.pushkar97.todo.dtos.TaskDto;
import com.github.pushkar97.todo.models.Task;
import org.indigo.dtomapper.providers.specification.Mapper;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TaskModelAssembler implements RepresentationModelAssembler<Task, EntityModel<TaskDto>> {

    Mapper mapper;
    public TaskModelAssembler(Mapper mapper){
        this.mapper = mapper;
    }
    @Override
    public EntityModel<TaskDto> toModel(Task entity) {
        return EntityModel.of(mapper.map(entity,TaskDto.class),
                linkTo(methodOn(TasksController.class).one(entity.getId(), null)).withSelfRel(),
                linkTo(methodOn(TasksController.class).all(null)).withRel("tasks"),
                linkTo(methodOn(TasksController.class).toggleChecked(entity.getId(),null)).withRel("toggleChecked")
                );
    }


}
