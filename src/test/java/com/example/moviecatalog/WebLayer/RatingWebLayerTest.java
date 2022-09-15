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
import com.example.moviecatalog.models.Rating;
import com.fasterxml.jackson.core.JsonProcessingException;


@SpringBootTest
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
public class RatingWebLayerTest extends AbstractTest {

  @BeforeEach
  public void setUp(RestDocumentationContextProvider restDocumentation) {
    super.setUp(restDocumentation);
  }

  protected String createRating(Integer number) throws JsonProcessingException {
    Rating createRating = new Rating();
    createRating.setNumber(number);
    String inputJson = this.mapToJson(createRating);
    return inputJson;
  }

  @Test
  public void shouldReturnAllMovies() throws Exception {
    this.mockMvc.perform(get("/ratings"))
        .andExpect(status().isOk())
        .andDo(document("getAllRatings"));
  }

  @Test
  public void shouldReturnOneMovie() throws Exception {
    MvcResult mvcCreateResult = this.mockMvc.perform(post("/ratings")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("utf-8")
        .content(createRating(2))).andReturn();

    String content = mvcCreateResult.getResponse().getContentAsString();

    Long movieId = super.mapFromJson(content, Rating.class).getId();

    this.mockMvc.perform(get("/ratings/" + movieId))
        .andExpect(status().isOk())
        .andDo(document("getOneRatings"));
  }

  @Test
  public void shouldcreateRating() throws Exception {
    this.mockMvc.perform(post("/ratings")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("utf-8")
        .content(this.createRating(3)))
        .andExpect(status().isOk())
        .andDo(document("createRating"));
  }

  @Test
  public void shouldDeleteMovie() throws Exception {
    this.mockMvc.perform(post("/ratings")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("utf-8")
        .content(this.createRating(4))).andReturn();
    String uri = "/ratings/1";
    MvcResult mvcResult = this.mockMvc.perform(delete(uri)).andExpect(status().isOk())
        .andDo(document("deleteOneRating")).andReturn();
    int status = mvcResult.getResponse().getStatus();
    assertEquals(200, status);
    String content = mvcResult.getResponse().getContentAsString();
    assertEquals(content, "Rating is deleted successsfully");
  }

  @Test
  public void shouldUpdateMovie() throws Exception {
    Rating createdMovie = new Rating();
    createdMovie.setNumber(2);
    String inputJson = super.mapToJson(createdMovie);

    MvcResult mvcCreateResult = this.mockMvc.perform(post("/ratings")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("utf-8")
        .content(inputJson)).andReturn();
    
    String prevContent = mvcCreateResult.getResponse().getContentAsString();

    Rating updatedMovie = new Rating();
    updatedMovie.setNumber(3);
    String updatedInputJson = super.mapToJson(updatedMovie);

    MvcResult mvcUpdateResult = this.mockMvc.perform(put("/ratings/"+ this.mapFromJson(prevContent, Rating.class).getId())
        .contentType(MediaType.APPLICATION_JSON_VALUE).content(updatedInputJson)).andExpect(status().isOk())
        .andDo(document("updateOneRatings")).andReturn();
    
    int status = mvcUpdateResult.getResponse().getStatus();
    assertEquals(200, status);
    String content = mvcUpdateResult.getResponse().getContentAsString();
    assertNotEquals(prevContent, content);
  }
}