package com.comp586.bonfilms.controllers;

import com.comp586.bonfilms.entities.Review;
import com.comp586.bonfilms.models.ReviewUpdateRequest;
import com.comp586.bonfilms.services.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    review.setId(1L);
    review.setRating(4);
    review.setReview("Nice film");

    when(reviewService.getReview(eq(1L))).thenReturn(Optional.of(review));

    mockMvc.perform(get("/api/reviews/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.rating").value(4))
        .andExpect(jsonPath("$.review").value("Nice film"));
  }

  @Test
  void getReviewById_returnsNotFoundWhenMissing() throws Exception {
    when(reviewService.getReview(eq(1L))).thenReturn(Optional.empty());

    mockMvc.perform(get("/api/reviews/1"))
        .andExpect(status().isNotFound());
  }

  @Test
  void createReview_returnsCreatedReview() throws Exception {
    Review review = new Review();
    review.setRating(5);
    review.setReview("Fantastic");

    Review saved = new Review();
    saved.setId(2L);
    saved.setRating(5);
    saved.setReview("Fantastic");

    when(reviewService.saveReview(any(Review.class))).thenReturn(saved);

    mockMvc.perform(post("/api/reviews")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(review)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(2))
        .andExpect(jsonPath("$.review").value("Fantastic"));
  }

  @Test
  void updateReview_returnsUpdatedReview() throws Exception {
    Review existing = new Review();
    existing.setId(1L);
    existing.setRating(3);
    existing.setReview("Okay");

    Review updated = new Review();
    updated.setId(1L);
    updated.setRating(5);
    updated.setReview("Excellent");

    when(reviewService.getReview(eq(1L))).thenReturn(Optional.of(existing));
    when(reviewService.updateReview(any(Review.class))).thenReturn(updated);

    mockMvc.perform(put("/api/reviews/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(new ReviewUpdateRequest(5, "Excellent"))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.rating").value(5))
        .andExpect(jsonPath("$.review").value("Excellent"));
  }

  @Test
  void deleteReview_returnsNoContentOnDelete() throws Exception {
    Review review = new Review();
    review.setId(1L);

    when(reviewService.getReview(eq(1L))).thenReturn(Optional.of(review));

    mockMvc.perform(delete("/api/reviews/1"))
        .andExpect(status().isNoContent());
  }
}
