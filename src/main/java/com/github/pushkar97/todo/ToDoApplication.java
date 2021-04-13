package com.github.pushkar97.todo;

import com.github.pushkar97.todo.models.Task;
import com.github.pushkar97.todo.models.User;
import com.github.pushkar97.todo.repositories.ITaskRepository;
import com.github.pushkar97.todo.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ToDoApplication implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(ToDoApplication.class);

	ITaskRepository taskRepository;
	UserRepository userRepository;
	PasswordEncoder passwordEncoder;

	public ToDoApplication(ITaskRepository taskRepository,
						   UserRepository userRepository,
						   PasswordEncoder passwordEncoder){
		this.taskRepository = taskRepository;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public static void main(String[] args) {
		SpringApplication.run(ToDoApplication.class, args);
	}

	@Override
	public void run(String... args) {
//		LOGGER.info("Preloading ->");
//		User admin = userRepository.save(new User(null, "admin@test.com", passwordEncoder.encode("password"), User.Role.ADMIN,null));
//		User user = userRepository.save(new User(null, "user@test.com", passwordEncoder.encode("password"), User.Role.USER, null));
//
//		taskRepository.save(new Task(null, "Buy Groceries", true, user));
//		taskRepository.save(new Task(null, "Style application", true, admin));
//		taskRepository.save(new Task(null, "Implement security", true, admin));
//		taskRepository.save(new Task(null, "Have fun", false, user));
//		LOGGER.info("Preloading completed ->");
	}
}
