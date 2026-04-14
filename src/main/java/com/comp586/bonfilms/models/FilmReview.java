package com.comp586.bonfilms.models;

import java.sql.Date;


public record FilmReview(
        Long id,
        int rating,
        String review,
        String userReviewedId,
        String title,
        Date dateReviewed) {
}
