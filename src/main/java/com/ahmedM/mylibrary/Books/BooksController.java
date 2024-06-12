package com.ahmedM.mylibrary.Books;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BooksController {
    @Autowired
    private BooksService booksService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Books> getAllBooks() {
        return booksService.findAllBooks();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Books> getBookById(@PathVariable int id) {
        if (booksService.findBookById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
        return booksService.findBookById(id);
    }
}
