package com.github.pushkar97.todo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
public class User {

    public enum Role {USER, ADMIN, USER_MANAGER}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    @Email
    private String username;

//    @JsonIgnore
    @ToString.Exclude
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @ToString.Exclude
    boolean expired;

    @ToString.Exclude
    boolean locked;

    @ToString.Exclude
    boolean disabled;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @ToString.Exclude
    private Collection<Task> tasks;
}