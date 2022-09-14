package com.example.moviecatalog.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.moviecatalog.expceptions.MovieNotFoundException;
import com.example.moviecatalog.models.Movie;
import com.example.moviecatalog.repositories.MovieRepository;

@RestController
public class MovieController {
  private final MovieRepository repository;

  MovieController(MovieRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/movies")
  List<Movie> all() {
    return repository.findAll();
  }

  @PostMapping("/movies")
  Movie newMovie(@RequestBody Movie newMovie) {
    return repository.save(newMovie);
  }

  @GetMapping("/mouvies/{id}")
  Movie one(@PathVariable Long id) {
    return repository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
  }
}
