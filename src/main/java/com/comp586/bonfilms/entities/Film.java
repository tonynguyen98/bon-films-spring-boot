package com.comp586.bonfilms.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import java.util.List;
import lombok.Data;


@Entity
@Table(name = "films")
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "genre")
    private String genre;

    @Column(name = "studio")
    private String studio;

    @Column(name = "director")
    private String director;

    @Column(name = "top_billing")
    private String topBilling;

    @Column(name = "synopsis")
    private String synopsis;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "film", orphanRemoval = true)
    private List<Review> reviews;

}
