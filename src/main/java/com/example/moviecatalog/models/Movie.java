package com.example.moviecatalog.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Column
    @NotBlank(message = "Name is mandatory")
    private String name;

    @ManyToMany
    @NotNull(message = "Directors is mandatory")
    Set<Director> directors;

    @OneToOne  
    private Rating rating;

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDirectors(Set<Director> directors) {
        this.directors = directors;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }
    
    public Set<Director> getDirectors() {
        return directors;
    }

    public Rating getRating() {
        return this.rating;
    }
}