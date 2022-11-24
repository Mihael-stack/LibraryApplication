package com.mihael.libraryapplication.services;

import com.mihael.libraryapplication.entity.Author;
import com.mihael.libraryapplication.entity.Book;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface BookService {
    Book findBookById(Long id);
    List<Book> getAllBooks();
    void createNewBook(Book book);
//    void createNewBook(Book book, List<Author> authors);
    Book findBookByTitle(String title);
    Book updateBook(Book book);
    Book addAuthorToBook(Book book, Author author);
    Book addExistingAuthorToBook(Long idBook, Long idAuthor);
//    Book removeAuthorFromBook(Book book, Author author);
    void removeBook(Book book);

}
