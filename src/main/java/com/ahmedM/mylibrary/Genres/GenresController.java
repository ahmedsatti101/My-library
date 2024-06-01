package com.ahmedM.mylibrary.Genres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenresController {

    @Autowired
    private GenresService genresService;

    @GetMapping
    public List<Genres> findAllGenres() {
        return genresService.getAllGenres();
    }
}
