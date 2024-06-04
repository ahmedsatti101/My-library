package com.ahmedM.mylibrary.Authors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class GetAuthorsFromJson implements CommandLineRunner {
    private AuthorsRepository authorsRepository;
    private ObjectMapper objectMapper;

    public GetAuthorsFromJson(AuthorsRepository authorsRepository, ObjectMapper objectMapper) {
        this.authorsRepository = authorsRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        JsonNode json = null;
        List<Authors> authors = new ArrayList<>();

        try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/authors.json")) {
            json = objectMapper.readValue(inputStream, JsonNode.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonNode res = getNodes(json);

        for (JsonNode node : res) {
            authors.add(createAuthorsFromNode(node));
        }

        authorsRepository.saveAll(authors);
    }

    private Authors createAuthorsFromNode(JsonNode node) {
        String name = node.get("name").asText();
        int id = node.get("authorId").asInt();
        String avatar = node.get("avatar").asText();
        return new Authors(id, name, avatar);
    }

    private JsonNode getNodes(JsonNode json) {
        return Optional.ofNullable(json)
                .map(j -> j.get("authors"))
                .orElseThrow(() -> new RuntimeException("Failed to get authors from json."));
    }
}
