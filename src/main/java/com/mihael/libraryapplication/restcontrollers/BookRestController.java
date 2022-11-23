package com.mihael.libraryapplication.restcontrollers;

import com.mihael.libraryapplication.entity.Author;
import com.mihael.libraryapplication.entity.Book;
import com.mihael.libraryapplication.representations.BookCollectionRepresentation;
import com.mihael.libraryapplication.representations.BookRepresentation;
import com.mihael.libraryapplication.representations.ClientErrorInformation;
import com.mihael.libraryapplication.services.AuthorService;
import com.mihael.libraryapplication.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class BookRestController {
    private final BookService bookService;

    @Autowired
    public BookRestController(BookService bookService, AuthorService authorService){
        this.bookService = bookService;
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ClientErrorInformation> rulesWhenGenreIsInvalid(Exception e){
        ClientErrorInformation error = new ClientErrorInformation(e.toString());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
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

    @PostMapping("books")
    public BookRepresentation createNewBook(@RequestBody Book book){
        this.bookService.createNewBook(book);
        BookRepresentation bookRepresentation = new BookRepresentation(this.bookService.findBookByTitle(book.getTitle()));
        Link link = linkTo(methodOn(BookRestController.class).findBookById(bookRepresentation.getId())).withSelfRel();
        bookRepresentation.add(link);
        return bookRepresentation;
    }

    @PutMapping("/books/{id}")
    public BookRepresentation updateBook(@RequestBody Book book, @PathVariable Long id){
        book.setId(id);
        this.bookService.updateBook(book);
        BookRepresentation bookRepresentation = new BookRepresentation(this.bookService.findBookById(id));
        Link link = linkTo(methodOn(BookRestController.class).findBookById(bookRepresentation.getId())).withSelfRel();
        bookRepresentation.add(link);
        return bookRepresentation;
    }
    @PutMapping("/books/{id}/addAuthor")
    public BookRepresentation addAuthorToBook(@RequestBody Author author, @PathVariable Long id){
        Book book = this.bookService.findBookById(id);
        this.bookService.addAuthorToBook(book,author);
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
}
