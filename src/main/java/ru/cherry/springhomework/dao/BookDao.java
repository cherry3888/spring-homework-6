package ru.cherry.springhomework.dao;

import ru.cherry.springhomework.domain.Author;
import ru.cherry.springhomework.domain.Book;
import ru.cherry.springhomework.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    Book save(Book Book);

    void delete(Book book);

    Optional<Book> getById(Long id);

    List<Book> getByTitle(String title);

    List<Book> getByTitleAndAuthorIdAndGenreId(String title, Long authorId, Long genreId);

    List<Book> getAll();
}
