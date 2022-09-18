package com.example.moviecatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.moviecatalog.expceptions.MovieNotFoundException;
import com.example.moviecatalog.models.Movie;
import com.example.moviecatalog.repositories.MovieRepository;

/**
 * @author Reza Zeraat
 * 
 * {@link MovieService} is a service class that provides methods to interact with the {@link MovieRepository} class.
 * 
 */

@Service
public class MovieService {
  @Autowired
  MovieRepository repository;

  /**
   * 
   * @return List<Movie>
   *         The all() method is used to get all movies from database.
   */
  public List<Movie> all() {
    return repository.findAll();
  }

  /**
   * @param movie
   * @return Movie
   * 
   *         The create() method is used to save a movie to the database.
   */
  public Movie create(Movie newMovie) {
    return repository.save(newMovie);
  }

  /**
   * @param id
   * @return Movie
   * 
   *         The findById() method is used to find a movie by its id.
   */
  public Movie one(Long id) {
    return repository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
  }

  /**
   * @param id
   * @param movie
   * @return Movie
   * 
   *         The update() method is used to update a movie by its id.
   */
  public Movie update(Movie newMovie,Long id) {
    return repository.findById(id).map(movie -> {
      movie.setName(newMovie.getName());
      movie.setDirectors(newMovie.getDirectors());
      movie.setRating(newMovie.getRating());
      return repository.save(movie);
    }).orElseThrow(() -> new MovieNotFoundException(id));
  }

  /**
   * @param id
   * 
   *           The delete() method is used to delete a movie by its id.
   */
  public String delete(Long id) {
    repository.deleteById(id);
    return "Movie is deleted successsfully";
  }

  /**
   * 
   *           The deleteAll() method is used to delete all movie in the database.
   */
  public String deleteAll() {
    repository.deleteAll();
    return "All movies are deleted successsfully";
  }  
}
