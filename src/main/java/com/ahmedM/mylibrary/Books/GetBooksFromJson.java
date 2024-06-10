package com.ahmedM.mylibrary.Books;

import com.ahmedM.mylibrary.Authors.Authors;
import com.ahmedM.mylibrary.Genres.GenreRepository;
import com.ahmedM.mylibrary.Genres.Genres;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
        System.out.println(books);
        System.out.println(res);

//        booksRepository.create(createBooksFromNode(json));
    }

    private Books createBooksFromNode(JsonNode node) {
        Authors bookAuthor = new Authors();
        Set<Genres> bookGenre = new HashSet<>();
        Genres genres = new Genres();

        String title = node.get("title").asText();
        int id = node.get("bookId").asInt();
        int authorId = node.get("authorId").asInt();
        int genreId = node.get("genreId").asInt();
        String cover = node.get("cover").asText();
        boolean isRead = node.get("isRead").asBoolean();
        String description = node.get("description").asText();

        bookAuthor.setAuthorId(authorId);
        genres.setGenreId(genreId);
        bookGenre.add(genres);

        return new Books(id, title, cover, isRead, description, bookAuthor, bookGenre);
    }

    private JsonNode getNodes(JsonNode json) {
        return Optional.ofNullable(json)
                .map(j -> j.get("books"))
                .orElseThrow(() -> new RuntimeException("Failed to get books from json."));
    }
}
