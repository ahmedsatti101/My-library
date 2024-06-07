package com.ahmedM.mylibrary.Books;

import com.ahmedM.mylibrary.Authors.Authors;
import com.ahmedM.mylibrary.Genres.Genres;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "books")
public class Books {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "book_id")
    private int bookId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "cover")
    private String cover;

    @Column(name = "is_read")
    private boolean isRead;

    @Column(name = "description", nullable = false)
    private String description;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "author_id", nullable = false)
    private Authors author;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "genre_id", nullable = false)
    private Genres genre;

    public Books() {
    }

    public Books(int bookId, String title, String cover, boolean isRead, String description, Authors author, Genres genre) {
        this.bookId = bookId;
        this.title = title;
        this.cover = cover;
        this.isRead = isRead;
        this.description = description;
        this.author = author;
        this.genre = genre;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Authors getAuthor() {
        return author;
    }

    public void setAuthor(Authors author) {
        this.author = author;
    }

    public Genres getGenre() {
        return genre;
    }

    public void setGenre(Genres genre) {
        this.genre = genre;
    }

    @JsonProperty("authorName")
    public String getAuthorName() {
        return author != null ? author.getName() : null;
    }

    @JsonProperty("genreName")
    public String getGenreName() {
        return genre != null ? genre.getName() : null;
    }
}
