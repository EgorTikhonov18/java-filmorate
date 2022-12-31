package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmService {
    public List<Film> getAllFilms();

    public Film getFilmById(Integer filmId);

    public Film addFilm(Film film);

    public Film updateFilm(Film film);

    public void addLike(Integer filmId, Integer userId);

    public void remoteLike(Integer filmId, Integer userId);

    public List<Film> getMostPopular(Integer count);

    public void createFilmId(Film film);

}
