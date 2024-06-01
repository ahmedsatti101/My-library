DROP TABLE IF EXISTS Books;
DROP TABLE IF EXISTS Authors;
DROP TABLE IF EXISTS Genres;


CREATE TABLE Genres (
    genreId SERIAL PRIMARY KEY,
    name VARCHAR(250) NOT NULL
);

CREATE TABLE Authors (
    authorId SERIAL PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    avatar VARCHAR(250)
);

CREATE TABLE Books (
    bookId SERIAL PRIMARY KEY,
    title VARCHAR(250),
    authorId INT NOT NULL REFERENCES Authors(authorId),
    genreId INT NOT NULL REFERENCES Genres(genreId),
    cover VARCHAR(250),
    `read` BOOL,
    description VARCHAR(250)
);