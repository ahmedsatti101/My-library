package com.ahmedM.mylibrary.Authors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/authors")
public class AuthorsController {
    @Autowired
    private AuthorsService authorsService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Authors> getAllAuthors() {
        return authorsService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Authors> getAuthorById(@PathVariable Integer id) {
        if (authorsService.getById(id).isEmpty()) {
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found");
        }

        return authorsService.getById(id);
    }
}
