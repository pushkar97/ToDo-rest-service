package com.github.pushkar97.todo.services;

import com.github.pushkar97.todo.models.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ITaskService {

    List<Task> getTasks();

    List<Task> getTasksByUser(String username);

    Optional<Task> getById(Long id);

    Task save(Task task);

    Task update(Task task);

    void delete(Long id);

    Task toggleCheck(Long id);
}
