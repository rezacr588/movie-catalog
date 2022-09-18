package com.example.moviecatalog.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Reza Zeraat
 * 
 *         {@link Rating} is a model class that represents a rating.
 */
@Entity
@Table(name = "ratings")
public class Rating {
    /**
     * The id field is used to store the id of a rating.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public Long getId() {
        return this.id;
    }

    public Long setId(Long id) {
        return this.id = id;
    }
    
    /**
     * The number field is used to store the rating of a movie.
     */
    @Column
    @NotNull(message = "Number is mandatory")
    private Integer number;

    public Integer getNumber() {
        return this.number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}