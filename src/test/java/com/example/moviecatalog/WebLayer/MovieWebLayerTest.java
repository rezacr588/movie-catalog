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

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MvcResult;

import com.example.moviecatalog.AbstractTest;
import com.example.moviecatalog.models.Movie;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;


@SpringBootTest
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
public class MovieWebLayerTest extends AbstractTest {

  private static final String URI = "/api/movies";

  public final String getUriID(String content) throws JsonParseException, JsonMappingException, IOException {
    return URI + "/" + super.mapFromJson(content, Movie.class).getId();
  }
  
  @Test
  public void shouldReturnAllMovies() throws Exception {
    this.mockMvc.perform(get(URI))
        .andExpect(status().isOk())
        .andDo(document("getAllMovies"));
  }

  @Test
  public void shouldReturnOneMovie() throws Exception {
    String movieJString = this.createMovie("Reza");

    MvcResult result = this.mockMvc.perform(post(URI)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("utf-8")
        .content(movieJString))
        .andExpect(status().isCreated())
        .andReturn();
    
    String content = result.getResponse().getContentAsString();

    this.mockMvc.perform(get(this.getUriID(content)))
        .andExpect(status().isOk())
        .andExpect(r -> assertEquals(r.getResponse().getContentAsString(), content))
        .andDo(document("getOneMovie"));
  }

  @Test
  public void shouldCreateMovie() throws Exception {
    this.mockMvc.perform(post(URI)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(createMovie("Reza")))
        .andExpect(status().isCreated())
        .andDo(document("createMovie"));
  }
  
  @Test
  public void shouldDeleteMovie() throws Exception {
    MvcResult createdMovie = this.mockMvc.perform(post(URI)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("utf-8")
        .content(this.createMovie("Reza")))
        .andReturn();
    String movieContent = createdMovie.getResponse().getContentAsString();

    MvcResult mvcResult = this.mockMvc.perform(delete(this.getUriID(movieContent)))
        .andExpect(status().isOk())
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

    MvcResult mvcCreateResult = this.mockMvc.perform(post(URI)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("utf-8")
        .content(inputJson)).andReturn();
    
    String prevContent = mvcCreateResult.getResponse().getContentAsString();

    Movie updatedMovie = new Movie();
    updatedMovie.setName("Ali");
    String updatedInputJson = super.mapToJson(updatedMovie);

    MvcResult mvcUpdateResult = this.mockMvc.perform(put(this.getUriID(prevContent))
        .contentType(MediaType.APPLICATION_JSON_VALUE).content(updatedInputJson)).andExpect(status().isOk())
        .andDo(document("updateOneMovies")).andReturn();
    
    int status = mvcUpdateResult.getResponse().getStatus();
    assertEquals(200, status);
    String content = mvcUpdateResult.getResponse().getContentAsString();
    assertNotEquals(prevContent, content);
  }
}