package ru.cherry.springhomework.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cherry.springhomework.dao.BookDao;
import ru.cherry.springhomework.dao.CommentDao;
import ru.cherry.springhomework.domain.Book;
import ru.cherry.springhomework.domain.Comment;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentDao commentDao;
    private final BookDao bookDao;

    public CommentServiceImpl(CommentDao commentDao, BookDao bookDao) {
        this.commentDao = commentDao;
        this.bookDao = bookDao;
    }

    @Transactional
    @Override
    public Comment save(Long bookId, String content) {
        Optional<Book> bookO = bookDao.getById(bookId);
        if (bookO.isPresent()) {
            Comment comment = new Comment(bookO.get(), content);
            return commentDao.save(comment);
        } else {
            return null;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getByBookId(Long id) {
        return commentDao.getByBookId(id);
    }
}
