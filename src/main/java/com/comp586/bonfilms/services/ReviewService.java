package com.comp586.bonfilms.services;

import com.comp586.bonfilms.entities.Review;
import java.util.List;
import java.util.Optional;


public interface ReviewService {

    List<Review> getAllReviews();

    Optional<Review> getReview(Long id);

    Review saveReview(Review review);

    Review updateReview(Review review);

    void deleteReview(Long id);
}
