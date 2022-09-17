package com.example.moviecatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.moviecatalog.models.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
  // create a query to find all movies on a joined table by a given director_id (hint: use the director's name as a parameter) 
  @Query(
      value = "SELECT * FROM movies m JOIN movie_director md ON m.id = md.movie_id JOIN directors d ON md.director_id = d.id WHERE d.id = ?1",
      nativeQuery = true
    )
  Iterable<Movie> findAllMoviesByDirector(Long directorName);

  // create a query to find all movies higher than a given rating in one to one (hint: use the rating as a parameter)
  @Query(
      value = "SELECT * FROM movies LEFT JOIN ratings ON movies.rating_id = ratings.id WHERE ratings.number > ?1",
      nativeQuery = true
    )
  Iterable<Movie> findAllMoviesByRating(int i);

}