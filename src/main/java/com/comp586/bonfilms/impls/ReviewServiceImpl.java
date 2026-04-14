package com.comp586.bonfilms.impls;

import com.comp586.bonfilms.entities.Review;
import com.comp586.bonfilms.repositories.ReviewRepository;
import com.comp586.bonfilms.services.ReviewService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;


@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Transactional
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Transactional
    public Optional<Review> getReview(Long id) {
        return reviewRepository.findById(id);
    }

    @Transactional
    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    @Transactional
    public Review updateReview(Review review) {
        return reviewRepository.save(review);
    }

    @Transactional
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
