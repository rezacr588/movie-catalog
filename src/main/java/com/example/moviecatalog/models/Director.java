package com.example.moviecatalog.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * @author Reza Zeraat
 * 
 *         {@link Director} is a model class that represents a director.
 */
@Entity
@Table(name = "directors")
public class Director {
    /**
     * The id field is used to store the id of a director.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * The name field is used to store the name of a director.
     */
    @Column
    @NotBlank(message = "Name is mandatory")
    private String name;
    
    public Long getId() {
        return this.id;
    }

    public Long setId(Long id) {
        return this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}