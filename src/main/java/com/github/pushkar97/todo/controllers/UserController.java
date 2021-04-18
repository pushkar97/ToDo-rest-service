package com.github.pushkar97.todo.controllers;

import com.github.pushkar97.todo.dtos.UserDto;
import com.github.pushkar97.todo.models.User;
import org.indigo.dtomapper.providers.specification.Mapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class UserController {

    PasswordEncoder passwordEncoder;
    Mapper mapper;

    public UserController(PasswordEncoder passwordEncoder,
                          Mapper mapper){
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }
    @PostMapping("/signup")
    public void signUp(UserDto userDto) {
        var user = mapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setDisabled(true);

    }

}
