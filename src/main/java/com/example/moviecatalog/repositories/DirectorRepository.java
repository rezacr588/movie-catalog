package com.example.moviecatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.moviecatalog.models.Director;

/**
 * @author Reza Zeraat
 * 
 *         {@link DirectorRepository} is a repository class that provides methods to interact with the database.
 * 
 */
public interface DirectorRepository extends JpaRepository<Director, Long> {}