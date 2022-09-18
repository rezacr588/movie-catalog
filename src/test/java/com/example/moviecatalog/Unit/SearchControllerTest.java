package com.example.moviecatalog.Unit;

import com.example.moviecatalog.controllers.SearchController;
import com.example.moviecatalog.models.Director;
import com.example.moviecatalog.models.Movie;
import com.example.moviecatalog.models.Rating;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Collections.singletonList;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
@WebMvcTest(SearchController.class)
public class SearchControllerTest {

   @Autowired
   private MockMvc mvc;

   @MockBean
   private SearchController searchController;

   @Test
   public void testRatingController() throws Exception {
      Movie movie = new Movie();
      movie.setName("Dark Knight");
      Director director = new Director();
      director.setName("Nolan");
      Rating rating = new Rating();
      rating.setNumber(5);
      Set<Director> directors = new HashSet<>(Arrays.asList(director));
      movie.setDirectors(directors);
      movie.setRating(rating);
      List<Movie> movies = singletonList(movie);
      given(searchController.searchMovies(null,"2")).willReturn(movies);
      mvc.perform(get("/search").param("rating", "2").contentType(APPLICATION_JSON))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$", hasSize(1)))
         .andExpect(jsonPath("$[0].name", is(movie.getName())))
         .andExpect(jsonPath("$[0].rating.number", is(movie.getRating().getNumber())))
         .andExpect(jsonPath("$[0].directors[0].name", is(movie.getDirectors().iterator().next().getName())))
         .andDo(MockMvcResultHandlers.print());

   }

   @Test
   public void testDirectorController() throws Exception {
      Movie movie = new Movie();
      movie.setName("Dark Knight");
      Director director = new Director();
      director.setName("Nolan");
      Rating rating = new Rating();
      rating.setNumber(5);
      Set<Director> directors = new HashSet<>(Arrays.asList(director));
      movie.setDirectors(directors);
      movie.setRating(rating);
      List<Movie> movies = singletonList(movie);
      given(searchController.searchMovies(String.valueOf(director.getId()), null)).willReturn(movies);
      mvc.perform(get("/search").param("director", String.valueOf(director.getId())).contentType(APPLICATION_JSON))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$", hasSize(1)))
         .andExpect(jsonPath("$[0].name", is(movie.getName())))
         .andExpect(jsonPath("$[0].rating.number", is(movie.getRating().getNumber())))
         .andExpect(jsonPath("$[0].directors[0].name", is(movie.getDirectors().iterator().next().getName())))
         .andDo(MockMvcResultHandlers.print());
   }
}