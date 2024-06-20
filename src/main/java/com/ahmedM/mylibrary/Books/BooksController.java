package com.ahmedM.mylibrary.Books;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

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
    public ResponseEntity<BookDTO> getBookById(@PathVariable int id) {
        Optional<BookDTO> bookDTO = booksService.findBookById(id);

        return bookDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BookDTO updateBook(@RequestBody Books books, @PathVariable int id) {
        if (booksService.findBookById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
        booksService.updateIsRead(id, books);
        return booksService.findBookById(id).get();
    }
}
