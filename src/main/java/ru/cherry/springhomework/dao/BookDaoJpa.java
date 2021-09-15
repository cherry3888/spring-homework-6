package ru.cherry.springhomework.dao;

import org.springframework.stereotype.Repository;
import ru.cherry.springhomework.domain.Author;
import ru.cherry.springhomework.domain.Book;
import ru.cherry.springhomework.domain.Genre;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class BookDaoJpa implements BookDao {

    @PersistenceContext
    private final EntityManager em;

    public BookDaoJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Book save(Book book) {
        if (null == book.getId()) {
            em.persist(book);
            return book;
        }
        return em.merge(book);
    }

    @Override
    public void delete(Book book) {
        em.createQuery("delete from Book b where b.id = :id")
                .setParameter("id", book.getId())
                .executeUpdate();
    }

    @Override
    public Optional<Book> getById(Long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> getByTitle(String title) {
        TypedQuery<Book> query = em.createQuery("select b from Book b where b.title =: title", Book.class);
        query.setParameter("title", title);
        query.setHint("javax.persistence.fetchgraph", em.getEntityGraph("author_genre_entity_graph"));
        return query.getResultList();
    }

    @Override
    public List<Book> getByTitleAndAuthorIdAndGenreId(String title, Long authorId, Long genreId) {
        TypedQuery<Book> query = em.createQuery("select b from Book b left join b.author a " +
                "left join b.genre g where b.title =: title and a.id =: authorId and g.id =: genreId", Book.class);
        query.setParameter("title", title);
        query.setParameter("authorId", authorId);
        query.setParameter("genreId", genreId);
        query.setHint("javax.persistence.fetchgraph", em.getEntityGraph("author_genre_entity_graph"));
        return query.getResultList();
    }

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        query.setHint("javax.persistence.fetchgraph", em.getEntityGraph("author_genre_entity_graph"));
        return query.getResultList();
    }

}
