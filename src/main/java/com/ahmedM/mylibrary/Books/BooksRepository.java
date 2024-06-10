package com.ahmedM.mylibrary.Books;

import com.ahmedM.mylibrary.Authors.Authors;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

@Repository
public class BooksRepository {

    private final JdbcClient jdbcClient;

    public BooksRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Books> findAll() {
        return jdbcClient
                .sql("SELECT * FROM books;")
                .query(Books.class)
                .list();
    }

    public void create(Books books) {
        Authors bookAuthor = new Authors();
        var updated = jdbcClient
                .sql("INSERT INTO books(book_id, title, author_id, cover, is_read, description) VALUES(?,?,?,?,?,?);")
                .params(List.of(books.getBookId(), books.getTitle(), bookAuthor.getAuthorId(), books.getCover(), books.isRead(), books.getDescription()))
                .update();

        Assert.state(updated == 1, "Failed to create book: " + books.getTitle());
    }

    public void saveAll(List<Books> books) {
        books.forEach(this::create);
    }
}
