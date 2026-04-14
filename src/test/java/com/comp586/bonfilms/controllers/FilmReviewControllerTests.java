package com.comp586.bonfilms.controllers;

import com.comp586.bonfilms.entities.Film;
import com.comp586.bonfilms.entities.Review;
import com.comp586.bonfilms.services.FilmService;
import com.comp586.bonfilms.services.ReviewService;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(FilmReviewController.class)
class FilmReviewControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ReviewService reviewService;

  @MockBean
  private FilmService filmService;

  @Test
  void getAllReviews_returnsCombinedFilmReviewList() throws Exception {
    Film film = new Film();
    film.setId(1L);
    film.setTitle("Test Movie");

    Review review = new Review();
    review.setId(5L);
    review.setRating(4);
    review.setReview("Good");
    review.setUserReviewedId("user123");
    review.setDateReviewed(new Date(System.currentTimeMillis()));
    review.setFilm(film);

    when(reviewService.getAllReviews()).thenReturn(List.of(review));
    when(filmService.getFilm(anyLong())).thenReturn(Optional.of(film));

    mockMvc.perform(get("/api/film-reviews"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(5))
        .andExpect(jsonPath("$[0].title").value("Test Movie"))
        .andExpect(jsonPath("$[0].userReviewedId").value("user123"));
  }
}
