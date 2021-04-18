package com.github.pushkar97.todo.repositories;

import com.github.pushkar97.todo.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Page<User> findByUsernameContains(String username, Pageable pageable);
    Page<User> findAllByUsername(String username, Pageable pageable);
    Boolean existsByUsername(String username);
}
