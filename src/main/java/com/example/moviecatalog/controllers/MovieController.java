package com.example.moviecatalog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.moviecatalog.models.Movie;
import com.example.moviecatalog.services.MovieService;

@RestController
@RequestMapping("/api")
public class MovieController {
  @Autowired
  MovieService movieService;

  @RequestMapping(value="/movies", method=RequestMethod.GET)
  public List<Movie> readMovies() {
    return movieService.all();
  }

  @RequestMapping(value="/movies", method=RequestMethod.GET)
  public Movie createMovie(@RequestBody Movie newMovie) {
    return movieService.create(newMovie);
  }

  @GetMapping("/movies/{id}")
  public Movie one(@PathVariable Long id) {
    return movieService.one(id);
  }

  @RequestMapping(value="/movies/{id}", method=RequestMethod.PUT)
  public Movie replaceMovie(@RequestBody Movie newMovie,@PathVariable Long id) {
    return movieService.update(newMovie, id);
  }

  @RequestMapping(value="/movies/{id}", method=RequestMethod.DELETE)
  public String deleteOne(@PathVariable Long id) {
    return movieService.delete(id);
  }

  @RequestMapping(value="/movies", method=RequestMethod.DELETE)
  public String deleteAll() {
    return movieService.deleteAll();
  }  
}
