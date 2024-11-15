package ru.yandex.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.model.Film;
import ru.yandex.storage.mapper.FilmMapper;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;

@Repository
@RequiredArgsConstructor
@Primary
public class FilmDbStorage implements FilmStorage{
    private final JdbcTemplate jdbcTemplate;
    private final FilmMapper filmMapper;

    @Override
    public Film createFilm(Film film) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO films(name, description, releaseDate, duration, like, ratin_id) " +
                            "VALUES(?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, film.getName());
            ps.setObject(2, film.getDescription());
            ps.setObject(3, film.getReleaseDate());
            ps.setObject(4, film.getDuration());
            ps.setObject(5, film.getLike());
            ps.setObject(6, film.getRating());
            return ps;
        }, keyHolder);
        Long generatedId = keyHolder.getKeyAs(Long.class);
        film.setId(generatedId);

        String sqlQuery = "insert into genreFilm(id_film, id_genre) " +
                "values (?, ?);";
        jdbcTemplate.update(sqlQuery, film.getId(), film.getGenre());  // ИЗМЕНИТЬ ПОЛЯ genre НА СПИСОК!!!! И ,СЛЕДОВАТЕЛЬНО, ИЗМЕНИТЬ ЗАПОЛНЕНИЕ ТАБЛИЦЫ И ОБНОВЛЕНИЕ ТАБЛИЦЫ!!!!

        sqlQuery = "insert into userLikes(id_film, id_user) " +
                "values (?, ?);";
        for(Long el: film.getUserLikes()) {
            jdbcTemplate.update(sqlQuery, film.getId(), el);
        }
        return film;

    }

    @Override
    public Collection<Film> allFilms() {
        return jdbcTemplate.query("SELECT * FROM films;", filmMapper);
    }

    @Override
    public Film updateFilm(Film newFilm) {
        String sqlQuery = "update films set" +
                "name = ?, description = ?, releaseDate = ?, duration = ?, like = ?, rating = ? " +
                "where id = ?;";
        jdbcTemplate.update(sqlQuery, newFilm.getName(), newFilm.getDescription(), newFilm.getReleaseDate(),
                newFilm.getDuration(), newFilm.getLike(), newFilm.getRating(), newFilm.getId());

        jdbcTemplate.update("delete from userLikes where id = ?;", newFilm.getId());
        sqlQuery = "insert into userLikes(id_film, id_user) " +
                "values (?, ?);";
        for(Long el: newFilm.getUserLikes()) {
            jdbcTemplate.update(sqlQuery, newFilm.getId(), el);
        }
        jdbcTemplate.update("update genreFilm set" +
                "id_film = ?, id_genre = ? " +
                "where id = ?;", newFilm.getId(), newFilm.getGenre());

        return newFilm;
    }

    @Override
    public Film deleteFilm(Film film) {
        String sqlQuery = "delete from ? where id = ?;";
        jdbcTemplate.update(sqlQuery, "films", film.getId());
        jdbcTemplate.update(sqlQuery, "genreFilm", film.getId());
        jdbcTemplate.update(sqlQuery, "userLikes", film.getId());
        return film;
    }
}
