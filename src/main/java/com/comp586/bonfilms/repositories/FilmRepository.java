package com.comp586.bonfilms.repositories;

import com.comp586.bonfilms.entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
}
