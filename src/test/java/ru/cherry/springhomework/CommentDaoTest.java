package ru.cherry.springhomework;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.cherry.springhomework.dao.CommentDaoJpa;
import ru.cherry.springhomework.domain.Book;
import ru.cherry.springhomework.domain.Comment;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(CommentDaoJpa.class)
class CommentDaoTest {

	@Autowired
	private CommentDaoJpa commentDao;
    @Autowired
    private TestEntityManager em;

	@Test
	@DirtiesContext
	void insertComment() {
		Book book = em.find(Book.class, 1L);
		Comment comment = new Comment(book,"Comment-1");
		comment = commentDao.save(comment);
		Comment comment1 = em.find(Comment.class, comment.getId());
		assertNotNull(comment1);
	}

	@Test
	@DirtiesContext
	void getById() {
		List<Comment> comments = commentDao.getByBookId(1L);
		assertEquals(2, comments.size());
	}

}