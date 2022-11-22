package com.mihael.libraryapplication.services;

import com.mihael.libraryapplication.entity.Author;

import java.time.LocalDate;
import java.util.List;

public interface AuthorService {
    void createNewAuthor(Author author);
    Author findAuthorById(Long id);
    List<Author> getAllAuthors();
    Author findAuthor(String firstName, String lastName, LocalDate date);
    Author updateAuthor(Author author);

}
