package com.example.moviecatalog.controllers;

import java.util.Set;

public class CreateMovieEntry {
  private Integer number;

  private String name;

  private Set<String> directors;

  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }

  public Integer getNumber() {
    return number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  public Set<String> getDirectors() {
    return directors;
  }

  public void setDirectors(Set<String> directors) {
    this.directors = directors;
  }
}
