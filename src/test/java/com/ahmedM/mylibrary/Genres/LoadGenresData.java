package com.ahmedM.mylibrary.Genres;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class LoadGenresData implements CommandLineRunner {

    private TestGenresRepository testGenresRepository;
    private ObjectMapper objectMapper;

    public LoadGenresData(ObjectMapper objectMapper, TestGenresRepository testGenresRepository) {
        this.objectMapper = objectMapper;
        this.testGenresRepository = testGenresRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        JsonNode json = null;
        List<Genres> genres = new ArrayList<>();

        try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/genres-test.json")) {
            json = objectMapper.readValue(inputStream, JsonNode.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonNode res = getNodes(json);

        for (JsonNode node : res) {
            genres.add(createGenresFromNode(node));
        }

        testGenresRepository.saveAll(genres);
    }

    private Genres createGenresFromNode(JsonNode node) {
        String name = node.get("name").asText();
        int id = node.get("genreId").asInt();
        return new Genres(id, name);
    }

    private JsonNode getNodes(JsonNode json) {
        return Optional.ofNullable(json)
                .map(j -> j.get("genres"))
                .orElseThrow(() -> new RuntimeException("Error getting json nodes."));
    }
}
