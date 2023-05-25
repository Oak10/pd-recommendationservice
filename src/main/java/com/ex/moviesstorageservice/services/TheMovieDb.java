package com.ex.moviesstorageservice.services;

import com.ex.moviesstorageservice.entities.Movie;
import com.ex.moviesstorageservice.entities.MovieResponseEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TheMovieDb {

    @Value(value = "${THEMOVIEDB_API_KEY}")
    private String MOVIE_DB_API_KEY;
    private static final String URL = "https://api.themoviedb.org/3/";


    public ResponseEntity<List<Movie>> getWeekMovieTrends() {
        // Default page 1 + "&page=1"
        String finalUrl = URL + "trending/movie/week?api_key=" + MOVIE_DB_API_KEY;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<MovieResponseEntity> response =
                restTemplate.getForEntity(finalUrl, MovieResponseEntity.class);
        if (  HttpStatus.OK != response.getStatusCode() ){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i< 10; i++){
            movies.add(Objects.requireNonNull(response.getBody()).getResults().get(i));
        }
        return new ResponseEntity<>( movies ,HttpStatus.OK);
    }

    public String getWeekMovieTrendsAsText() {
        // Default page 1 + "&page=1"
        String finalUrl = URL + "trending/movie/week?api_key=" + MOVIE_DB_API_KEY;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<MovieResponseEntity> response =
                restTemplate.getForEntity(finalUrl, MovieResponseEntity.class);

        StringBuilder result = new StringBuilder("Weekly movie trends: \n\n");
        for (int i = 0; i< 10; i++){
            Movie movie = Objects.requireNonNull(response.getBody()).getResults().get(i);
            result.append("\n-").append(movie.getTitle()).append(", ").append(movie.getRelease_date())
                    .append(":\n   ").append(movie.getOverview()).append("\n");
        }
        result.append("\n\n\nRegards,\n\n").append("DemoPD team");

        return result.toString();
    }

    // Search and Query Movies:
    // https://developers.themoviedb.org/3/getting-started/search-and-query-for-details


}
