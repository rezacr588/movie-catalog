package com.example.moviecatalog.controllers;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.moviecatalog.models.Movie;
import com.example.moviecatalog.repositories.MovieRepository;

/**
 * @author Reza Zeraat
 * 
 *         {@link SearchController} is a controller class that provides methods to search for movies.
 * 
 */
@RestController
public class SearchController {
    
    private MovieRepository movieRepository;
    
    /**
     * @param movieRepository
     * 
     * The SearchController() constructor is used to initialize the movieRepository.
     *
     */
    public SearchController(MovieRepository movieRepository) {
      this.movieRepository = movieRepository;
    }

    /**
     * @param director
     * @param rating
     * @return List<Movie>
     * 
     * The searchMovies() method is used to search for movies by params.
     */
    @GetMapping("/search")
    public Iterable<Movie> searchMovies(
      @RequestParam(required = false, name="director") String director,
      @RequestParam(required = false, name="rating") String rating
    ) {

      /**
       * 1. Check if the director param is not empty
       * 2. Check if the rating param is not empty
       * 3. If both params are not empty, return movies by director and more than the rating param
       * 4. If the director param is not empty, return movies by director
       * 5. If the rating param is not empty, return movies by rating
       * 6. If both params are empty, return all movies
       */
      if (director != null && StringUtils.hasLength(director)) {
        return movieRepository.findAllMoviesByDirector(Long.parseLong(director));
      }
      
      if (rating != null && StringUtils.hasLength(rating)) {
        return movieRepository.findAllMoviesByRating(Integer.parseInt(rating));
      }

      return movieRepository.findAll();
    }
}
