CREATE TABLE IF NOT EXISTS my_users (
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    login VARCHAR(50) NOT NULL,
    birthday DATE
);


CREATE TABLE IF NOT EXISTS films (
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(200) NOT NULL,
    releaseDate TIMESTAMP,
    duration INTEGER,
    like INTEGER,
    rating_id INTEGER REFERENCES rating (id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS genre (
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    genre VARCHAR(50)
);


CREATE TABLE IF NOT EXISTS rating (
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    rating VARCHAR(5)
);


CREATE TABLE IF NOT EXISTS status (
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    status VARCHAR(20)
);


CREATE TABLE IF NOT EXISTS genreFilm (
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    id_genre INTEGER REFERENCES genre (id) ON DELETE CASCADE,
    id_film INTEGER REFERENCES films (id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS userLikes (
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    id_user INTEGER REFERENCES my_users (id) ON DELETE CASCADE,
    id_film INTEGER REFERENCES films (id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS friends (
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    id_user INTEGER REFERENCES my_users (id) ON DELETE CASCADE,
    id_friends INTEGER REFERENCES my_users (id) ON DELETE CASCADE
);
