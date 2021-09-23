package ru.cherry.springhomework.dao;

import ru.cherry.springhomework.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {

    Genre save(Genre genre);

    void delete(Genre genre);

    Optional<Genre> getById(Long id);

    List<Genre> getByName(String name);

    List<Genre> getAll();

}
