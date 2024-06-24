package com.ahmedM.mylibrary.Books;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.*;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.*;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class BooksTest {

    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper objectMapper;

    private String api = "http://localhost";

    @InjectMocks
    BooksService booksService;

    @Mock
    BooksRepository booksRepository;

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
        assertEquals("Jane Doe", object.getString("author"));
        assertEquals("Dystopian", object.getString("genre"));
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

    @Test
    public void updateIfBookWasRead() throws Exception {
        HashMap<String, Boolean> update = new HashMap<>();
        update.put("read", true);

        MvcResult result = mockMvc.perform(patch(api + "/{id}", 23)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(content);

        assertTrue(jsonObject.getBoolean("read"));
    }

    @Test
    public void updateIfBookWasNotRead() throws Exception {
        HashMap<String, Boolean> update = new HashMap<>();
        update.put("read", false);

        MvcResult result = mockMvc.perform(patch(api + "/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(content);

        assertFalse(jsonObject.getBoolean("read"));
    }

    @Test
    public void throwErrorIfRequestBodyIsEmpty() throws Exception {
        mockMvc.perform(patch(api + "/{id}", 17))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void patchRequest_throwBadRequestIfIdIsNotANumber() throws Exception {
        mockMvc.perform(patch(api + "/{id}", "apple"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void patchRequest_throwNotFoundIfIdNotFound() throws Exception {
        HashMap<String, Boolean> update = new HashMap<>();
        update.put("read", false);

        mockMvc.perform(patch(api + "/{id}", 1000)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Book not found"));
    }

    @Test
    public void sortByIsRead_OrderInDescendingOrder() {
        // Arrange
        String column = "isRead";
        String order = "desc";

        // Create a sample list of books to be returned by the mock repository
        List<Books> mockBooksList = new ArrayList<>();
        mockBooksList.add(new Books(1, "Book 1", "", true, "Book 1 description", 15, 2));
        mockBooksList.add(new Books(2, "Book 2", "", false, "Book 2 description", 12, 9));

        // Mock the repository's findAll method
        when(booksRepository.findAll(Sort.by(Sort.Direction.DESC, column))).thenReturn(mockBooksList);

        // Act
        List<Books> result = booksService.findAllBooks(column, order);

        // Assert
        assertNotNull(result);  // Ensure the result is not null
        assertEquals(2, result.size());  // Ensure the size of the result list is as expected
        assertEquals("Book 1", result.getFirst().getTitle());  // Check the content of the result list

        // Verify that the repository's findAll method was called with the correct parameters
        verify(booksRepository, times(1)).findAll(Sort.by(Sort.Direction.DESC, column));
    }

    @Test
    public void returnNotFoundIfGivenWrongColumnToSortBy() throws Exception {
        mockMvc.perform(get(api + "?sort_by=apple"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Invalid sort by query"));
    }

    @Test
    public void returnNotFoundIfGivenWrongOrderQuery() throws Exception {
        mockMvc.perform(get(api + "?order=banana"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid order by query"));
    }
}
