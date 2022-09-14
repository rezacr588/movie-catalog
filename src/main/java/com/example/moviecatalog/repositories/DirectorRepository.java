package com.example.moviecatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.moviecatalog.models.Director;

public interface DirectorRepository extends JpaRepository<Director, Long> {}