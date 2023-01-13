package ru.yandex.practicum.filmorate.storage_jdbc;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.Optional;

public interface MpaDao {
    Optional<Mpa> getMpaById(Integer id);

    List<Mpa> getAllMpa();
}
