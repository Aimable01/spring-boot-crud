package com.Aimable01.database.dao.impl;

import com.Aimable01.database.TestDataUtil;
import com.Aimable01.database.dao.AuthorDao;
import com.Aimable01.database.dao.Impl.BookDaoImpl;
import com.Aimable01.database.domain.Author;
import com.Aimable01.database.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookDaoImpIntegrationTest {

    private AuthorDao authorDao;
    private BookDaoImpl underTest;

    @Autowired
    public BookDaoImpIntegrationTest(BookDaoImpl underTest, AuthorDao authorDao) {
        this.underTest = underTest;
        this.authorDao = authorDao;
    }

    @Test
    public void testThatBookCanBeCreatedAndRecalled(){

        Author author = TestDataUtil.createTestAuthorA();
        authorDao.create(author);

        Book book  = TestDataUtil.createTestBookA();
        underTest.create(book);
        Optional<Book> result = underTest.findOne(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test void testThatMultipleBooksCanBeCreatedAndRecalled(){
        Author author = TestDataUtil.createTestAuthorA();
        authorDao.create(author);

        Book bookA  = TestDataUtil.createTestBookA();
        bookA.setAuthorId(author.getId());
        underTest.create(bookA);

        Book bookB = TestDataUtil.createTestBookB();
        bookB.setAuthorId(author.getId());
        underTest.create(bookB);

        Book bookC = TestDataUtil.createTestBookC();
        bookC.setAuthorId(author.getId());
        underTest.create(bookC);

        List<Book> result =  underTest.find();
        assertThat(result).hasSize(3).containsExactly(bookA, bookB, bookC);
    }

    @Test
    public void testThatBookCanBeUpdated(){
        Author author  = TestDataUtil.createTestAuthorA();
        authorDao.create(author);

        Book bookA  = TestDataUtil.createTestBookA();
        bookA.setAuthorId(author.getId());
        underTest.create(bookA);

        bookA.setTitle("UPDATED");
        underTest.update(bookA.getIsbn(),bookA);

        Optional<Book> result = underTest.findOne(bookA.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookA);
    }

    @Test
    public void testThatBookCanBeDeleted(){
        Author author  = TestDataUtil.createTestAuthorA();
        authorDao.create(author);

        Book bookA  = TestDataUtil.createTestBookA();
        bookA.setAuthorId(author.getId());
        underTest.create(bookA);

        underTest.delete(bookA.getIsbn());

        Optional<Book> result = underTest.findOne(bookA.getIsbn());
        assertThat(result).isEmpty();
    }
}
