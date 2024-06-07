package com.ahmedM.mylibrary.Books;
//package com.ahmedM.mylibrary.Books;
//
//import com.ahmedM.mylibrary.Authors.Authors;
//import com.ahmedM.mylibrary.Genres.Genres;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Component
//public class GetBooksFromJson implements CommandLineRunner {
//    private BooksRepository booksRepository;
//    private ObjectMapper objectMapper;
//
//    public GetBooksFromJson(BooksRepository booksRepository, ObjectMapper objectMapper) {
//        this.booksRepository = booksRepository;
//        this.objectMapper = objectMapper;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        JsonNode json = null;
//        List<Books> books = new ArrayList<>();
//
//        try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/books.json")) {
//            json = objectMapper.readValue(inputStream, JsonNode.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        JsonNode res = getNodes(json);
//
//        for (JsonNode node : res) {
//            books.add(createAuthorsFromNode(node));
//        }
//
//        booksRepository.saveAll(books);
//    }
//
//    private Books createAuthorsFromNode(JsonNode node) {
//        int id = node.get("bookId").asInt();
//        String title = node.get("title").asText();
//        Authors authorId = new Authors();
//        Genres genreId = new Genres();
//        String cover = node.get("cover").asText();
//        boolean isRead = node.get("isRead").asBoolean();
//        String description = node.get("description").asText();
//        return new Books(id, title, cover, isRead, description, authorId, genreId);
//    }
//
//    private JsonNode getNodes(JsonNode json) {
//        return Optional.ofNullable(json)
//                .map(j -> j.get("books"))
//                .orElseThrow(() -> new RuntimeException("Failed to get books from json."));
//    }
//}
//

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GetBooksFromJson {
    public static String GetBooksFromJson(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }
}
