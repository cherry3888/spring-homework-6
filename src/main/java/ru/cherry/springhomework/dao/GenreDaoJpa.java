package ru.cherry.springhomework.dao;

import org.springframework.stereotype.Component;
import ru.cherry.springhomework.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Component
public class GenreDaoJpa implements GenreDao {
    @PersistenceContext
    private final EntityManager em;

    public GenreDaoJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Genre save(Genre genre) {
        if (null == genre.getId()) {
            em.persist(genre);
            return genre;
        }
        return em.merge(genre);
    }

    @Override
    public void delete(Genre genre) {
        em.remove(genre);
    }

    @Override
    public Optional<Genre> getById(Long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public List<Genre> getByName(String name) {
        TypedQuery<Genre> query = em.createQuery("select a from Genre a where a.name =: name", Genre.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public List<Genre> getAll() {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g", Genre.class);
        return query.getResultList();
    }

}
