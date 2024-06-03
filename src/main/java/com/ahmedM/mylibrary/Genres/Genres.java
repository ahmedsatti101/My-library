package com.ahmedM.mylibrary.Genres;

import jakarta.persistence.*;
import org.springframework.context.annotation.Profile;

@Entity
@Table(name = "genres")
public class Genres {
    @Column(name = "genre_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int genreId;

    @Column(name = "name")
    private String name;

    public Genres(int genreId, String genreName) {
        this.genreId = genreId;
        this.name = genreName;
    }

    public Genres() {

    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
