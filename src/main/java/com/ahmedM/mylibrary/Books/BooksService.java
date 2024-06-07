package com.ahmedM.mylibrary.Books;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksService {
    @Autowired
    private BooksRepository booksRepository;

    public List<Books> findAllBooks() {
        return booksRepository.findAll();
    }
}
