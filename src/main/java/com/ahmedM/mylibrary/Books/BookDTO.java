package com.ahmedM.mylibrary.Books;

public class BookDTO {
    private int bookId;
    private String title;
    private String cover;
    private String description;
    private int authorId;
    private int genreId;
    private boolean read;
    private String author;
    private String genre;

    public BookDTO(int bookId, String title, String cover, String description, int authorId, int genreId, boolean read, String author, String genre) {
        this.bookId = bookId;
        this.title = title;
        this.cover = cover;
        this.description = description;
        this.authorId = authorId;
        this.genreId = genreId;
        this.read = read;
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

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
