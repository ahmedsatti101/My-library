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
