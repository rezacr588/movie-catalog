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

@RestController
public class DirectorController {
  private final DirectorRepository repository;

  public DirectorController(DirectorRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/directors")
  List<Director> all() {
    return repository.findAll();
  }

  @PostMapping("/directors")
  Director newDirector(@RequestBody Director newDirector) {
    System.out.println(newDirector.getName());
    return repository.save(newDirector);
  }

  @GetMapping("/directors/{id}")
  Director one(@PathVariable Long id) {
    return repository.findById(id).orElseThrow(() -> new DirectorNotFoundException(id));
  }

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

  @DeleteMapping("/directors/{id}")
  String deleteOne(@PathVariable Long id) {
    repository.deleteById(id);
    return "Director is deleted successsfully";
  }

  // create a route to delete all directors
  @DeleteMapping("/directors") 
  String deleteAll() {
    repository.deleteAll();
    return "All directors are deleted successsfully";
  }

}
