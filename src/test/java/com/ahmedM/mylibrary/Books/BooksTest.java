package com.ahmedM.mylibrary.Books;

import com.ahmedM.mylibrary.Authors.Authors;
import com.ahmedM.mylibrary.Authors.AuthorsRepository;
import com.ahmedM.mylibrary.Genres.GenreRepository;
import com.ahmedM.mylibrary.Genres.Genres;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Iterator;

import static net.bytebuddy.matcher.ElementMatchers.is;
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
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);
        JsonNode booksNode = rootNode.path("books");

        Iterator<JsonNode> iterator = booksNode.elements();

        while (iterator.hasNext()) {
            Authors author = new Authors();
            Genres genre = new Genres();
            Books book = new Books();

            System.out.println(iterator.next());
            JsonNode bookNode = iterator.next();
            author.setAuthorId(bookNode.path("authorId").asInt());
//
//            genre.setGenreId(bookNode.path("genreId").asInt());
//            genre.setName("Genre Name " + genre.getGenreId());
//
//            book.setTitle(bookNode.path("title").asText());
//            book.setAuthor(author);
//            book.setGenre(genre);
//            book.setCover(bookNode.path("cover").asText());
//            book.setRead(bookNode.path("isRead").asBoolean());
//            book.setDescription(bookNode.path("description").asText());
//
//            booksRepository.save(book);
        }

//        Authors author = new Authors();
//        Genres genre = new Genres();

//        author.setName("Ahmed Mohamed");
//        genre.setName("Horror");
//
//        Books book = new Books();
//        book.setTitle("Life");
//        book.setAuthor(author);
//        book.setGenre(genre);
//        book.setCover("");
//        book.setRead(false);
//        book.setDescription("Sucks");
//
//        booksRepository.save(book);

        mockMvc.perform(get(api))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(40));
    }
}
