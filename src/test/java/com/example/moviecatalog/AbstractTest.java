package com.example.moviecatalog;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import com.example.moviecatalog.models.Director;
import com.example.moviecatalog.models.Movie;
import com.example.moviecatalog.models.Rating;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
public abstract class AbstractTest {
  @Autowired
  protected WebApplicationContext context;

  protected MockMvc mockMvc;

  protected String createDirector(String name) throws JsonProcessingException {
    Director createDirector = new Director();
    createDirector.setName(name);
    String inputJson = this.mapToJson(createDirector);
    return inputJson;
  }

  protected String createRating(Integer number) throws JsonProcessingException {
    Rating createRating = new Rating();
    createRating.setNumber(number);
    String inputJson = this.mapToJson(createRating);
    return inputJson;
  }

  protected String createMovie(String name) throws UnsupportedEncodingException, Exception {
    String newDirectorContent = this.mockMvc.perform(post("/directors")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("utf-8")
        .content(this.createDirector("Reza")))
        .andReturn()
        .getResponse()
        .getContentAsString();
            
    Set<Director> directors = new HashSet<Director>();
    directors.add(this.mapFromJson(newDirectorContent, Director.class) );

    String newRatingContent = this.mockMvc.perform(post("/ratings")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("utf-8")
        .content(this.createRating(2)))
        .andReturn()
        .getResponse()
        .getContentAsString();
    
    Rating rating = this.mapFromJson(newRatingContent, Rating.class);
    Movie createMovie = new Movie();
    createMovie.setName(name);
    createMovie.setDirectors(directors);
    createMovie.setRating(rating);
    String inputJson = this.mapToJson(createMovie);
    return inputJson;
  }

  @BeforeEach
  public void setUp(RestDocumentationContextProvider restDocumentation) {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(documentationConfiguration(restDocumentation)).build();
  }

  protected String mapToJson(Object obj) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(obj);
  }
  
  protected <T> T mapFromJson(String json, Class<T> clazz)
      throws JsonParseException, JsonMappingException, IOException {
      
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.readValue(json, clazz);
  }
}
