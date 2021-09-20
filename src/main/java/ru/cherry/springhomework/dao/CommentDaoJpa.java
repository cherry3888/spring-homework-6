package ru.cherry.springhomework.dao;

import org.springframework.stereotype.Component;
import ru.cherry.springhomework.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class CommentDaoJpa implements CommentDao{
    @PersistenceContext
    private final EntityManager em;

    public CommentDaoJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Comment save(Comment comment) {
        if (null == comment.getId()) {
            em.persist(comment);
            return comment;
        }
        return em.merge(comment);
    }

    @Override
    public List<Comment> getByBookId(Long bookId) {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c left join c.book b where b.id =: bookId", Comment.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }
}
