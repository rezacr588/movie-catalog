package com.example.moviecatalog.expceptions;

/**
 * @author Reza Zeraat
 * 
 *         {@link DirectorNotFoundException} is a custom exception class that is thrown when a director is not found.
 * 
 */
public class RatingNotFoundException extends RuntimeException {
  public RatingNotFoundException(Long id) {
    super("Could not find the Rating" + id.toString());
  }
}
