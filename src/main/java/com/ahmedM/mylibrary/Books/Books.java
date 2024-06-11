package com.ahmedM.mylibrary.Books;

import com.ahmedM.mylibrary.Authors.Authors;
import com.ahmedM.mylibrary.Genres.Genres;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

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

    @Column(name = "author_id", nullable = false)
    private int authorId;

    @Column(name = "genre_id", nullable = false)
    private int genreId;

    public Books() {
    }

    public Books(int bookId, String title, String cover, boolean isRead, String description, int authorId, int genreId) {
        this.bookId = bookId;
        this.title = title;
        this.cover = cover;
        this.isRead = isRead;
        this.description = description;
        this.authorId = authorId;
        this.genreId = genreId;
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

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    @Override
    public String toString() {
        return "Books{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", cover='" + cover + '\'' +
                ", isRead=" + isRead +
                ", description='" + description + '\'' +
                ", author=" + authorId +
                ", genres=" + genreId +
                '}';
    }
}
