package ru.yandex.practicum.filmorate.controller;


import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@NonNull
@Valid
@NotEmpty
@NotBlank
public class UserController {
    private Map<Integer, User> users = new HashMap<>();
    private int id = 1;

    @PostMapping(value = "/users")
    public User create(@Valid  @NonNull @NotEmpty @RequestBody User user) {

        if (user.getId() == 0) {
            user.setId(id);
            id++;
        }
        if (user.getName() == null){
            user.setName(user.getLogin());
        }

        if (!users.containsKey(user.getId()) ) {
            users.put(user.getId(), user);
            log.info("User {} added successfully", user.getName());

            return user;
        } else {
            log.info("This User is already on the list");
            throw new ValidationException(user.getName());

        }
    }


    @PutMapping(value = "/users")
    public User update(@Valid @NonNull @NotEmpty @NotBlank @RequestBody User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.info("User {} update successfully", user.getName());
            return user;
        }
        log.info("This User is not already on the list");

         throw new ValidationException(user.getName() + " not exists");

    }

    @GetMapping(value = "/users")

    public Collection<User> findAll() {
        log.debug("Текущее количество пользователей: {}", users.size());
        return users.values();
    }

}
