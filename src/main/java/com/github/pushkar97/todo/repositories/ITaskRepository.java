package com.github.pushkar97.todo.repositories;


import com.github.pushkar97.todo.models.Task;
import com.github.pushkar97.todo.models.User;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface ITaskRepository extends Repository<Task,Long> {

    void delete(Task deleted);

    List<Task> findAll();

    List<Task> findAllByUser(User user);

    Optional<Task> findById(Long id);

    Task save(Task persisted);
}
