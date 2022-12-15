package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.exception.FilmReleaseException;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;


import javax.validation.Valid;
import java.util.List;

public interface FilmStorage {
    List<Film> findAll();

    Film create(@Valid Film film) throws FilmReleaseException;

    Film update(@Valid Film film) throws FilmNotFoundException;

    Film putLike(@Valid int id, int userId);

    Film deleteLike(@Valid int id, int userId);

    Film getFilm(int id);

    List<Film> findPopularFilms(@Valid int count);
}