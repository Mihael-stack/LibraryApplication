package com.mihael.libraryapplication.representations;

import com.mihael.libraryapplication.entity.Book;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "books")
public class BookCollectionRepresentation {
    private List<BookRepresentation> books;

    public BookCollectionRepresentation() {
    }

    public BookCollectionRepresentation(List<Book> books) {
        this.books = new ArrayList<>();
        for(Book book : books){
            this.books.add(new BookRepresentation(book));
        }
    }

    public List<BookRepresentation> getBooks() {
        return books;
    }

    public void setBooks(List<BookRepresentation> books) {
        this.books = books;
    }
}
