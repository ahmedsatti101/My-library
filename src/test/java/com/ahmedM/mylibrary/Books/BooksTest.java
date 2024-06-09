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

    private static Session session;
    @Autowired
    private MockMvc mockMvc;
    @LocalServerPort
    private int port;
    private String api = "http://localhost";

    @BeforeAll
    public static void beforeAll() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @AfterAll
    public static void tearDown() {
        if (session != null) {
            session.getTransaction().commit();
            session.close();
        }
    }

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
    public void givenSession_whenRead_thenReturnsMtoMdata() throws Exception {
        prepareData();
        List<Books> booksList = session.createQuery("from Books ").list();
        List<Genres> genresList = session.createQuery("from Genres").list();
        List<Authors> authors = session.createQuery("from Authors").list();
        assertNotNull(booksList);
        assertNotNull(genresList);
        assertEquals(2, booksList.size());
        assertEquals(40, genresList.size());
    }

    private void prepareData() throws Exception {
        Authors author = new Authors();

        MvcResult mvcResult = mockMvc.perform(get("/api/authors")).andReturn();
        JSONArray jsonArray = new JSONArray(mvcResult.getResponse().getContentAsString());

        for (int i = 0; i < jsonArray.length(); i++) {
            author.setName(jsonArray.getJSONObject(i).getString("name"));
        }
        session.persist(author);

        String[] bookData = {"The Housemaid", "Verity"};
        for (String bookTitle : bookData) {
            Books book = new Books();
            book.setTitle(bookTitle);
            book.setCover("");
            book.setRead(true);
            book.setDescription("");
            book.setAuthor(author);
            session.persist(book);
        }

        session.flush();
    }
}
