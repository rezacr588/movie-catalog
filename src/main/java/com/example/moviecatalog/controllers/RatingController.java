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

/**
 * @author Reza Zeraat
 * 
 *         {@link RatingController} is a controller class that provides methods to interact with the {@link RatingRepository} class.
 * 
 */
@RestController
public class RatingController {
  /**
   * The RatingController() constructor is used to initialize the ratingRepository.
   */
  private final RatingRepository repository;

  /**
   * @param repository
   * 
   *        The RatingController() constructor is used to initialize the ratingRepository.
   */
  public RatingController(RatingRepository repository) {
    this.repository = repository;
  }

  /**
   * @return List<Rating>
   * 
   *         The all() method is used to get all ratings from database.
   */
  @GetMapping("/ratings")
  List<Rating> all() {
    return repository.findAll();
  }

  /**
   * @param newRating
   * @return Rating
   * 
   *         The create() method is used to save a rating to the database.
   */
  @PostMapping("/ratings")
  public
  Rating newRating(@RequestBody Rating newRating) {
    return repository.save(newRating);
  }

  /**
   * @param id
   * @return Rating
   * 
   *         The one() method is used to get a rating by id from the database.
   */
  @GetMapping("/ratings/{id}")
  Rating one(@PathVariable Long id) {
    return repository.findById(id).orElseThrow(() -> new RatingNotFoundException(id));
  }

  /**
   * @param id
   * @param newRating
   * @return Rating
   * 
   *         The replaceRating() method is used to update a rating by id in the database.
   */
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

  /**
   * @param id
   * 
   *         The deleteRating() method is used to delete a rating by id from the database.
   */
  @DeleteMapping("/ratings/{id}")
  String deleteOne(@PathVariable Long id) {
    repository.deleteById(id);
    return "Rating is deleted successsfully";
  }

  /**
   * @param id
   * @return Rating
   * 
   *         The deleteRating() method is used to delete a rating by id from the database.
   */
  @DeleteMapping("/ratings")
  String deleteAll() {
    repository.deleteAll();
    return "All ratings are deleted successsfully";
  }
}
