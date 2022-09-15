/*
 * Copyright 2014-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.moviecatalog.WebLayer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MvcResult;

import com.example.moviecatalog.AbstractTest;
import com.example.moviecatalog.controllers.CreateMovieEntry;
import com.example.moviecatalog.models.Movie;
import com.fasterxml.jackson.core.JsonProcessingException;


@SpringBootTest
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
public class MovieWebLayerTest extends AbstractTest {

  @BeforeEach
  public void setUp(RestDocumentationContextProvider restDocumentation) {
    super.setUp(restDocumentation);
  }

  protected String createMovie(String name) throws JsonProcessingException {
    Movie createMovie = new Movie();
    createMovie.setName(name);
    String inputJson = this.mapToJson(createMovie);
    return inputJson;
  }

  @Test
  public void shouldReturnAllMovies() throws Exception {
    this.mockMvc.perform(get("/movies"))
        .andExpect(status().isOk())
        .andDo(document("getAllMovies"));
  }

  @Test
  public void shouldReturnOneMovie() throws Exception {
    MvcResult mvcCreateResult = this.mockMvc.perform(post("/movies")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("utf-8")
        .content(createMovie("Reza"))).andReturn();

    String content = mvcCreateResult.getResponse().getContentAsString();

    Long movieId = super.mapFromJson(content, Movie.class).getId();

    this.mockMvc.perform(get("/movies/" + movieId))
        .andExpect(status().isOk())
        .andDo(document("getOneMovies"));
  }

  @Test
  public void shouldCreateMovie() throws Exception {
    this.mockMvc.perform(post("/movies")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("utf-8")
        .content(this.createMovie("Reza")))
        .andExpect(status().isOk())
        .andDo(document("createMovie"));
  }

  @Test
  public void shouldCreateEntryMovie() throws Exception {
    CreateMovieEntry createMovieEntry = new CreateMovieEntry();
    createMovieEntry.setName("Justice League");
    createMovieEntry.setNumber(8);

    Set<String> hash_Set = new HashSet<String>();

    hash_Set.add("Nolan");
    hash_Set.add("Snyder");

    createMovieEntry.setDirectors(hash_Set);

    String inputJson = this.mapToJson(createMovieEntry);

    this.mockMvc.perform(post("/createMovie")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("utf-8")
        .content(inputJson))
        .andExpect(status().isCreated())
        .andDo(document("createEntryMovie"));
  }
  
  @Test
  public void shouldDeleteMovie() throws Exception {
    MvcResult createdMovie = this.mockMvc.perform(post("/movies")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("utf-8")
        .content(this.createMovie("Reza"))).andReturn();
    String movieContent = createdMovie.getResponse().getContentAsString();
    String uri = "/movies/" + super.mapFromJson(movieContent, Movie.class).getId();
    MvcResult mvcResult = this.mockMvc.perform(delete(uri)).andExpect(status().isOk())
        .andDo(document("deleteOneMovie")).andReturn();
    int status = mvcResult.getResponse().getStatus();
    assertEquals(200, status);
    String content = mvcResult.getResponse().getContentAsString();
    assertEquals(content, "Movie is deleted successsfully");
  }

  @Test
  public void shouldUpdateMovie() throws Exception {
    Movie createdMovie = new Movie();
    createdMovie.setName("Reza");
    String inputJson = super.mapToJson(createdMovie);

    MvcResult mvcCreateResult = this.mockMvc.perform(post("/movies")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("utf-8")
        .content(inputJson)).andReturn();
    
    String prevContent = mvcCreateResult.getResponse().getContentAsString();

    Movie updatedMovie = new Movie();
    updatedMovie.setName("Ali");
    String updatedInputJson = super.mapToJson(updatedMovie);

    MvcResult mvcUpdateResult = this.mockMvc.perform(put("/movies/"+ this.mapFromJson(prevContent, Movie.class).getId())
        .contentType(MediaType.APPLICATION_JSON_VALUE).content(updatedInputJson)).andExpect(status().isOk())
        .andDo(document("updateOneMovies")).andReturn();
    
    int status = mvcUpdateResult.getResponse().getStatus();
    assertEquals(200, status);
    String content = mvcUpdateResult.getResponse().getContentAsString();
    assertNotEquals(prevContent, content);
  }
}