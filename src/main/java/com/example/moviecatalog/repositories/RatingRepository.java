package com.example.moviecatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.moviecatalog.models.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {}