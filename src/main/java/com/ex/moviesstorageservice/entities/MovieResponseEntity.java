package com.ex.moviesstorageservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MovieResponseEntity {
    public int page;
    public ArrayList<Movie> results;
    public int totalPages;
    public int totalResults;
}
