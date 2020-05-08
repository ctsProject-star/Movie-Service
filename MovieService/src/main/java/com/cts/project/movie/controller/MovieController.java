package com.cts.project.movie.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.cts.project.movie.entity.Movie;
import com.cts.project.movie.errorHandling.CustomErrorType;
import com.cts.project.movie.errorHandling.MovieNotFoundException;
import com.cts.project.movie.repository.MovieRepository;

@RestController
@RequestMapping("/movie")
public class MovieController 
{
		 @Autowired
	     private MovieRepository movieRepository;
	
		
		 private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
		 @RequestMapping(value = "/", method = RequestMethod.GET) 
		 public ResponseEntity<List<Movie>> listAllMovie() throws IOException
		 {
			    logger.info("Fetching all Movie's details..."); 
		        List<Movie> movie;
		        try 
		        {
		        	movie= movieRepository.findAll();
			        
			        if (movie.isEmpty()) {
			            return new ResponseEntity<List<Movie>>(HttpStatus.NO_CONTENT);
			            // You many decide to return HttpStatus.NOT_FOUND
			        }
		        }
		        catch(RuntimeException e) 
				 { 
					logger.error("RunTimeException is thrown... ");
					throw new RuntimeException(e);
				 }
		        
		        return new ResponseEntity<List<Movie>>(movie, HttpStatus.OK);
		  }
		 
		 
		 @SuppressWarnings({ "unchecked", "rawtypes" })
		 @RequestMapping(value = "/{movieId}", method = RequestMethod.GET)
		 public ResponseEntity<Movie> getMovieById(@PathVariable("movieId") Integer movieId) throws IOException
	     {
			   logger.info("Fetching Movie with id {}", movieId);
			   Movie movie;
			   try 
			   {
				  movie = movieRepository.findById(movieId).orElse(null);
				 
				  if (movie == null)
				  {
					  logger.error("Movie with id {} not found.", movieId);
					  return new ResponseEntity(new CustomErrorType("Unable to find Movie with id: " 
					                            +  movieId + " ,does not exist."),HttpStatus.NOT_FOUND);
				  }
			   }
			   catch(RuntimeException e) 
			   { 
				 logger.error("RunTimeException is thrown... ");
				 throw new RuntimeException(e);
			   }
		
			   return new ResponseEntity<Movie>(movie, HttpStatus.OK);
			
		 }
	    
	     @SuppressWarnings({ "unchecked", "rawtypes" })
		 @RequestMapping(value = "/", method = RequestMethod.POST)
	     public ResponseEntity<?> createMovie(@Valid @RequestBody Movie movie, UriComponentsBuilder ucBuilder) throws IOException 
	     {
	    	  logger.info("Creating new Movie : {}", movie);
	    	  try 
	    	  {
	    		 if (movieRepository.existsMovieBymCode(movie.getmCode() )) 
		    	 {
		             logger.error("Unable to create Movie with code {} , already exists!", movie.getmCode());
		             return new ResponseEntity(new CustomErrorType("Unable to create Movie with code " 
		                                       +  movie.getmCode() + " already exist."),HttpStatus.CONFLICT);
		         }
	    	  }
	    	  catch(RuntimeException e)
	    	  {
	    		 logger.error("RunTimeException is thrown... ");
				 throw new RuntimeException(e);
	    	  }
	         
	    	  movieRepository.save(movie);
	        
	    	  logger.info("Movie : {} created sucessfully...", movie);
	          HttpHeaders headers = new HttpHeaders();
	          headers.setLocation(ucBuilder.path("/movie/{movieId}").buildAndExpand(movie.getId()).toUri());
	          return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	     }
	
	     @SuppressWarnings({ "unchecked", "rawtypes" })
		 @RequestMapping(value = "/{movieId}", method = RequestMethod.PUT)
	     public ResponseEntity<Movie> updateMovie(@PathVariable Integer movieId, @Valid @RequestBody Movie movieRequest) 
	     {
		    	logger.info("Updating Movie with id {}", movieId);
		    	Movie movie;
		    	try 
		    	{
		    		movie = movieRepository.findById(movieId).orElse(null);
		    		
		    		 if (movie == null)
					 {
						 logger.error("Movie with id {} not found.", movieId);
						 return new ResponseEntity(new CustomErrorType("Unable to find Movie with id: " +  movieId + " ,does not exist."),HttpStatus.NOT_FOUND);
					 }
	
		    		 movie.setmName(movieRequest.getmName());
		    		
		    	}
		    	catch(RuntimeException e)
		    	{
		    		logger.error("RunTimeException is thrown... ");
					throw new RuntimeException(e);
		    	}

		    	movieRepository.save(movie);
		    	logger.info("Movie : {} updated sucessfully...", movie);
		    	return new ResponseEntity<Movie>(movie, HttpStatus.OK);
		       
	     }
	
	
	     @RequestMapping(value = "/{movieId}", method = RequestMethod.DELETE)
		 public ResponseEntity<?> deleteMovie(@PathVariable (value="movieId") Integer movieId) 
	     {
	     	 logger.info("Deleting Movie with id {}", movieId);
	     	 
		         return movieRepository.findById(movieId).map(movie -> {
		         	movieRepository.delete(movie);
		             return ResponseEntity.ok().build();
		         }).orElseThrow(() -> new MovieNotFoundException("Movie Id : " + movieId + " not found"));
		  }
     
	      
	      @RequestMapping(value = "/", method = RequestMethod.DELETE)
	      public ResponseEntity<Movie> deleteAllMovie() 
	      {
	          logger.info("Deleting All Movies...");
	   
	          movieRepository.deleteAll();
	          return new ResponseEntity<Movie>(HttpStatus.NO_CONTENT);
	      }


}
