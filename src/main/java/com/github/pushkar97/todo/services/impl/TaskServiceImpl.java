package com.github.pushkar97.todo.services.impl;

import com.github.pushkar97.todo.errors.EntityNotFoundException;
import com.github.pushkar97.todo.models.Task;
import com.github.pushkar97.todo.models.User;
import com.github.pushkar97.todo.repositories.TaskRepository;
import com.github.pushkar97.todo.repositories.UserRepository;
import com.github.pushkar97.todo.services.ITaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements ITaskService {

    TaskRepository taskRepository;
    UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository,
                           UserRepository userRepository){
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<Task> getTasks(){
        return taskRepository.findAll();
    }

    public List<Task> getTasksByUser(String username){
        return taskRepository.findAllByUser(userRepository.findByEmail(username).orElseThrow(() -> new EntityNotFoundException(User.class,"email",username)));
    }

    public Optional<Task> getById(Long id){
        return taskRepository.findById(id);
    }

    public Task save(Task task){
        if(task.getChecked() == null) task.setChecked(false);
        return taskRepository.save(task);
    }

    public Task update(Task task){
        if(getById(task.getId()).isEmpty()) task.setId(0L);
        return taskRepository.save(task);
    }

    public void delete(Long id){
        var taskToDelete = this.getById(id);
        taskRepository.delete(taskToDelete.orElseThrow(() -> new RuntimeException("Task not found. id=" + id)));
    }

    public Task toggleCheck(Long id) {
        var task = taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Task.class,"id",id.toString()));
        task.setChecked(!task.getChecked());
        return taskRepository.save(task);
    }
}
