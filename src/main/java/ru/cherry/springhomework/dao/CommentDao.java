package ru.cherry.springhomework.dao;

import ru.cherry.springhomework.domain.Comment;
import java.util.List;

public interface CommentDao {
    Comment save(Comment comment);
    List<Comment> getByBookId(Long bookId);
}
