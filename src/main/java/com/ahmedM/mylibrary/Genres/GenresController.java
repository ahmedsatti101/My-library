package com.ahmedM.mylibrary.Genres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/genres")
public class GenresController {

    @Autowired
    private GenresService genresService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Genres> findAllGenres() {
        return genresService.getAllGenres();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Genres> getGenreById(@PathVariable Integer id) {
        if (genresService.getGenreById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre not found");
        }

        return genresService.getGenreById(id);
    }
}
