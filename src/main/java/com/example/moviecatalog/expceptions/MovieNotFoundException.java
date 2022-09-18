package com.example.moviecatalog.expceptions;

/**
 * @author Reza Zeraat
 * 
 *         {@link DirectorNotFoundException} is a custom exception class that is thrown when a director is not found.
 * 
 */
public class MovieNotFoundException extends RuntimeException {
  public MovieNotFoundException(Long id) {
    super("Could not find the movie" + id.toString());
  }
}
