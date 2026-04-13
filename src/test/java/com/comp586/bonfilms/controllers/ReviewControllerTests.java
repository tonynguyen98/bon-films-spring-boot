package com.comp586.bonfilms.controllers;

import com.comp586.bonfilms.entities.Review;
import com.comp586.bonfilms.services.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewController.class)
class ReviewControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private ReviewService reviewService;

  @Test
  void getReviewById_returnsReviewWhenFound() throws Exception {
    Review review = new Review();
    review.setId(1);
    review.setRating(4);
    review.setReview("Nice film");

    when(reviewService.getReview(eq(1))).thenReturn(review);

    mockMvc.perform(get("/api/review/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.rating").value(4))
        .andExpect(jsonPath("$.review").value("Nice film"));
  }

  @Test
  void getReviewById_returnsNotFoundWhenMissing() throws Exception {
    when(reviewService.getReview(eq(1))).thenReturn(null);

    mockMvc.perform(get("/api/review/1"))
        .andExpect(status().isNotFound());
  }

  @Test
  void createReview_returnsCreatedReview() throws Exception {
    Review review = new Review();
    review.setRating(5);
    review.setReview("Fantastic");

    Review saved = new Review();
    saved.setId(2);
    saved.setRating(5);
    saved.setReview("Fantastic");

    when(reviewService.saveReview(any(Review.class))).thenReturn(saved);

    mockMvc.perform(post("/api/review/create")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(review)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(2))
        .andExpect(jsonPath("$.review").value("Fantastic"));
  }

  @Test
  void updateReview_returnsUpdatedReview() throws Exception {
    Review existing = new Review();
    existing.setId(1);
    existing.setRating(3);
    existing.setReview("Okay");

    Review updated = new Review();
    updated.setId(1);
    updated.setRating(5);
    updated.setReview("Excellent");

    when(reviewService.getReview(eq(1))).thenReturn(existing);
    when(reviewService.updateReview(any(Review.class))).thenReturn(updated);

    mockMvc.perform(put("/api/review/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(Map.of("rating", "5", "review", "Excellent"))))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.rating").value(5))
        .andExpect(jsonPath("$.review").value("Excellent"));
  }

  @Test
  void deleteReview_returnsAcceptedOnDelete() throws Exception {
    Review review = new Review();
    review.setId(1);

    when(reviewService.getReview(eq(1))).thenReturn(review);
    when(reviewService.deleteReview(eq(review))).thenReturn(null);

    mockMvc.perform(delete("/api/review/1"))
        .andExpect(status().isAccepted());
  }
}
