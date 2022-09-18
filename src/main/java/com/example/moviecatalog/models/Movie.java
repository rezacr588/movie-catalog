package com.example.moviecatalog.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Reza Zeraat
 * 
 *         {@link Movie} is a model class that represents a movie.
 */
@Entity
@Table(name = "movies")
public class Movie {
    /**
     * The id field is used to store the id of a movie.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    /**
     * The name field is used to store the name of a movie.
     */
    @Column
    @NotBlank(message = "Name is mandatory")
    private String name;
    /**
     * this is a {@link ManyToMany} relationship between {@link Movie} and {@link Director}
     */
    @ManyToMany
    @JoinTable(name = "movie_director",
            joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "director_id", referencedColumnName = "id")
        )
    @NotNull(message = "Directors are mandatory")
    Set<Director> directors;

    /**
     * this is a {@link OneToOne} relationship between {@link Movie} and {@link Rating}
     */
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