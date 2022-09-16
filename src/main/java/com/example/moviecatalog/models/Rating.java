package com.example.moviecatalog.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public Long getId() {
        return this.id;
    }

    public Long setId(Long id) {
        return this.id = id;
    }
    
    @Column
    @NotBlank(message = "Number is mandatory")
    private Integer number;

    public Integer getNumber() {
        return this.number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @OneToOne(mappedBy = "rating")
    @NotNull(message = "Movie is mandatory")
    private Movie rating;

    public Movie getRating() {
        return this.rating;
    }

    public void setRating(Movie rating) {
        this.rating = rating;
    }
}