package com.mihael.libraryapplication.dao.book;

import com.mihael.libraryapplication.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findBookById(Long id);
    Book findBookByTitle(String title);
}
