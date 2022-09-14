package com.example.moviecatalog.expceptions;

public class DirectorNotFoundException extends RuntimeException {
  public DirectorNotFoundException(Long id) {
    super("Could not find the Director" + id.toString());
  }
}
