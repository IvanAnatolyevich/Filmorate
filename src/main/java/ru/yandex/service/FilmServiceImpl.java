package ru.yandex.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.exception.FilmNotFoundException;
import ru.yandex.exception.UserNotFoundException;
import ru.yandex.model.Film;
import ru.yandex.model.User;
import ru.yandex.storage.InMemoryFilmStorage;
import ru.yandex.storage.InMemoryUserStorage;

import java.util.*;

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
                User user = userStorage.getUsers().get(userId);
                Set<Long> set = userStorage.getUsers().get(userId).getLikes();
                set.add(id);
                user.setLikes(set);
                Map<Long, User> map = userStorage.getUsers();
                map.put(userId, user);
                userStorage.setUsers(map);
                return filmStorage.getFilms().get(id);
                // ИСПРАВИТЬ ЗАМЕНУ МНОЖЕСТВА В КЛАССЕ USERCONTROLLER
            }
            throw new UserNotFoundException("Пост с id = " + userId + " не найден");
        }
        throw new FilmNotFoundException("Пост с id = " + id + " не найден");
    }

    public Film deleteLike(Long id, Long userId) {
        if (filmStorage.getFilms().containsKey(id)) {
            if (userStorage.getUsers().containsKey(userId)) {
                if (userStorage.getUsers().get(userId).getLikes().contains(id)) {
                    User user = userStorage.getUsers().get(userId);
                    Set<Long> set = userStorage.getUsers().get(userId).getLikes();
                    set.remove(id);
                    user.setLikes(set);
                    Map<Long, User> map = userStorage.getUsers();
                    map.put(userId, user);
                    userStorage.setUsers(map);
                    return filmStorage.getFilms().get(id);
                }
                throw new FilmNotFoundException("Пост с id = " + id + " не найден");
            }
            throw new UserNotFoundException("Пост с id = " + userId + " не найден");
        }
        throw new FilmNotFoundException("Пост с id = " + id + " не найден");
    }

    public Set<Film> topFilm(Integer count) {
        Comparator<Film> filmComparator = new Comparator<>() {
            @Override
            public int compare(Film film1, Film film2) {
                return film1.getLike().compareTo(film2.getLike());
            }
        };

        Set<Film> top = new TreeSet(filmComparator);
        top.addAll(filmStorage.getFilms().values());
        if (count == null) {
            Film[] topFilms = new Film[10];
            for (Film  film: top) {
                int i = 0;
                if (i == 10) {
                    return new HashSet<>(List.of(topFilms));
                }
                topFilms[i] = film;
            }
            return new HashSet<>(List.of(topFilms));
        }
        Film[] topFilms = new Film[count];
        for (Film  film: top) {
            int i = 0;
            if (i >= count) {
                break;
            }
            topFilms[i] = film;
        }
        return new HashSet<>(List.of(topFilms));
    }

    public Film getFilmId(Long id) {
        return filmStorage.getFilms().get(id);
    }
}
