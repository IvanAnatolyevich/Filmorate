import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;

public class TestFilmController {
    FilmController filmController;
    Film film;
    @BeforeEach
    public void init() {
        filmController = new FilmController();
        film = new Film();
    }
    @Test
    void validTitle() {
        film.setDescription("Классный фильм");
        film.setDuration(Duration.ofHours(3));
        film.setReleaseDate(LocalDate.of(2004,4,7));
        Assertions.assertThrows(ValidationException.class, () -> filmController.createFilm(film));
        film.setName(" ");
        Assertions.assertThrows(ValidationException.class, () -> filmController.createFilm(film));
    }
    @Test
    void validDescription() {
        film.setName("Троя");
        film.setDescription("Классный фильм.Классный фильм.Классный фильм.Классный фильм.Классный фильм." +
                "Классный фильм.Классный фильм.Классный фильм.Классный фильм.Классный фильм.Классный фильм." +
                "Классный фильм.Классный фильм.Классный фильм.Классный фильм.Классный фильм.Классный фильм.");
        film.setDuration(Duration.ofHours(3));
        film.setReleaseDate(LocalDate.of(2004,4,7));
        Assertions.assertThrows(ValidationException.class, () -> filmController.createFilm(film));
    }
    @Test
    void validReleaseDate() {
        film.setName("Троя");
        film.setDescription("Классный фильм");
        film.setDuration(Duration.ofHours(3));
        film.setReleaseDate(LocalDate.of(1894,4,7));
        Assertions.assertThrows(ValidationException.class, () -> filmController.createFilm(film));
    }
    @Test
    void validDuration() {
        film.setName("Троя");
        film.setDescription("Классный фильм");
        film.setReleaseDate(LocalDate.of(2004,4,7));
        Assertions.assertThrows(ValidationException.class, () -> filmController.createFilm(film));
        film.setDuration(Duration.ofHours(-1));
        Assertions.assertThrows(ValidationException.class, () -> filmController.createFilm(film));
    }



    }
