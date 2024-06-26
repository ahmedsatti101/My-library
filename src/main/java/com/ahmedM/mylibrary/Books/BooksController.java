package com.ahmedM.mylibrary.Books;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BooksController {
    @Autowired
    private BooksService booksService;

    private static final List<String> validColumns = List.of("bookId", "title", "cover", "isRead", "description", "authorId", "genreId");
    private static final List<String> validOrderByQueries = List.of("desc", "asc");

    @Tag(name = "Books endpoints")
    @Operation(summary = "Get all books")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "404")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Books> getAllBooks(@RequestParam(value = "sort_by", defaultValue = "isRead") String column, @RequestParam(value = "order", defaultValue = "desc") String order, @RequestParam(value = "limit", defaultValue = "10") int limit, @RequestParam(value = "p", defaultValue = "0") int p) {
        if (!validColumns.contains(column)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid sort by query");
        }

        if (!validOrderByQueries.contains(order)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid order by query");
        }

        if (booksService.findAllBooks(column, order, limit, p).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return booksService.findAllBooks(column, order, limit, p);
    }

    @Tag(name = "Books endpoints")
    @Operation(summary = "Get information about single book")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = BookDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BookDTO> getBookById(@Parameter(description = "Book id", required = true) @PathVariable int id) {
        Optional<BookDTO> bookDTO = booksService.findBookById(id);

        return bookDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Tag(name = "Books endpoints")
    @Operation(summary = "Update whether book has been read or not")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = BookDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BookDTO updateBook(@RequestBody Books books, @Parameter(description = "Book id", required = true) @PathVariable int id) {
        if (booksService.findBookById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
        booksService.updateIsRead(id, books);
        return booksService.findBookById(id).get();
    }
}
