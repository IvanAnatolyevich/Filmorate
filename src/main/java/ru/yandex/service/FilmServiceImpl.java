package ru.yandex.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.exception.NotFoundException;
import ru.yandex.model.Film;
import ru.yandex.model.User;
import ru.yandex.storage.InMemoryFilmStorage;
import ru.yandex.storage.InMemoryUserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Getter
public class FilmServiceImpl implements FilmService {

    private final InMemoryFilmStorage filmStorage;
    private final InMemoryUserStorage userStorage;

    public Film createFilm(Film film) {
        return filmStorage.createFilm(film);
    }

    public Film deleteFilm(Film film) {
        return filmStorage.deleteFilm(film);
    }

    public Film updateFilm(Film newFilm) {
        return filmStorage.updateFilm(newFilm);
    }

    public Collection<Film> allFilms() {
        return filmStorage.allFilms();
    }

    public Film addLike(Long id, Long userId) {
        if (filmStorage.getFilms().containsKey(id)) {
            if (userStorage.getUsers().containsKey(userId)) {
                filmStorage.getFilms().get(id).getUserLikes().add(userId);
                return filmStorage.getFilms().get(id);
            }
            throw new NotFoundException("Пользователь с id = " + userId + " не найден");
        }
        throw new NotFoundException("Фильм с id = " + id + " не найден");
    }

    public Film deleteLike(Long id, Long userId) {
        if (filmStorage.getFilms().containsKey(id)) {
            if (userStorage.getUsers().containsKey(userId)) {
                if (filmStorage.getFilms().get(id).getUserLikes().contains(userId)) {
                    filmStorage.getFilms().get(id).getUserLikes().remove(userId);
                    return filmStorage.getFilms().get(id);
                }
                throw new NotFoundException("Фильм с id = " + id + " не найден");
            }
            throw new NotFoundException("Пользователь с id = " + userId + " не найден");
        }
        throw new NotFoundException("Фильм с id = " + id + " не найден");
    }

    public Collection<Film> topFilms(Integer count) {
        return filmStorage.getFilms().values().stream()
                .sorted((f1, f2) -> Integer.compare(f2.getUserLikes().size(), f1.getUserLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }


    public Film getFilmId(Long id) {
        return filmStorage.getFilms().get(id);
    }
}
