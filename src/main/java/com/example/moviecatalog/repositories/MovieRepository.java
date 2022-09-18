package com.example.moviecatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.moviecatalog.models.Movie;

/** 
 * @author Reza Zeraat
 * 
 * {@link MovieRepository} is a repository class that provides methods to interact with the database.
 */
public interface MovieRepository extends JpaRepository<Movie, Long> {
  
  /**
   * @param directorId
   * @return List<Movie>
   * 
   * The findAllMoviesByDirector() method is used to find all movies by a given director_id.
   */
  @Query(
      value = "SELECT * FROM movies m JOIN movie_director md ON m.id = md.movie_id JOIN directors d ON md.director_id = d.id WHERE d.id = ?1",
      nativeQuery = true
    )
  Iterable<Movie> findAllMoviesByDirector(Long directorId);

  /** 
   * @param number
   *  create a query to find all movies higher than a given rating in one to one (hint: use the rating as a parameter)
   */
  @Query(
      value = "SELECT * FROM movies LEFT JOIN ratings ON movies.rating_id = ratings.id WHERE ratings.number > ?1",
      nativeQuery = true
    )
  Iterable<Movie> findAllMoviesByRating(int i);

}