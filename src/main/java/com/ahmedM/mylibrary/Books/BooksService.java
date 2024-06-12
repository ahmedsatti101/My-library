package com.ahmedM.mylibrary.Books;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
}
