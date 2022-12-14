package ru.yandex.practicum.filmorate.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmReleaseException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@Validated
public class FilmController {
    private static final LocalDate FIRST_FILM_RELEASE = LocalDate.of(1895, 12, 18);
    private final Map<Integer, Film> films = new HashMap<>();
    private int id;
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/films")
    public List<Film> findAll() {
        log.info("Получен запрос GET /films.");
        return filmService.findAll();
    }

    @PostMapping(value = "/films")
    public Film create(@Valid @RequestBody Film film) throws FilmReleaseException {
        log.info("Получен запрос POST /films. Фильм {} добавлен.", film.getName());
        return filmService.create(film);
    }

    @PutMapping(value = "/films")
    public Film update(@RequestBody Film film) {
        log.info("Получен запрос PUT /films. Фильм {} обновлен.", film.getName());
        return filmService.update(film);
    }

    @GetMapping("/films/{id}")
    public Film getFilm(@PathVariable int id) {
        log.info("Найдем пользователя по id = " + id + ".");
        return filmService.getFilm(id);
    }

    // PUT /films/{id}/like/{userId} — пользователь ставит лайк фильму.
    @PutMapping("/films/{id}/like/{userId}")
    public void putLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Пользователь ставит лайк фильму.");
        filmService.putLike(id, userId);
    }

    //DELETE /films/{id}/like/{userId} — пользователь удаляет лайк.
    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Пользователь удаляет лайк.");
        filmService.deleteLike(id, userId);
    }

}
