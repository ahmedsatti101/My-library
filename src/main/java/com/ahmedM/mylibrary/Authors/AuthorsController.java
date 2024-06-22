package com.ahmedM.mylibrary.Authors;

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
@RequestMapping("/api/authors")
public class AuthorsController {
    @Autowired
    private AuthorsService authorsService;

    @Tag(name = "Authors endpoints")
    @Operation(summary = "Get all authors")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Authors> getAllAuthors() {
        return authorsService.getAll();
    }

    @Tag(name = "Authors endpoints")
    @Operation(summary = "Get information about single author")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Authors.class))}),
            @ApiResponse(responseCode = "404", description = "Author not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Authors> getAuthorById(@Parameter(description = "Author id", required = true) @PathVariable Integer id) {
        if (authorsService.getById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found");
        }

        return authorsService.getById(id);
    }
}
