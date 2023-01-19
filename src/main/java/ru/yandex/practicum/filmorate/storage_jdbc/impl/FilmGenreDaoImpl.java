package ru.yandex.practicum.filmorate.storage_jdbc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage_jdbc.FilmGenreDao;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class FilmGenreDaoImpl implements FilmGenreDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmGenreDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Set<Genre> findAllByFilmId(Integer id) {
        String sql = "select * from FILMS_GENRES G join GENRES G2 on G2.GENRE_ID = G.GENRE_ID " +
                "where film_id = ? order by 1";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, id);
        return mappingGenre(rs);
    }

    @Override
    public void addNewGenreToFilm(Integer filmId, Genre genre) {
        String sql = " insert into FILMS_GENRES(FILM_ID, GENRE_ID) values  (?, ?)";
        jdbcTemplate.update(sql, filmId, genre.getId());
    }

    @Override
    public void updateAllGenreByFilm(Film film) {
        if (film.getGenres() != null) {
            if (film.getGenres().isEmpty()) {
                deleteAll(film);
                return;
            }
            // перед обновлением  удаляем устаревшие данные
            deleteAll(film);

            StringBuilder sb = new StringBuilder("insert into FILMS_GENRES(GENRE_ID, FILM_ID) values ");

            film.setGenres(film.getGenres().stream().distinct().collect(Collectors.toList()));

            for (Genre genre : film.getGenres()) {
                sb.append("(" + genre.getId() + ",")
                        .append(film.getId() + "),");
            }
            String sql = sb.subSequence(0, sb.length() - 1).toString();
            jdbcTemplate.update(sql);
        }
    }

    private void deleteAll(Film film) {
        final String sql = "delete from FILMS_GENRES where  FILM_ID = ?";
        jdbcTemplate.update(sql, film.getId());
    }

    private Set<Genre> mappingGenre(SqlRowSet rs) {
        Set<Genre> genres = new HashSet<>();
        while (rs.next()) {
            genres.add(new Genre(rs.getInt("GENRE_ID"),
                    rs.getString("NAME")));
        }
        return genres;
    }
}