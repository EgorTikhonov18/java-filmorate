package ru.yandex.practicum.filmorate.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

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

    @GetMapping(value = "/films")
    public List<Film> findAll() {
        log.info("Получен запрос GET /films. Текущее количество фильмов: {}", films.size());
        return new ArrayList<>(films.values());
    }

    @PostMapping(value = "/films")
    public Film create(@Valid @RequestBody Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(FIRST_FILM_RELEASE)) {
            throw new ValidationException("Неверная дата релиза");
        }
        film.setId(++id);
        films.put(film.getId(), film);
        log.info("Получен запрос POST /films. Фильм {} добавлен.", film.getName());
        return film;
    }

    @PutMapping(value = "/films")
    public Film update(@RequestBody Film film) throws Exception {
        if (!films.containsKey(film.getId())) {
            throw new Exception("Фильма с таким id не существует.");
        }
        films.put(film.getId(), film);
        log.info("Получен запрос PUT /films. Фильм {} обновлен.", film.getName());
        return film;
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
