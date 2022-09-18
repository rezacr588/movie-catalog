package com.example.moviecatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.moviecatalog.models.Rating;

/**
 * @author Reza Zeraat
 * 
 *        {@link RatingRepository} is a repository class that provides methods to interact with the database.
 */
public interface RatingRepository extends JpaRepository<Rating, Long> {}