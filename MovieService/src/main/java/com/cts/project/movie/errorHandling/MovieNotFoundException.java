package com.cts.project.movie.errorHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MovieNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 100L;

	public MovieNotFoundException() 
    {
        super();
    }

    public MovieNotFoundException(String message) 
    {
        super(message);
    }
    
    public MovieNotFoundException(String message, Throwable cause) 
    {
        super(message, cause);
    }
}
