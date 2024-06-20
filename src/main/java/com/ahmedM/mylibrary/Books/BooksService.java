package com.ahmedM.mylibrary.Books;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class BooksService {
    @Autowired
    private BooksRepository booksRepository;

    public List<Books> findAllBooks() {
        return booksRepository.findAll();
    }

    public Optional<BookDTO> findBookById(int id) {
        List<Object[]> result = booksRepository.findByAuthorId(id);

        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }

        Object[] row = result.getFirst();
        BookDTO bookDTO = new BookDTO(
                (int) row[0],
                (String) row[1],    // title
                (String) row[4],    // cover
                (String) row[6],    // description
                (int) row[2],       // authorId
                (int) row[3],       // genreId
                (boolean) row[5],   // is_read
                (String) row[7],     // author
                (String) row[8]
        );
        return Optional.of(bookDTO);
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
