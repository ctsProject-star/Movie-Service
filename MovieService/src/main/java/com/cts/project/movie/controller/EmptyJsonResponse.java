package com.cts.project.movie.controller;

import com.cts.project.movie.entity.Movie;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class EmptyJsonResponse extends Movie {

}
