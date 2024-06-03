package com.ahmedM.mylibrary.Genres;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class GetGenresFromJson implements CommandLineRunner {
    private GenreRepository genreRepository;
    private ObjectMapper objectMapper;

    public GetGenresFromJson(GenreRepository genreRepository, ObjectMapper objectMapper) {
        this.genreRepository = genreRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        JsonNode json = null;
        List<Genres> genres = new ArrayList<>();

        try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/genres.json")) {
            json = objectMapper.readValue(inputStream, JsonNode.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonNode res = getNodes(json);

        for (JsonNode node : res) {
            genres.add(createGenresFromNode(node));
        }

        genreRepository.saveAll(genres);
    }

    private Genres createGenresFromNode(JsonNode node) {
        String name = node.get("name").asText();
        int id = node.get("genreId").asInt();
        return new Genres(id, name);
    }

    private JsonNode getNodes(JsonNode json) {
        return Optional.ofNullable(json)
                .map(j -> j.get("genres"))
                .orElseThrow(() -> new RuntimeException("Failed to get genres from main/resources/data/genres.json"));
    }
}
