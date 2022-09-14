package com.example.moviecatalog.controllers;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.moviecatalog.models.Movie;

interface MovieRepository extends JpaRepository<Movie, Long>