package com.ahmedM.mylibrary.Authors;

import org.json.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class AuthorsTest {

    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    private String api = "http://localhost";

    @BeforeEach
    public void setUp() {
        api = api.concat(":").concat(port + "").concat("/api/authors");
    }

    @Test
    public void makeRequestToAuthorsEndpoint() throws Exception {
        mockMvc.perform(get(api))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllAuthors() throws Exception {
        MvcResult result = mockMvc.perform(get(api))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        JSONArray response = new JSONArray(content);

        assertNotNull(content);
        assertEquals(10, response.length());
    }

    @Test
    public void returnNotFoundIfEndpointIsWrong() throws Exception {
        mockMvc.perform(get("/api/author"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAuthorById() throws Exception {
        MvcResult result = mockMvc.perform(get(api + "/{id}", 1))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        JSONObject obj = new JSONObject(content);

        assertEquals(1, obj.getInt("authorId"));
        assertEquals("Collen Hover", obj.getString("name"));
        assertEquals("", obj.getString("avatar"));
    }

    @Test
    public void throwNotFoundIfIdNotFound() throws Exception {
        mockMvc.perform(get(api + "/{id}", 200))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Author not found"));
    }

    @Test
    public void throwBadRequestIfIdIsNotANumber() throws Exception {
        mockMvc.perform(get(api + "/{id}", "banana"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void throwBadRequestIfLimitIsNotANumber() throws Exception {
        mockMvc.perform(get(api + "?limit=banana"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void throwBadRequestIfPageIsNotANumber() throws Exception {
        mockMvc.perform(get(api + "?p=apple"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void throwNotFoundIfContentNotFoundOnPage() throws Exception {
        mockMvc.perform(get(api + "?p=100"))
                .andExpect(status().isNotFound());
    }
}
