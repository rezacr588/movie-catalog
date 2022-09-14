package com.example.moviecatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.moviecatalog.models.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {}