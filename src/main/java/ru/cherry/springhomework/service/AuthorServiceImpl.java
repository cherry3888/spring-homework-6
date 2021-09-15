package ru.cherry.springhomework.service;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.cherry.springhomework.dao.AuthorDao;
import ru.cherry.springhomework.domain.Author;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;
    private final MessageService messageService;

    public AuthorServiceImpl(AuthorDao authorDao, MessageService messageService) {
        this.authorDao = authorDao;
        this.messageService = messageService;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> getAllAuthors() {
        return authorDao.getAll();
    }

    @Transactional
    @Override
    public Author addAuthor(String name) {
        return authorDao.save(new Author(name));
    }

    @Transactional(readOnly = true)
    @Override
    public Author getAuthor(String name) {
        List<Author> authors = authorDao.getByName(name);
        if (!CollectionUtils.isEmpty(authors)) {
            return authors.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public Author editAuthor(Author author) {
        Optional<Author> authorO = authorDao.getById(author.getId());
        if (authorO.isPresent()) {
            authorDao.save(author);
            return author;
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public boolean deleteAuthor(Long id) {
        Optional<Author> authorO = authorDao.getById(id);
        if (authorO.isPresent()) {
            authorDao.delete(authorO.get());
            return true;
        }
        return false;
    }

}
