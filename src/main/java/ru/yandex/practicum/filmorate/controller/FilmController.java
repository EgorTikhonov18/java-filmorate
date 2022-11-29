package ru.yandex.practicum.filmorate.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class FilmController {
    private static final LocalDate FIRST_FILM_RELEASE = LocalDate.of(1895, 12, 18);
    private Map<Integer, Film> films = new HashMap<>();
    private int id;



    @GetMapping("/films")
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
        log.info("Получен запрос POST /films. Фильм  добавлен.", film.getName());
        return film;
    }

    @PutMapping(value = "/films")
    public Film update(@RequestBody Film film) throws Exception {
        if (!films.containsKey(film.getId())) {
            throw new Exception("Фильма с таким id не существует.");
        }
        films.put(film.getId(), film);
        log.info("Получен запрос PUT /films. Фильм  обновлен.", film.getName());
        return film;
    }
}