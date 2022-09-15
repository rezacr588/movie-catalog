package com.example.moviecatalog.controllers;

import java.net.URI;
import java.util.List;

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

  // create a custome route for create movie entry 
  @PostMapping(
    value = "/createMovie",
    consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  public ResponseEntity<Movie> postBody(@RequestBody CreateMovieEntry createMovieEntry) {
    Movie newMovie = new Movie();
    Rating newRating = new Rating();
    newMovie.setName(createMovieEntry.getName());
    newRating.setNumber(createMovieEntry.getNumber());
    
    Movie persistedMovie = repository.save(newMovie);
    return ResponseEntity
        .created(URI
                  .create(String.format("/movies/%s", persistedMovie.getId())))
        .body(persistedMovie);
    }
  
}
