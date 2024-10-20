import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

@RestController
@RequestMapping("/films")
public class FilmController {

    private HashMap<Long, Film> films = new HashMap<>();

    private final static Logger log = LoggerFactory.getLogger(FilmController.class);
    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        log.info("Получен запрос на добавление фильма {}", film);
        if (film.getName() == null || film.getName().isBlank()) {
            log.error("Ошибка валидации name");
            throw new ValidationException("Название фильма не может быть пустым");
        }
        if (film.getDescription() != null) {
            if (film.getDescription().length() > 200) {
                log.error("Ошибка валидации description");
                throw new ValidationException("Максимальная длина описания - 200 символов");
            }
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 2, 28))) {
            log.error("Ошибка валидации releaseDate");
            throw new ValidationException("Дата релиза должна быть не раньше 1895.02.28");
        }
        if (film.getDuration() == null || film.getDuration().isNegative()) {
            log.error("Ошибка валидации duration");
            throw new ValidationException("Продолжительность фильма доджна быть положительным числом");
        }
        film.setId(getNextId());
        films.put(film.getId(), film);
        log.info("Фильм успешно добавлен");
        return film;

    }

    @PutMapping
    public Film updateFilm(@RequestBody Film newFilm) {
        log.info("Получен запрос на обновление фильма {}", newFilm);
        if (newFilm.getId() == null) {
            log.error("Ошибка валидации");
            throw new ValidationException("Id должен быть указан");
        }
        if (films.containsKey(newFilm.getId())) {
            Film oldFilm = films.get(newFilm.getId());
            // если публикация найдена и все условия соблюдены, обновляем её содержимое
            oldFilm.setDescription(newFilm.getDescription());
            log.info("фильм успешно обновился");
            return newFilm;
        }
        log.error("Ошибка валидации");
        throw new ValidationException("Пост с id = " + newFilm.getId() + " не найден");
    }


    @GetMapping
    public Collection<Film> allFilms() {
        log.info("Получен запрос на получение фильмом");
        return films.values();
    }

    private long getNextId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

}
