package com.cts.project.movie.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cts.project.movie.repository.MovieRepository;


@WebMvcTest(MovieController.class)

class MovieControllerTest {
	
	@Autowired
	private MockMvc mock;
	
	@MockBean
	private MovieRepository mr;

	@Test
	void getMovieByIdTestApi() throws Exception {
		
		mock.perform(MockMvcRequestBuilders .get("/movie/{movieId}",6)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status()
				.isOk());
		
	}

}
