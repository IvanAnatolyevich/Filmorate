package ru.yandex.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.model.Film;
import ru.yandex.service.FilmServiceImpl;
import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmServiceImpl filmServiceImpl;
    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        return filmServiceImpl.createFilm(film);
    }

    @GetMapping
    public Collection<Film> allFilms() {
        return filmServiceImpl.allFilms();
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film newFilm) {
        return filmServiceImpl.updateFilm(newFilm);
    }

    @DeleteMapping
    public Film deleteFilm(@RequestBody Film film) {
        return filmServiceImpl.deleteFilm(film);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public Film addLike(@PathVariable Long id, @PathVariable Long userId) {
        return filmServiceImpl.addLike(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public Film deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        return filmServiceImpl.deleteLike(id, userId);
    }

    @GetMapping("/films/popular")
    public Collection<Film> topFilms(@RequestParam Integer count) {
        return filmServiceImpl.topFilms(count);
    }

    @GetMapping("/films/{id}")
    public Film getFilmId(@PathVariable Long id) {
        return filmServiceImpl.getFilmId(id);
    }




}
