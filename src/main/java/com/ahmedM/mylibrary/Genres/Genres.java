package com.ahmedM.mylibrary.Genres;

import jakarta.persistence.*;

@Entity
@Table(name = "Genres")
public class Genres {
    @Column(name = "genreId")
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
}
