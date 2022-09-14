package com.example.moviecatalog.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    System.out.println(newMovie.getName());
    return repository.save(newMovie);
  }

  @GetMapping("/movies/{id}")
  Movie one(@PathVariable Long id) {
    return repository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
  }

  @PutMapping("/movies/{id}")
  Movie replaceMovie(@RequestBody Movie newMovie,@PathVariable Long id) {
    return repository.findById(id).map(movie -> {
      movie.setName(newMovie.getName());
      return repository.save(movie);
    }).orElseGet(() -> {
      newMovie.setId(id);
      return repository.save(newMovie);
    });
  }

  @DeleteMapping("/movies/{id}")
  void deleteOne(@PathVariable Long id) {
    repository.deleteById(id);
  }
}
