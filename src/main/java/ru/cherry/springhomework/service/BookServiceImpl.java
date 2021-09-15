package ru.cherry.springhomework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import ru.cherry.springhomework.dao.AuthorDao;
import ru.cherry.springhomework.dao.BookDao;
import ru.cherry.springhomework.dao.GenreDao;
import ru.cherry.springhomework.domain.Author;
import ru.cherry.springhomework.domain.Book;
import ru.cherry.springhomework.domain.Genre;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAll();
    }

    @Transactional
    @Override
    public Book addBook(String title, String authorName, String genreName) {
        Author author;
        List<Author> authors = authorDao.getByName(authorName);
        if (CollectionUtils.isEmpty(authors)) {
            author = authorDao.save(new Author(authorName));
        } else {
            author = authors.get(0);
        }
        Genre genre;
        List<Genre> genres = genreDao.getByName(genreName);
        if (CollectionUtils.isEmpty(authors)) {
            genre = genreDao.save(new Genre(genreName));
        } else {
            genre = genres.get(0);
        }
        List<Book> books = bookDao.getByTitleAndAuthorIdAndGenreId(title, author.getId(), genre.getId());
        if (CollectionUtils.isEmpty(books)) {
              return bookDao.save(new Book(title, author, genre));
        } else {
            return null;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> findBook(String title) {
        return bookDao.getByTitle(title);
    }

    @Transactional
    @Override
    public Book editBook(Long id, String newTitle) {
        Optional<Book> bookO = bookDao.getById(id);
        if (bookO.isPresent()) {
            Book book = bookO.get();
            book.setTitle(newTitle);
            return bookDao.save(book);
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public boolean deleteBook(Long id) {
        Optional<Book> bookO = bookDao.getById(id);
        if (bookO.isPresent()) {
            bookDao.delete(bookO.get());
            return true;
        } else {
            return false;
        }
    }
}
