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
        mockMvc.perform(get(api))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n" +
                        "        {\n" +
                        "            \"authorId\": 1,\n" +
                        "            \"name\": \"Collen Hover\",\n" +
                        "            \"avatar\": \"\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"authorId\": 2,\n" +
                        "            \"name\": \"J. K. Rowling\",\n" +
                        "            \"avatar\": \"\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"authorId\": 3,\n" +
                        "            \"name\": \"J. R. R. Tolkien\",\n" +
                        "            \"avatar\": \"\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"authorId\": 4,\n" +
                        "            \"name\": \"George R. R. Martin\",\n" +
                        "            \"avatar\": \"\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"authorId\": 5,\n" +
                        "            \"name\": \"Hannah Baker\",\n" +
                        "            \"avatar\": \"\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"authorId\": 6,\n" +
                        "            \"name\": \"John Smith\",\n" +
                        "            \"avatar\": \"\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"authorId\": 7,\n" +
                        "            \"name\": \"Jane Doe\",\n" +
                        "            \"avatar\": \"\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"authorId\": 8,\n" +
                        "            \"name\": \"Mark Twain\",\n" +
                        "            \"avatar\": \"\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"authorId\": 9,\n" +
                        "            \"name\": \"Alice Smith\",\n" +
                        "            \"avatar\": \"\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"authorId\": 10,\n" +
                        "            \"name\": \"Bob Johnson\",\n" +
                        "            \"avatar\": \"\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"authorId\": 11,\n" +
                        "            \"name\": \"Charlie Brown\",\n" +
                        "            \"avatar\": \"\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"authorId\": 12,\n" +
                        "            \"name\": \"David Lee\",\n" +
                        "            \"avatar\": \"\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"authorId\": 13,\n" +
                        "            \"name\": \"Emily Chen\",\n" +
                        "            \"avatar\": \"\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"authorId\": 14,\n" +
                        "            \"name\": \"Frank Davis\",\n" +
                        "            \"avatar\": \"\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"authorId\": 15,\n" +
                        "            \"name\": \"Grace Wang\",\n" +
                        "            \"avatar\": \"\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"authorId\": 16,\n" +
                        "            \"name\": \"Henry Lee\",\n" +
                        "            \"avatar\": \"\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"authorId\": 17,\n" +
                        "            \"name\": \"Ivy Chen\",\n" +
                        "            \"avatar\": \"\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"authorId\": 18,\n" +
                        "            \"name\": \"Jack Smith\",\n" +
                        "            \"avatar\": \"\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"authorId\": 19,\n" +
                        "            \"name\": \"Karen Johnson\",\n" +
                        "            \"avatar\": \"\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"authorId\": 20,\n" +
                        "            \"name\": \"Sarah Smith\",\n" +
                        "            \"avatar\": \"\"\n" +
                        "        }\n" +
                        "    ]"));
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
}
