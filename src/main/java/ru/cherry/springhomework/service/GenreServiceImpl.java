package ru.cherry.springhomework.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import ru.cherry.springhomework.dao.GenreDao;
import ru.cherry.springhomework.domain.Genre;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreDao genreDao;

    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Transactional
    @Override
    public List<Genre> getAllGenres() {
        return genreDao.getAll();
    }

    @Transactional
    @Override
    public Genre addGenre(String name) {
        return genreDao.save(new Genre(name));
    }

    @Transactional(readOnly = true)
    @Override
    public Genre getGenre(String name) {
        List<Genre> genres = genreDao.getByName(name);
        if (!CollectionUtils.isEmpty(genres)) {
            return genres.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public Genre editGenre(Genre genre) {
        Optional<Genre> genreO = genreDao.getById(genre.getId());
        if (genreO.isPresent()) {
            genreDao.save(genre);
            return genre;
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public boolean deleteGenre(Long id) {
        Optional<Genre> genreO = genreDao.getById(id);
        if (genreO.isPresent()) {
            genreDao.delete(genreO.get());
            return true;
        }
        return false;
    }

}
