package com.ahmedM.mylibrary.Genres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenresService {

    @Autowired
    private GenreRepository genreRepository;

    public List<Genres> getAllGenres() {
        return genreRepository.findAll();
    }
}
