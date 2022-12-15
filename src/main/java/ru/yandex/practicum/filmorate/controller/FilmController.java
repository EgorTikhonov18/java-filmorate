package ru.yandex.practicum.filmorate.controller;


import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmReleaseException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j

public class FilmController {
    private final FilmService filmService;
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
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

    //GET /films/popular?count={count} — возвращает список из первых count фильмов по количеству лайков. Если значение параметра count не задано, верните первые 10.
    @GetMapping("/films/popular")
        public List<Film> findPopularFilms(@RequestParam(value = "count", defaultValue = "10", required = false) Integer count) {
            log.info("возвращает список из первых count фильмов по количеству лайков. Если значение параметра count не задано, верните первые 10.");
            return filmService.findPopularFilms(count);
        }
}
