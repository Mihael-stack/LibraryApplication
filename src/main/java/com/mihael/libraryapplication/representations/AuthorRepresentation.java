package com.mihael.libraryapplication.representations;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mihael.libraryapplication.entity.Author;
import com.mihael.libraryapplication.entity.Book;
import org.springframework.hateoas.RepresentationModel;

import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@XmlRootElement(name = "author")
public class AuthorRepresentation extends RepresentationModel {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate date;
    @JsonIgnore
    private List<Book> booksWritten;

    public AuthorRepresentation() {
    }

    public AuthorRepresentation(Author author) {
        this.booksWritten = new ArrayList<>();
        this.id = author.getId();
        this.firstName = author.getFirstName();
        this.lastName = author.getLastName();
        this.date = author.getDate();
        this.booksWritten = author.getBooksWritten();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Book> getBooksWritten() {
        return booksWritten;
    }

    public void setBooksWritten(List<Book> booksWritten) {
        this.booksWritten = booksWritten;
    }
}
