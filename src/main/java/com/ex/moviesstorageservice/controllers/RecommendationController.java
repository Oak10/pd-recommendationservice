package com.ex.moviesstorageservice.controllers;

import com.ex.moviesstorageservice.entities.EmailDetails;
import com.ex.moviesstorageservice.entities.Movie;
import com.ex.moviesstorageservice.services.KafkaSender;
import com.ex.moviesstorageservice.services.TheMovieDb;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping(value = "/recommendation")
public class RecommendationController {
    Random r = new Random();
    private static final Logger LOGGER = LoggerFactory.getLogger(RecommendationController.class);
    @Autowired
    KafkaSender kafkaSender;

    @Autowired
    TheMovieDb theMovieDb;

    @GetMapping(value = "/produce")
    public EmailDetails produce(@RequestHeader("Authorization") String token) throws JsonProcessingException, VerificationException {
        int rand = r.nextInt(1000);

        AccessToken actoken = TokenVerifier.create(
                token.replace("Bearer ", ""), AccessToken.class).getToken();

        EmailDetails message = new EmailDetails();
        message.setRecipient(actoken.getEmail());
        message.setMsgBody(theMovieDb.getWeekMovieTrendsAsText());
        message.setSubject("Test, rand: " + rand);

        ObjectMapper objectMapper = new ObjectMapper();
        LOGGER.info(objectMapper.writeValueAsString(message));

        kafkaSender.send(message);
        return message;
    }


    @GetMapping(value = "/data")
    public ResponseEntity<List<Movie>> produceMovies(){
       return theMovieDb.getWeekMovieTrends();
    }





}