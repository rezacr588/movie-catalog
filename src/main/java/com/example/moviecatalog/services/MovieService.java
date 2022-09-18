package com.example.moviecatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.moviecatalog.expceptions.MovieNotFoundException;
import com.example.moviecatalog.models.Movie;
import com.example.moviecatalog.repositories.MovieRepository;

@Service
public class MovieService {
  @Autowired
  MovieRepository repository;

  public List<Movie> all() {
    return repository.findAll();
  }

  public Movie create(Movie newMovie) {
    return repository.save(newMovie);
  }

  public Movie one(Long id) {
    return repository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
  }

  public Movie update(Movie newMovie,Long id) {
    return repository.findById(id).map(movie -> {
      movie.setName(newMovie.getName());
      movie.setDirectors(newMovie.getDirectors());
      movie.setRating(newMovie.getRating());
      return repository.save(movie);
    }).orElseThrow(() -> new MovieNotFoundException(id));
  }

  public String delete(Long id) {
    repository.deleteById(id);
    return "Movie is deleted successsfully";
  }

  public String deleteAll() {
    repository.deleteAll();
    return "All movies are deleted successsfully";
  }  
}
