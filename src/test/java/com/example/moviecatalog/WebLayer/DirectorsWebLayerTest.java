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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MvcResult;

import com.example.moviecatalog.AbstractTest;
import com.example.moviecatalog.models.Director;


@SpringBootTest
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
public class DirectorsWebLayerTest extends AbstractTest {

  @Test
  public void shouldReturnAllDirectors() throws Exception {
    this.mockMvc.perform(get("/directors"))
        .andExpect(status().isOk())
        .andDo(document("getAllDirectors"));
  }

  @Test
  public void shouldReturnOneDirector() throws Exception {
    MvcResult mvcCreateResult = this.mockMvc.perform(post("/directors")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("utf-8")
        .content(createDirector("Nolan"))).andReturn();

    String content = mvcCreateResult.getResponse().getContentAsString();

    Long movieId = super.mapFromJson(content, Director.class).getId();

    this.mockMvc.perform(get("/directors/" + movieId))
        .andExpect(status().isOk())
        .andDo(document("getOneDirector"));
  }

  @Test
  public void shouldcreateRating() throws Exception {
    this.mockMvc.perform(post("/directors")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("utf-8")
        .content(this.createDirector("Nolan")))
        .andExpect(status().isOk())
        .andDo(document("createDirector"));
  }

  @Test
  public void shouldDeleteMovie() throws Exception {
    MvcResult mvcCreateResult = this.mockMvc.perform(post("/directors")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("utf-8")
        .content(createDirector("Nolan"))).andReturn();

    String createdDirectorContent = mvcCreateResult.getResponse().getContentAsString();

    Long movieId = super.mapFromJson(createdDirectorContent, Director.class).getId();

    MvcResult mvcResult = this.mockMvc.perform(delete("/directors/" + movieId)).andExpect(status().isOk())
        .andDo(document("deleteOneDirector")).andReturn();
    int status = mvcResult.getResponse().getStatus();
    assertEquals(200, status);
    String content = mvcResult.getResponse().getContentAsString();
    assertEquals(content, "Director is deleted successsfully");
  }

  @Test
  public void shouldUpdateMovie() throws Exception {
    MvcResult mvcCreateResult = this.mockMvc.perform(post("/directors")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("utf-8")
        .content(this.createDirector("Reza"))).andReturn();
    
    String prevContent = mvcCreateResult.getResponse().getContentAsString();

    Director targeDirector = this.mapFromJson(prevContent, Director.class);
    targeDirector.setName("Ali");
    String updatedInputJson = super.mapToJson(targeDirector);
    String uri = "/directors/" + this.mapFromJson(prevContent, Director.class).getId();

    MvcResult mvcUpdateResult = this.mockMvc.perform(
        put(uri)
        .contentType(MediaType.APPLICATION_JSON_VALUE).content(updatedInputJson)).andExpect(status().isOk())
        .andDo(document("updateOneDirector")).andReturn();
    
    int status = mvcUpdateResult.getResponse().getStatus();
    assertEquals(200, status);
    String content = mvcUpdateResult.getResponse().getContentAsString();
    assertNotEquals(prevContent, content);
  }
}