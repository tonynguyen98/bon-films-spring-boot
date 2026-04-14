package com.comp586.bonfilms.controllers;

import com.comp586.bonfilms.models.FilmReview;
import com.comp586.bonfilms.services.FilmService;
import com.comp586.bonfilms.services.ReviewService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/film-reviews")
public class FilmReviewController {

    private final ReviewService reviewService;
    private final FilmService filmService;

    public FilmReviewController(ReviewService reviewService, FilmService filmService) {
        this.reviewService = reviewService;
        this.filmService = filmService;
    }

    @GetMapping
    public ResponseEntity<List<FilmReview>> getAllReviews() {
        List<FilmReview> filmReviews = reviewService.getAllReviews().stream()
                .map(review -> {
                    Long filmId = review.getFilm().getId();
                    String title = filmService.getFilm(filmId)
                            .map(film -> film.getTitle())
                            .orElse("Unknown Title");
                    return new FilmReview(review.getId(), review.getRating(), review.getReview(),
                            review.getUserReviewedId(), title, review.getDateReviewed());
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(filmReviews);
    }
}
