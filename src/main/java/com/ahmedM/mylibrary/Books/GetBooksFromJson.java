package com.ahmedM.mylibrary.Books;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Component
public class GetBooksFromJson implements CommandLineRunner {

    private BooksRepository booksRepository;
    private ObjectMapper objectMapper;

    public GetBooksFromJson(BooksRepository booksRepository, ObjectMapper objectMapper) {
        this.booksRepository = booksRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        JsonNode json = null;
        List<Books> books = new ArrayList<>();

        try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/books.json")) {
            json = objectMapper.readValue(inputStream, JsonNode.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonNode res = getNodes(json);

        for (JsonNode node : res) {
            books.add(createBooksFromNode(node));
        }

        booksRepository.saveAll(books);
    }

    private Books createBooksFromNode(JsonNode node) {
        String title = node.get("title").asText();
        int id = node.get("bookId").asInt();
        int authorId = node.get("authorId").asInt();
        int genreId = node.get("genreId").asInt();
        String cover = node.get("cover").asText();
        boolean isRead = node.get("isRead").asBoolean();
        String description = node.get("description").asText();

        return new Books(id, title, cover, isRead, description, authorId, genreId);
    }

    private JsonNode getNodes(JsonNode json) {
        return Optional.ofNullable(json)
                .map(j -> j.get("books"))
                .orElseThrow(() -> new RuntimeException("Failed to get books from json."));
    }
}
