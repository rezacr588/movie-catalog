package com.example.moviecatalog.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.moviecatalog.expceptions.RatingNotFoundException;
import com.example.moviecatalog.models.Rating;
import com.example.moviecatalog.repositories.RatingRepository;

@RestController
public class RatingController {
  private final RatingRepository repository;

  RatingController(RatingRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/ratings")
  List<Rating> all() {
    return repository.findAll();
  }

  @PostMapping("/ratings")
  Rating newRating(@RequestBody Rating newRating) {
    return repository.save(newRating);
  }

  @GetMapping("/ratings/{id}")
  Rating one(@PathVariable Long id) {
    return repository.findById(id).orElseThrow(() -> new RatingNotFoundException(id));
  }

  @PutMapping("/ratings/{id}")
  Rating replaceRating(@RequestBody Rating newRating,@PathVariable Long id) {
    return repository.findById(id).map(rating -> {
      rating.setNumber(newRating.getNumber());
      return repository.save(rating);
    }).orElseGet(() -> {
      newRating.setId(id);
      return repository.save(newRating);
    });
  }

  @DeleteMapping("/ratings/{id}")
  String deleteOne(@PathVariable Long id) {
    repository.deleteById(id);
    return "Rating is deleted successsfully";
  }

  @DeleteMapping("/ratings")
  String deleteAll() {
    repository.deleteAll();
    return "All ratings are deleted successsfully";
  }
}
