package com.comp586.bonfilms.impls;

import com.comp586.bonfilms.entities.Film;
import com.comp586.bonfilms.repositories.FilmRepository;
import com.comp586.bonfilms.services.FilmService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;


@Service
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;

    public FilmServiceImpl(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Transactional
    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    @Transactional
    public Optional<Film> getFilm(Long id) {
        return filmRepository.findById(id);
    }

    @Transactional
    public Film saveFilm(Film film) {
        return filmRepository.save(film);
    }
}
