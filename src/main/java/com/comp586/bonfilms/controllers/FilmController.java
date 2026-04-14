package com.comp586.bonfilms.controllers;

import com.comp586.bonfilms.entities.Film;
import com.comp586.bonfilms.entities.Review;
import com.comp586.bonfilms.services.FilmService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/films")
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public ResponseEntity<List<Film>> getAllFilms() {
        return ResponseEntity.ok(filmService.getAllFilms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable Long id) {
        return filmService.getFilm(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Film> createFilm(@RequestBody Film film) {
        return ResponseEntity.status(HttpStatus.CREATED).body(filmService.saveFilm(film));
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<Review>> getReviewsByFilmId(@PathVariable Long id) {
        return filmService.getFilm(id)
                .map(film -> ResponseEntity.ok(film.getReviews()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
