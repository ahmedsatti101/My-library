package com.ahmedM.mylibrary.Books;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface BooksRepository extends JpaRepository<Books, Integer> {
    @Query(value = "SELECT book_id, title, cover, is_read, description, authors.name as author FROM books LEFT OUTER JOIN authors ON books.author_id = authors.author_id WHERE authors.author_id = :id", nativeQuery = true)
    List<Object[]> findByAuthorId(@Param("id") int id);
}
