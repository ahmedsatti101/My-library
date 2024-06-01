package com.ahmedM.mylibrary.Genres;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class GenresIntegrationTest {

    @LocalServerPort
    private int port;

    private String api = "http://localhost";

    private static RestTemplate restTemplate;

    @Autowired
    private TestGenresRepository testGenresRepository;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        api = api.concat(":").concat(port+"").concat("/api/genres");
    }

    @Test
    public void getAllGenres() {
        List<Genres> genres = restTemplate.getForObject(api, List.class);
        assertNotNull(genres);
        assertEquals(10, genres.size());
    }
}
