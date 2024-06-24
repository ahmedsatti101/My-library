package com.ahmedM.mylibrary.Genres;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Tag(name = "Genres endpoints")
    @Operation(summary = "Get all genres")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Genres> findAllGenres(@RequestParam(value = "limit", defaultValue = "10") int limit, @RequestParam(value = "p", defaultValue = "0") int p) {
        if (genresService.getAllGenres(limit, p).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return genresService.getAllGenres(limit, p);
    }

    @Tag(name = "Genres endpoints")
    @Operation(summary = "Get information about single genre")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Genres.class))}),
            @ApiResponse(responseCode = "404", description = "Genre not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Genres> getGenreById(@Parameter(description = "Genre id", required = true) @PathVariable Integer id) {
        if (genresService.getGenreById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre not found");
        }

        return genresService.getGenreById(id);
    }
}
