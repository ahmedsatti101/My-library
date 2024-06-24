package com.ahmedM.mylibrary.Genres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenresService {

    @Autowired
    private GenreRepository genreRepository;

    public List<Genres> getAllGenres(int limit, int p) {
        PageRequest pageRequest = PageRequest.of(p, limit);
        Page<Genres> genresPage = genreRepository.findAll(pageRequest);
        return genresPage.getContent();
    }

    public Optional<Genres> getGenreById(Integer id) {
        return genreRepository.findById(id);
    }
}
