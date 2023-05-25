package com.ex.moviesstorageservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Movie {
    public boolean adult;
//    public String backdrop_path;
    public int id;
    public String title;
    public String original_language;
//    public String original_title;
    public String overview;
//    public String poster_path;
//    public String media_type;
    public ArrayList<Integer> genre_ids;
//    public double popularity;
    public String release_date;
//    public boolean video;
//    public double vote_average;
//    public int vote_count;
}
