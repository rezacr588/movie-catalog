package com.example.moviecatalog.expceptions;

public class RatingNotFoundException extends RuntimeException {
  public RatingNotFoundException(Long id) {
    super("Could not find the Rating" + id.toString());
  }
}
