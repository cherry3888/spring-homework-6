package ru.cherry.springhomework;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.cherry.springhomework.dao.GenreDaoJpa;
import ru.cherry.springhomework.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(GenreDaoJpa.class)
class GenreDaoTest {

	@Autowired
	private GenreDaoJpa genreDao;
    @Autowired
    private TestEntityManager em;

	@Test
	@DirtiesContext
	void getAllGenres() {
		List<Genre> genres = genreDao.getAll();
		assertEquals(3, genres.size());
	}

	@Test
	@DirtiesContext
	void insertGenre() {
		Genre genre = new Genre(4L,"Genre-4");
		genreDao.save(genre);
		Genre genre1 = em.find(Genre.class, 4L);
		assertNotNull(genre1);
	}

	@Test
	@DirtiesContext
	void updateGenre() {
		Optional<Genre> genreO = genreDao.getById(3L);
		Genre genre = null;
		if (genreO.isPresent()) {
			genre = genreO.get();
			genre.setName("Genre-123");
			genreDao.save(genre);
		}
		Genre genre1 = em.find(Genre.class, 3L);
		assertNotNull(genre);
		assertEquals(genre1.getName(), genre.getName());
	}

	@Test
	@DirtiesContext
	void deleteGenre() {
		Genre genre = em.find(Genre.class, 1L);
		genreDao.delete(genre);
		Genre genre1 = em.find(Genre.class, 1L);
		assertNull(genre1);
	}

	@Test
	@DirtiesContext
	void getById() {
		Optional<Genre> genreO = genreDao.getById(2L);
		Genre genre = null;
		if (genreO.isPresent()) {
			genre = genreO.get();
		}
		assertNotNull(genre);
	}

	@Test
	@DirtiesContext
	void getByName() {
		List<Genre> genres = genreDao.getByName("Genre-1");
		assertTrue(genres.size() >0);
	}

}