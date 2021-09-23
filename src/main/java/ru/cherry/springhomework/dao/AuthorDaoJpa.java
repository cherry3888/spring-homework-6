package ru.cherry.springhomework.dao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.cherry.springhomework.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Component
public class AuthorDaoJpa implements AuthorDao {

    @PersistenceContext
    private final EntityManager em;

    public AuthorDaoJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Author save(Author author) {
        if (null == author.getId()) {
            em.persist(author);
            return author;
        }
        return em.merge(author);
    }

    @Override
    public void delete(Author author) {
        em.remove(author);
    }

    @Override
    public Optional<Author> getById(Long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public List<Author> getByName(String name) {
        TypedQuery<Author> query = em.createQuery("select a from Author a where a.name =: name", Author.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public List<Author> getAll() {
        TypedQuery<Author> query = em.createQuery("select a from Author a", Author.class);
        return query.getResultList();
    }

}
