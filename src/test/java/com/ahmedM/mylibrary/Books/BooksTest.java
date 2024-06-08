package com.ahmedM.mylibrary.Books;

import com.ahmedM.mylibrary.Authors.Authors;
import com.ahmedM.mylibrary.Authors.AuthorsRepository;
import com.ahmedM.mylibrary.Genres.GenreRepository;
import com.ahmedM.mylibrary.Genres.Genres;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class BooksTest {

    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private AuthorsRepository authorsRepository;

    @Autowired
    private GenreRepository genresRepository;

    private String api = "http://localhost";

    @BeforeEach
    public void setUp() {
        api = api.concat(":").concat(port + "").concat("/api/books");
    }

    @Test
    public void makeRequestToBooksEndpoint() throws Exception {
        mockMvc.perform(get(api))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllBooks() throws Exception {
        String json = GetBooksFromJson.GetBooksFromJson("src/main/resources/data/books.json");
        JSONObject obj = new JSONObject(json);
        JSONArray arr = obj.getJSONArray("books");
        Authors author = new Authors();
        Genres genre = new Genres();
        Books book = new Books();

        for (int i = 0; i < arr.length(); i++) {
            author.setAuthorId(arr.getJSONObject(i).getInt("genreId"));
            genre.setGenreId(arr.getJSONObject(i).getInt("authorId"));
            book.setTitle(arr.getJSONObject(i).getString("title"));
            book.setCover(arr.getJSONObject(i).getString("cover"));
            book.setRead(arr.getJSONObject(i).getBoolean("isRead"));
            book.setDescription(arr.getJSONObject(i).getString("description"));
        }

            booksRepository.save(book);

        mockMvc.perform(get(api))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(40));
    }
}
