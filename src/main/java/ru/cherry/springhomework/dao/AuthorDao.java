package ru.cherry.springhomework.dao;

import ru.cherry.springhomework.domain.Author;
import java.util.List;
import java.util.Optional;

public interface AuthorDao {

    Author save(Author author);

    void delete(Author author);

    Optional<Author> getById(Long id);

    List<Author> getByName(String name);

    List<Author> getAll();
}
