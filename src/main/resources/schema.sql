DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS genres;

CREATE TABLE genres (
    genre_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(250) NOT NULL
);

CREATE TABLE authors (
    author_id SERIAL PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    avatar VARCHAR(250)
);

CREATE TABLE books (
    book_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(250) NOT NULL,
    author_id INT NOT NULL REFERENCES authors(author_id),
    genre_id INT NOT NULL REFERENCES genres(genre_id),
    cover VARCHAR(250),
    is_read BOOL,
    description VARCHAR(250) NOT NULL
);