package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.FilmReleaseException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmorateApplicationTest {
    FilmController filmController;
    UserController userController;
    Film film;
    User user;
    FilmService filmService;
    UserService userService;

    @Test
    void checkFilmValidation() throws ru.yandex.practicum.filmorate.exception.FilmReleaseException {
        filmController = new FilmController(filmService);
        film =  Film.builder()
                .name("Film 1")
                .description("Film 1 description")
                .releaseDate(LocalDate.of(2022,11,1))
                .duration(23)
                .build();
        filmController.create(film);
        assertEquals(1, filmController.findAll().size());
    }

    @Test
    void checkFilmReleaseDate() {
        filmController = new FilmController(filmService);
        film =  Film.builder()
                .name("name")
                .description("Film 1 description")
                .releaseDate(LocalDate.of(1322,11,1))
                .duration(23)
                .build();
       FilmReleaseException exc = assertThrows(
               FilmReleaseException.class, () -> filmController.create(film)
        );
        Assertions.assertEquals("Incorrect release date.", exc.getMessage());
    }

    @Test
    void checkUserValidation() {
        userController = new UserController(userService);
        user =  User.builder()
                .email("email@mail.ru")
                .login("login")
                .name("name")
                .birthday(LocalDate.of(2022,11,1))
                .build();
        userController.create(user);
        assertEquals(1, userController.getAllUsers().size());
    }
    @Test
    void contextLoads() {
    }
}