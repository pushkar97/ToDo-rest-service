package com.github.pushkar97.todo.dtos;

import com.github.pushkar97.todo.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.indigo.dtomapper.annotations.Property;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    Long id;

    String name;

    Boolean checked;

}
