package ru.yandex.practicum.filmorate.service.film;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.InMemoryUserService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.List;


@Slf4j
@Service
public class InMemoryFilmService implements FilmService {

    private final FilmStorage storage;
    private final InMemoryUserService userService;
    private Integer id = 0;

    @Autowired
    public InMemoryFilmService(FilmStorage storage, InMemoryUserService userService) {
        this.storage = storage;
        this.userService = userService;

    }

    public List<Film> getAllFilms() {
        log.debug("Получен запрос GET /films.");
        return storage.getAllFilms();
    }

    public Film getFilmById(Integer filmId) {
        return storage.findById(filmId).orElseThrow(() ->
                new FilmNotFoundException("Фильм с id " + filmId + " не найден.") );
    }

    public Film addFilm(Film film) {

        createFilmId(film);
        log.debug("Получен запрос POST. Передан обьект {}", film);
        return storage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        getFilmById(film.getId());
        return storage.updateFilm(film);

    }

    public void addLike(Integer filmId, Integer userId) {
        Film film = getFilmById(filmId);
        User user = userService.getUserById(userId);
        film.addLike(userId);
        log.info("Пользователь: {} поставил лайк фильму: {}", user, film);
    }

    public void remoteLike(Integer filmId, Integer userId) {
        Film film = getFilmById(filmId);
        User user = userService.getUserById(userId);
        film.remoteLike(userId);
        log.info("Пользователь: {} убрал лайк с фильма: {}", user, film);
    }

    public List<Film> getMostPopular(Integer count) {
        log.debug("Отправлен список популярных фильмов с параметром {}", count);
        return storage.getMostPopular(count);
    }

    public void createFilmId(Film film) {
        id++;
        film.setId(id);
    }

}