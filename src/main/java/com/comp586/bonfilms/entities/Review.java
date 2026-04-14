package com.comp586.bonfilms.entities;

import jakarta.persistence.*;
import java.sql.Date;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;


@Entity
@Table(name = "reviews")
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "rating")
    private int rating;

    @Column(name = "review")
    private String review;

    @Column(name = "user_reviewed_id")
    private String userReviewedId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;

    @Column(name = "date_reviewed")
    @CreationTimestamp
    private Date dateReviewed;
}
