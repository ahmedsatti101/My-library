package com.ahmedM.mylibrary.Genres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/genres")
public class GenresController {

    @Autowired
    private GenresService genresService;

    @GetMapping
    public List<Genres> findAllGenres() {
        return genresService.getAllGenres();
    }

    @GetMapping("/{id}")
    public Optional<Genres> getGenreById(@PathVariable Integer id) {
        return genresService.getGenreById(id);
    }
}
