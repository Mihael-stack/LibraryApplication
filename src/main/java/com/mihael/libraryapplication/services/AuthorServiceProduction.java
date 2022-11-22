package com.mihael.libraryapplication.services;

import com.mihael.libraryapplication.dao.author.AuthorRepository;
import com.mihael.libraryapplication.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional
@Service
public class AuthorServiceProduction implements AuthorService{
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceProduction(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void createNewAuthor(Author author) {
        this.authorRepository.save(author);
    }
    @Override
    public Author findAuthorById(Long id) {
        return this.authorRepository.findAuthorById(id);
    }

    @Override
    public List<Author> getAllAuthors() {
        return this.authorRepository.findAll();
    }

    @Override
    public Author findAuthor(String firstName, String lastName, LocalDate date) {
        return this.authorRepository.findAuthorByFirstNameAndLastNameAndDate(firstName,lastName,date);
    }

    @Override
    public Author updateAuthor(Author author) {
        return this.authorRepository.save(author);
    }
}
