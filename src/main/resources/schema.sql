DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS genres;


CREATE TABLE genres (
    genre_id SERIAL PRIMARY KEY,
    name VARCHAR(250) NOT NULL
);

CREATE TABLE authors (
    author_id SERIAL PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    avatar VARCHAR(250)
);

CREATE TABLE books (
    book_id SERIAL PRIMARY KEY,
    title VARCHAR(250),
    author_id INT NOT NULL REFERENCES Authors(authorId),
    genre_id INT NOT NULL REFERENCES Genres(genreId),
    cover VARCHAR(250),
    `read` BOOL,
    description VARCHAR(250)
);