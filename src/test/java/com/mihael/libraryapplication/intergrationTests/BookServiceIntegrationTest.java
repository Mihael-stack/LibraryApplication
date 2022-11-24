package com.mihael.libraryapplication.intergrationTests;

import com.mihael.libraryapplication.entity.Book;
import com.mihael.libraryapplication.entity.Genre;
import com.mihael.libraryapplication.services.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class BookServiceIntegrationTest {

    @Autowired
    private BookService bookService;

    @Test
    void createBookWithAlreadyExistingTitle(){
        // This test fails because Hibernate doesn't try to save the objects before the transaction is ending
        Book book1 = new Book("Staff of Perfection", Genre.FICTION);
        Book book2 = new Book("Chase Without Fear", Genre.NONFICTION);
        Book book3 = new Book("Reads Tweaks", Genre.FICTION);
        Book book4 = new Book("Igniting the World", Genre.NONFICTION);
        assertThrows(DataIntegrityViolationException.class, () -> bookService.createNewBook(book1));
        assertThrows(DataIntegrityViolationException.class, () -> bookService.createNewBook(book2));
        assertThrows(DataIntegrityViolationException.class, () -> bookService.createNewBook(book3));
        assertThrows(DataIntegrityViolationException.class, () -> bookService.createNewBook(book4));
    }
    @Test
    void createNewBookWithoutAuthor(){
        Book book1 = new Book("newBook", Genre.FICTION);
        Book book2 = new Book("newNewBook", Genre.NONFICTION);
        Book book3 = new Book("newNewNewNewBook", Genre.FICTION);
        Book book4 = new Book("oldButNewBook", Genre.NONFICTION);
        List<Book> listBefore = this.bookService.getAllBooks();
        this.bookService.createNewBook(book1);
        this.bookService.createNewBook(book2);
        this.bookService.createNewBook(book3);
        this.bookService.createNewBook(book4);
        assertEquals(this.bookService.getAllBooks().size(), listBefore.size() + 4);
    }

    @Test
    void findExistingBookById(){
        assertEquals("Staff of Perfection",this.bookService.findBookById(1L).toString());
        assertEquals("Chase Without Fear",this.bookService.findBookById(2L).toString());
        assertEquals("Reads Tweaks",this.bookService.findBookById(3L).toString());
        assertEquals("Igniting the World",this.bookService.findBookById(4L).toString());
    }
}
