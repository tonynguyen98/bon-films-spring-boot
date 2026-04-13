package com.comp586.bonfilms.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "reviews")
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "rating")
    private int rating;

    @Column(name = "review")
    private String review;

    @Column(name = "user_reviewed_id")
    private String userReviewedId;

    @ManyToOne
    @JoinColumn(name = "film_id", nullable = false)
    // @Column(name = "film_id")
    private Film film;

    @Column(name = "date_reviewed")
    @CreationTimestamp
    private Date dateReviewed;
}
