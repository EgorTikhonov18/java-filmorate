package ru.yandex.practicum.filmorate.model;


import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
@Builder
public class User {
    private int id;
    @Email
    private String email;
    @NotNull
    @NotBlank
    private String login;
    private String name;
    @PastOrPresent
    private LocalDate birthday;


}