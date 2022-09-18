package com.example.moviecatalog.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.moviecatalog.expceptions.DirectorNotFoundException;
import com.example.moviecatalog.models.Director;
import com.example.moviecatalog.repositories.DirectorRepository;

/**
 * @author Reza Zeraat
 * 
 *         {@link DirectorController} is a controller class that provides methods to interact with the {@link DirectorRepository} class.
 * 
 */
@RestController
public class DirectorController {
  /**
   * The DirectorController() constructor is used to initialize the directorRepository.
   */
  private final DirectorRepository repository;

  /**
   * @param repository
   * 
   *        The DirectorController() constructor is used to initialize the directorRepository.
   */
  public DirectorController(DirectorRepository repository) {
    this.repository = repository;
  }

  /**
   * @return List<Director>
   * 
   *         The all() method is used to get all directors from database.
   */
  @GetMapping("/directors")
  List<Director> all() {
    return repository.findAll();
  }

  /**
   * @param newDirector
   * @return Director
   * 
   *         The newDirector() method is used to save a director to the database.
   */
  @PostMapping("/directors")
  Director newDirector(@RequestBody Director newDirector) {
    System.out.println(newDirector.getName());
    return repository.save(newDirector);
  }

  /**
   * @param id
   * @return Director
   * 
   *         The one() method is used to get a director from the database.
   */
  @GetMapping("/directors/{id}")
  Director one(@PathVariable Long id) {
    return repository.findById(id).orElseThrow(() -> new DirectorNotFoundException(id));
  }

  /**
   * @param id
   * @param newDirector
   * @return Director
   * 
   *         The replaceDirector() method is used to update a director in the database.
   */
  @PutMapping("/directors/{id}")
  Director replaceMovie(@RequestBody Director newDirector,@PathVariable Long id) {
    return repository.findById(id).map(movie -> {
      movie.setName(newDirector.getName());
      return repository.save(movie);
    }).orElseGet(() -> {
      newDirector.setId(id);
      return repository.save(newDirector);
    });
  }

  /**
   * @param id
   * 
   *         The deleteDirector() method is used to delete a director from the database.
   */
  @DeleteMapping("/directors/{id}")
  String deleteOne(@PathVariable Long id) {
    repository.deleteById(id);
    return "Director is deleted successsfully";
  }

  /**
   * @param name
   * @return List<Director>
   * 
   *         The findByName() method is used to find a director by name from the database.
   */
  @DeleteMapping("/directors") 
  String deleteAll() {
    repository.deleteAll();
    return "All directors are deleted successsfully";
  }

}
