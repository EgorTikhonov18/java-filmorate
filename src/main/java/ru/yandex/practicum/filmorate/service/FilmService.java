package ru.yandex.practicum.filmorate.service;


import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.FilmReleaseException;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@Service
public class FilmService {

    private final FilmStorage filmStorage;

    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public List<Film> findAll() {
        return filmStorage.findAll();
    }
    public Film getFilm(int id) {
        return filmStorage.getFilm(id);
    }
    public Film create(Film film) throws FilmReleaseException {
        return filmStorage.create(film);
    }

    public Film update(Film film) throws FilmNotFoundException {
        return filmStorage.update(film);
    }

    public Film putLike(int id, int userId) {
        return filmStorage.putLike(id, userId);
    }

    public Film deleteLike(int id, int userId) {
        return filmStorage.deleteLike(id, userId);
    }

    public List<Film> findPopularFilms(int count) {
        return filmStorage.findPopularFilms(count);
    }
}