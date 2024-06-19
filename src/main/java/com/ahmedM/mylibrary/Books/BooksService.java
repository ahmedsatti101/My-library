package com.ahmedM.mylibrary.Books;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BooksService {
    @Autowired
    private BooksRepository booksRepository;

    public List<Books> findAllBooks() {
        return booksRepository.findAll();
    }

    public Optional<Books> findBookById(int id) {
        List<Object[]> results = booksRepository.findByAuthorId(id);

        for (Object[] row : results) {
            int bookId = (int) row[0];
            String title = (String) row[1];
            String cover = (String) row[2];
            boolean isRead = (boolean) row[3];
            String description = (String) row[4];
            String author = (String) row[5];

            System.out.printf("Book ID: %d, Title: %s, Cover: %s, Is Read: %b, Description: %s, Author: %s%n",
                    bookId, title, cover, isRead, description, author);
        }
        return booksRepository.findById(id);
    }

    public Books updateIsRead(int id, Books book) {
        Books existingBook = null;

        if (booksRepository.findById(id).isPresent()) {
            existingBook = booksRepository.findById(id).get();
        }

        assert existingBook != null;
        existingBook.setRead(book.isRead());
        return booksRepository.save(existingBook);
    }
}
