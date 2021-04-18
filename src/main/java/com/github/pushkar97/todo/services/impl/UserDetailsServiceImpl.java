package com.github.pushkar97.todo.services.impl;

import com.github.pushkar97.todo.errors.EntityNotFoundException;
import com.github.pushkar97.todo.models.User;
import com.github.pushkar97.todo.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl(UserRepository userRepository,
                                  PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s).orElseThrow(() -> new EntityNotFoundException(User.class, "email", s));
        LOGGER.debug(user.toString());
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRole().name())
                .accountExpired(user.isExpired())
                .accountLocked(user.isLocked())
                .credentialsExpired(false)
                .disabled(user.isDisabled())
                .build();
    }
}
