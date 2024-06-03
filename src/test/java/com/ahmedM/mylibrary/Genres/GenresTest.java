package com.ahmedM.mylibrary.Genres;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class GenresTest {

    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    private String api = "http://localhost";

    private static RestTemplate restTemplate;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        api = api.concat(":").concat(port + "").concat("/api/genres");
    }

    @Test
    public void getAllGenres() {
        List<Genres> genres = restTemplate.getForObject(api, List.class);
        assertNotNull(genres);
        assertEquals(40, genres.size());
    }

    @Test
    public void getGenreById() {
        Genres genre = restTemplate.getForObject(api + "/{id}", Genres.class, 1);
        assertAll(
                () -> assertEquals(1, genre.getGenreId()),
                () -> assertNotNull(genre),
                () -> assertEquals("Fantasy", genre.getName())
        );
    }

    @Test
    public void throwNotFoundIfGenreDoesNotExist() throws Exception {
        mockMvc.perform(get(api+"/{id}", 240))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Genre not found"));
    }

    @Test
    public void throwNotFoundIfIdIsNotCorrect() throws Exception {
        mockMvc.perform(get(api+"/{id}", "banana"))
                .andExpect(status().isBadRequest());
    }
}
