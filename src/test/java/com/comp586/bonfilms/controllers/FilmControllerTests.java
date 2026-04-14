package com.comp586.bonfilms.controllers;

import com.comp586.bonfilms.entities.Film;
import com.comp586.bonfilms.entities.Review;
import com.comp586.bonfilms.services.FilmService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(FilmController.class)
class FilmControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private FilmService filmService;

  @Test
  void getAllFilms_returnsList() throws Exception {
    Film film = new Film();
    film.setId(1L);
    film.setTitle("Test Movie");

    when(filmService.getAllFilms()).thenReturn(List.of(film));

    mockMvc.perform(get("/api/films"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].title").value("Test Movie"));
  }

  @Test
  void getFilmById_returnsFilmWhenFound() throws Exception {
    Film film = new Film();
    film.setId(1L);
    film.setTitle("Test Movie");

    when(filmService.getFilm(eq(1L))).thenReturn(Optional.of(film));

    mockMvc.perform(get("/api/films/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.title").value("Test Movie"));
  }

  @Test
  void getFilmById_returnsNotFoundWhenMissing() throws Exception {
    when(filmService.getFilm(eq(1L))).thenReturn(Optional.empty());

    mockMvc.perform(get("/api/films/1"))
        .andExpect(status().isNotFound());
  }

  @Test
  void createFilm_returnsCreatedFilm() throws Exception {
    Film film = new Film();
    film.setTitle("New Movie");

    Film saved = new Film();
    saved.setId(2L);
    saved.setTitle("New Movie");

    when(filmService.saveFilm(any(Film.class))).thenReturn(saved);

    mockMvc.perform(post("/api/films")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(film)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(2))
        .andExpect(jsonPath("$.title").value("New Movie"));
  }

  @Test
  void getFilmReviews_returnsReviewsForFilm() throws Exception {
    Film film = new Film();
    film.setId(1L);
    film.setTitle("Test Movie");

    Review review = new Review();
    review.setId(10L);
    review.setReview("Good movie");
    review.setRating(5);
    review.setFilm(film);

    film.setReviews(List.of(review));

    when(filmService.getFilm(eq(1L))).thenReturn(Optional.of(film));

    mockMvc.perform(get("/api/films/1/reviews"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(10))
        .andExpect(jsonPath("$[0].review").value("Good movie"))
        .andExpect(jsonPath("$[0].rating").value(5));
  }
}
