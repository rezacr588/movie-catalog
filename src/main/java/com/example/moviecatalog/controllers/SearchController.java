package com.example.moviecatalog.controllers;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.moviecatalog.models.Movie;
import com.example.moviecatalog.repositories.MovieRepository;

@RestController
public class SearchController {
    
    private MovieRepository movieRepository;
    
    public SearchController(MovieRepository movieRepository) {
      this.movieRepository = movieRepository;
    }
    
    public MovieRepository getMovieRepository() {
      return movieRepository;
    }
    
    public void setMovieRepository(MovieRepository movieRepository) {
      this.movieRepository = movieRepository;
    }

    @GetMapping("/search")
    public Iterable<Movie> getTickets(
      @RequestParam(required = false, name="director") String director,
      @RequestParam(required = false, name="rating") String rating
    ) {

      if (director != null && !StringUtils.isEmpty(director)) {
        return movieRepository.findAllMoviesByDirector(Long.parseLong(director));
      }
      
      if (rating != null && !StringUtils.isEmpty(rating)) {
        return movieRepository.findAllMoviesByRating(Integer.parseInt(rating));
      }

      return movieRepository.findAll();
    }
}
