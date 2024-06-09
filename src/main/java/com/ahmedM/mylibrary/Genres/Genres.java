package com.ahmedM.mylibrary.Genres;

import com.ahmedM.mylibrary.Books.Books;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "genres")
public class Genres {
    @Column(name = "genre_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int genreId;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "genres")
    private Set<Books> books = new HashSet<>();

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

    @Override
    public String toString() {
        return "Genres{" +
                "genreId=" + genreId +
                ", name='" + name + '\'' +
                ", books=" + books +
                '}';
    }

    public Set<Books> getBooks() {
        return books;
    }

    public void setBooks(Set<Books> books) {
        this.books = books;
    }
}
