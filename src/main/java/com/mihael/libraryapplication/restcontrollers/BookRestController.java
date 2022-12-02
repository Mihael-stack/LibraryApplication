package com.mihael.libraryapplication.restcontrollers;

import com.mihael.libraryapplication.entity.Author;
import com.mihael.libraryapplication.entity.Book;
import com.mihael.libraryapplication.representations.BookCollectionRepresentation;
import com.mihael.libraryapplication.representations.BookRepresentation;
import com.mihael.libraryapplication.representations.ClientErrorInformation;
import com.mihael.libraryapplication.services.BookService;
import com.mihael.libraryapplication.tasks.AuthorsWithMostBooks;
import com.mihael.libraryapplication.tasks.AuthorsWithMostBooksSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class BookRestController {
    private final BookService bookService;
    private final AuthorsWithMostBooksSQL task;

    @Autowired
    public BookRestController(BookService bookService, AuthorsWithMostBooksSQL task){
        this.bookService = bookService;
        this.task = task;
    }
    @ExceptionHandler({HttpMessageNotReadableException.class, DataIntegrityViolationException.class})
    public ResponseEntity<ClientErrorInformation> rulesWhenGenreIsInvalidOrDuplicateBook(Exception e){
        ClientErrorInformation error = new ClientErrorInformation(e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(IOException.class)
    public ResponseEntity<ClientErrorInformation> rulesWhenTaskIsHavingAnException(Exception e){
        ClientErrorInformation error = new ClientErrorInformation(e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @GetMapping("/books/{id}")
    public BookRepresentation findBookById(@PathVariable Long id){
        BookRepresentation book = new BookRepresentation(this.bookService.findBookById(id));
        Link link = linkTo(methodOn(BookRestController.class).findBookById(id)).withSelfRel();
        book.add(link);
        return book;
    }
    @GetMapping("/books")
    public BookCollectionRepresentation getAllBooks(){
        BookCollectionRepresentation books = new BookCollectionRepresentation(this.bookService.getAllBooks());
        for(BookRepresentation book : books.getBooks()){
            Link link = linkTo(methodOn(BookRestController.class).findBookById(book.getId())).withSelfRel();
            book.add(link);
        }
        return books;
    }

    @PostMapping("/books")
    public BookRepresentation createNewBook(@RequestBody @Valid Book book){
        this.bookService.createNewBook(book);
        BookRepresentation bookRepresentation = new BookRepresentation(this.bookService.findBookByTitle(book.getTitle()));
        Link link = linkTo(methodOn(BookRestController.class).findBookById(bookRepresentation.getId())).withSelfRel();
        bookRepresentation.add(link);
        return bookRepresentation;
    }

    @PutMapping("/books/{id}")
    public BookRepresentation updateBook(@RequestBody @Valid Book book, @PathVariable Long id){
        book.setId(id);
        this.bookService.updateBook(book);
        BookRepresentation bookRepresentation = new BookRepresentation(this.bookService.findBookById(id));
        Link link = linkTo(methodOn(BookRestController.class).findBookById(bookRepresentation.getId())).withSelfRel();
        bookRepresentation.add(link);
        return bookRepresentation;
    }
    @PutMapping("/books/{id}/addAuthor")
    public BookRepresentation addAuthorToBook(@RequestBody @Valid Author author, @PathVariable Long id){
        Book book = this.bookService.findBookById(id);
        this.bookService.addAuthorToBook(book,author);
        BookRepresentation bookRepresentation = new BookRepresentation(this.bookService.findBookById(id));
        Link link = linkTo(methodOn(BookRestController.class).findBookById(bookRepresentation.getId())).withSelfRel();
        bookRepresentation.add(link);
        return bookRepresentation;
    }
    @PutMapping("/books/{id}/addAuthor/{idAuthor}")
    public BookRepresentation addAuthorToBook(@PathVariable Long id, @PathVariable Long idAuthor){
        this.bookService.addExistingAuthorToBook(id,idAuthor);
        BookRepresentation bookRepresentation = new BookRepresentation(this.bookService.findBookById(id));
        Link link = linkTo(methodOn(BookRestController.class).findBookById(bookRepresentation.getId())).withSelfRel();
        bookRepresentation.add(link);
        return bookRepresentation;
    }

    @DeleteMapping("/books/{id}")
    public Link removeBook(@PathVariable Long id){
        this.bookService.removeBook(this.bookService.findBookById(id));
        return linkTo(methodOn(BookRestController.class).getAllBooks()).withRel("books");
    }
    @GetMapping("/task")
    public void task() throws IOException {
        task.authorsWithMostBooks();
    }
}
