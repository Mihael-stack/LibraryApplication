package com.mihael.libraryapplication.dao.author;

import com.mihael.libraryapplication.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long>{
    Author findAuthorById(Long id);
    Author findAuthorByFirstNameAndLastNameAndDate(String firstName, String lastName, LocalDate date);
}
