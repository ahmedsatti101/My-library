package com.ahmedM.mylibrary.Genres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenresService {

    @Autowired
    private GenreRepository genreRepository;

    public List<Genres> getAllGenres() {
        return genreRepository.findAll();
    }

    public Optional<Genres> getGenreById(Integer id) {
        return genreRepository.findById(id);
    }
}
