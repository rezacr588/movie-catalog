package com.example.moviecatalog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.moviecatalog.models.Movie;
import com.example.moviecatalog.services.MovieService;

@RestController
@RequestMapping("/api")
public class MovieController {
  @Autowired
  MovieService movieService;

  @GetMapping(value="/movies")
  public List<Movie> readMovies() {
    return movieService.all();
  }

  @PostMapping(value = "/movies")
  @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
  public Movie createMovie(@RequestBody Movie newMovie) {
    return movieService.create(newMovie);
  }

  @GetMapping(value = "/movies/{id}")
  public Movie one(@PathVariable Long id) {
    return movieService.one(id);
  }

  @PutMapping(value="/movies/{id}")
  public Movie replaceMovie(@RequestBody Movie newMovie,@PathVariable Long id) {
    return movieService.update(newMovie, id);
  }

  @DeleteMapping(value="/movies/{id}")
  public String deleteOne(@PathVariable Long id) {
    return movieService.delete(id);
  }

  @DeleteMapping(value="/movies")
  public String deleteAll() {
    return movieService.deleteAll();
  }  
}
