package com.cts.project.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.project.movie.entity.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> 
{
	boolean existsMovieBymCode(String mCode);

}
