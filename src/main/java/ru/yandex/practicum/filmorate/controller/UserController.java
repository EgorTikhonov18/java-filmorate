package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class UserController {
    private Map<Integer, User> users = new HashMap<>();
    private int id;

    @PostMapping(value = "/user")
    public User create(@Valid @RequestBody User user) {
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        user.setId(++id);
        users.put(user.getId(), user);
        log.debug("Получен запрос POST /user.");
        return user;
    }

    @PutMapping(value = "/user")
    public User update(@RequestBody User user) {
        if (users.containsKey(user.getId())) {
            users.replace(user.getId(), user);
        } else {
            user.setId(++id);
            users.put(user.getId(), user);
        }
        log.debug("Получен запрос PUT /user.");
        return user;
    }

    @GetMapping("/users")
    public Map<Integer, User> findAll() {
        log.debug("Текущее количество пользователей: ", users.size());
        return users;
    }
}
