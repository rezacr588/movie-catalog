package com.example.moviecatalog.expceptions;

/**
 * @author Reza Zeraat
 * 
 *         {@link DirectorNotFoundException} is a custom exception class that is thrown when a director is not found.
 * 
 */
public class DirectorNotFoundException extends RuntimeException {
  public DirectorNotFoundException(Long id) {
    super("Could not find the Director" + id.toString());
  }
}
