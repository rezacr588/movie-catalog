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

/**
 * @author Reza Zeraat
 * 
 *         {@link MovieController} is a controller class that provides methods to interact with the {@link MovieService} class.
 * 
 */
@RestController
@RequestMapping("/api")
public class MovieController {
  /**
   * The MovieController() constructor is used to initialize the movieService.
   */
  @Autowired
  MovieService movieService;

  /**
   * @return List<Movie>
   * 
   *         The all() method is used to get all movies from database.
   */
  @GetMapping(value="/movies")
  public List<Movie> readMovies() {
    return movieService.all();
  }

  /**
   * @param movie
   * @return Movie
   * 
   *         The create() method is used to save a movie to the database.
   */
  @PostMapping(value = "/movies")
  @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
  public Movie createMovie(@RequestBody Movie newMovie) {
    return movieService.create(newMovie);
  }
  
  /**
   * @param id
   * @return Movie
   * 
   *        The one() method is used to get a movie by id from the database.
   */
  @GetMapping(value = "/movies/{id}")
  public Movie one(@PathVariable Long id) {
    return movieService.one(id);
  }

  /**
   * @param id
   * @param movie
   * @return Movie
   * 
   *         The update() method is used to update a movie by id in the database.
   */
  @PutMapping(value="/movies/{id}")
  public Movie replaceMovie(@RequestBody Movie newMovie,@PathVariable Long id) {
    return movieService.update(newMovie, id);
  }

  /**
   * @param id
   * 
   *         The delete() method is used to delete a movie by id from the database.
   */
  @DeleteMapping(value="/movies/{id}")
  public String deleteOne(@PathVariable Long id) {
    return movieService.delete(id);
  }

  /**
   * @param id
   * @return Movie
   * 
   *         The delete() method is used to delete a movie by id from the database.
   */
  @DeleteMapping(value="/movies")
  public String deleteAll() {
    return movieService.deleteAll();
  }  
}
