package com.ahmedM.mylibrary.Books;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface BooksRepository extends JpaRepository<Books, Integer> {
    @Query(value = "SELECT book_id, title, authors.author_id, genre_id, cover, is_read, description, authors.name as author FROM books LEFT OUTER JOIN authors ON books.author_id = authors.author_id WHERE books.book_id = :id GROUP BY books.book_id", nativeQuery = true)
    List<Object[]> findByAuthorId(@Param("id") int id);
}
