package ru.yandex.storage.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.model.Film;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;

@Component
public class FilmMapper implements RowMapper<Film> {

    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        LocalDate releaseDate = LocalDate.parse(rs.getString("releaseDate"));
        Duration duration = Duration.ofMinutes(rs.getInt("duration"));
        long like = rs.getLong("like");
        int rating_id = rs.getInt("rating_id");
        return Film.builder().name(name).description(description).releaseDate(releaseDate).duration(duration)
                .like(like).rating(rating_id).build();
    }

}
