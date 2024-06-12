package com.ahmedM.mylibrary.Books;

import org.json.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class BooksTest {

    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

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
        MvcResult result = mockMvc.perform(get(api))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        JSONArray array = new JSONArray(content);

        assertNotNull(content);
        assertEquals(40, array.length());
    }

    @Test
    public void returnNotFoundIfEndpointIsWrong() throws Exception {
        mockMvc.perform(get("/api/book"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getBookById() throws Exception {
        MvcResult result = mockMvc.perform(get(api + "/{id}", 7))
                .andExpect(status().isOk())
                .andReturn();
        String response = result.getResponse().getContentAsString();
        JSONObject object = new JSONObject(response);

        assertNotNull(response);
        assertEquals(7, object.getInt("bookId"));
        assertEquals("Dystopian Dreams", object.getString("title"));
        assertEquals(7, object.getInt("authorId"));
        assertEquals(7, object.getInt("genreId"));
        assertEquals("", object.getString("cover"));
        assertFalse(object.getBoolean("read"));
        assertEquals("A dark vision of the future.", object.getString("description"));
    }

    @Test
    public void throwNotFoundIfIdNotFound() throws Exception {
        mockMvc.perform(get(api + "/{id}", 200))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Book not found"));
    }

    @Test
    public void throwBadRequestIfIdIsNotANumber() throws Exception {
        mockMvc.perform(get(api + "/{id}", "banana"))
                .andExpect(status().isBadRequest());
    }
}
