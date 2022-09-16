package com.example.moviecatalog.controllers;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.tomcat.jni.Directory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.moviecatalog.expceptions.MovieNotFoundException;
import com.example.moviecatalog.models.Director;
import com.example.moviecatalog.models.Movie;
import com.example.moviecatalog.models.Rating;
import com.example.moviecatalog.repositories.MovieRepository;

@RestController
public class MovieController {
  private final MovieRepository repository;

  public MovieController(MovieRepository repository) {
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

  @GetMapping("/movies/{id}")
  Movie one(@PathVariable Long id) {
    return repository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
  }

  @PutMapping("/movies/{id}")
  Movie replaceMovie(@RequestBody Movie newMovie,@PathVariable Long id) {
    return repository.findById(id).map(movie -> {
      movie.setName(newMovie.getName());
      return repository.save(movie);
    }).orElseThrow(() -> new MovieNotFoundException(id));
  }

  @DeleteMapping("/movies/{id}")
  String deleteOne(@PathVariable Long id) {
    repository.deleteById(id);
    return "Movie is deleted successsfully";
  }

  // create a route to delete all movies
  @DeleteMapping("/movies") 
  String deleteAll() {
    repository.deleteAll();
    return "All movies are deleted successsfully";
  }  
}
