package com.comp586.bonfilms.services;

import com.comp586.bonfilms.entities.Film;
import java.util.List;
import java.util.Optional;


public interface FilmService {

    List<Film> getAllFilms();

    Optional<Film> getFilm(Long id);

    Film saveFilm(Film film);
}
