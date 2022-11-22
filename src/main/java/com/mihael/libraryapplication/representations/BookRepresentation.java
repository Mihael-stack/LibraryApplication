package com.mihael.libraryapplication.representations;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mihael.libraryapplication.entity.Author;
import com.mihael.libraryapplication.entity.Book;
import org.springframework.hateoas.RepresentationModel;

import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;


@XmlRootElement(name = "book")
public class BookRepresentation extends RepresentationModel {
    private Long id;
    private String title;
    private AuthorCollectionRepresentation authors;
    private String genre; // 2 genres are possible only ( fiction and nonfiction )

    public BookRepresentation() {
    }
    public BookRepresentation(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.authors = new AuthorCollectionRepresentation(book.getAuthors());
        this.genre = book.getGenre();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AuthorCollectionRepresentation getAuthors() {
        return authors;
    }

    public void setAuthors(AuthorCollectionRepresentation authors) {
        this.authors = authors;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
