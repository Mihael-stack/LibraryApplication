package com.mihael.libraryapplication.services;

import com.mihael.libraryapplication.dao.author.AuthorRepository;
import com.mihael.libraryapplication.dao.book.BookRepository;
import com.mihael.libraryapplication.entity.Author;
import com.mihael.libraryapplication.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookServiceProduction implements BookService{
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookServiceProduction(BookRepository bookRepository, AuthorRepository authorRepository){
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public Book findBookById(Long id) {
        return this.bookRepository.findBookById(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }

    @Override
    public void createNewBook(Book book){
        List<Author> authors = book.getAuthors();
        for(Author author : authors){
            author.addBooks(book);
        }
        book.setAuthors(authors);
        this.bookRepository.save(book);
    }

    @Override
    public Book findBookByTitle(String title) {
        return this.bookRepository.findBookByTitle(title);
    }

    @Override
    public Book updateBook(Book book) {
        return this.bookRepository.save(book);
    }
    public Book addAuthorToBook(Book book, Author author){
        book.addAuthor(author);
        author.addBooks(book);
        return this.bookRepository.save(book);
    }

    @Override
    public Book addExistingAuthorToBook(Long idBook, Long idAuthor) {
        Author author = this.authorRepository.findAuthorById(idAuthor);
        Book book = this.bookRepository.findBookById(idBook);
        book.addAuthor(author);
        author.addBooks(book);
        return book;
    }

    @Override
    public void removeBook(Book book) {
        for(Author author : book.getAuthors()){
            author.removeBook(book);
        }
        this.bookRepository.delete(book);
    }

}
