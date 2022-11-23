package com.mihael.libraryapplication.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate date;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<Book> booksWritten = new ArrayList<>();

    /* Hibernate requires empty constructor */
    public Author() {
    }

    public Author(String firstName, String lastName, LocalDate date) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
        this.booksWritten = new ArrayList<>();
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
        if(booksWritten == null){
            return new ArrayList<>();
        }
        else return booksWritten;
    }

    public void setBooksWritten(List<Book> booksWritten) {
        this.booksWritten = booksWritten;
    }
    public void addBooks(Book book) {
        this.booksWritten.add(book);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return firstName.equals(author.firstName) && lastName.equals(author.lastName) && Objects.equals(date, author.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, date);
    }

    public void removeBook(Book book) {
        this.booksWritten.remove(book);
    }
}
