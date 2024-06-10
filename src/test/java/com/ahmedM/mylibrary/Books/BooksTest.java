package com.ahmedM.mylibrary.Books;

import com.ahmedM.mylibrary.Authors.Authors;
import com.ahmedM.mylibrary.Genres.Genres;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class BooksTest {

    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    private String api = "http://localhost";

//    @BeforeAll
//    public static void beforeAll() {
//        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//        session = sessionFactory.openSession();
//        session.beginTransaction();
//    }
//
//    @AfterAll
//    public static void tearDown() {
//        if (session != null) {
//            session.getTransaction().commit();
//            session.close();
//        }
//    }

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

        assertNotNull(result.getResponse().getContentAsString());
        assertEquals(40, result.getResponse().getContentLength());
    }
}
