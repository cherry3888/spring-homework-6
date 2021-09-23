package ru.cherry.springhomework;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.cherry.springhomework.dao.BookDao;
import ru.cherry.springhomework.dao.BookDaoJpa;
import ru.cherry.springhomework.domain.Author;
import ru.cherry.springhomework.domain.Book;
import ru.cherry.springhomework.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(BookDaoJpa.class)
class BookDaoTest {

    @Autowired
    private BookDao bookDao;
    @Autowired
    private TestEntityManager em;


	@Test
	@DirtiesContext
	void getAllBooks() {
		List<Book> books = bookDao.getAll();
		assertEquals(3, books.size());
	}

	@Test
	@DirtiesContext
	void insertBook() {
        Author author = em.find(Author.class, 1L);
        Genre genre = em.find(Genre.class, 1L);
		Book book = new Book("Book-4", author, genre);
		bookDao.save(book);
		List<Book> books = bookDao.getByTitle(book.getTitle());
		assertTrue(books.size() == 1);
	}

	@Test
	@DirtiesContext
	void updateBook() {
		Book book = em.find(Book.class, 1L);
		String newTitle = "Book-new";
		book.setTitle(newTitle);
		bookDao.save(book);
        Book book1 = em.find(Book.class, 1L);
        assertEquals(newTitle, book1.getTitle());
	}

	@Test
	@DirtiesContext
	void deleteBook() {
        Book book = em.find(Book.class, 1L);
		bookDao.delete(book);
        Book book1 = em.find(Book.class, 1L);
		assertNull(book1);
	}

	@Test
	@DirtiesContext
	void getById() {
		Optional<Book> bookO = bookDao.getById(2L);
		Book book = null;
		if (bookO.isPresent()) {
		    book = bookO.get();
        }
		assertNotNull(book);
	}

	@Test
	@DirtiesContext
	void getByTitle() {
		List<Book> books = bookDao.getByTitle("Book-2");
        assertEquals(books.size(), 1);
	}

}