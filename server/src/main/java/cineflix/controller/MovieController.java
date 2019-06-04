package cineflix.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cineflix.models.Movie;
import cineflix.models.enums.MovieField;
import cineflix.services.MovieService;

@RestController
@RequestMapping(path = "/movie")
public class MovieController {

	@Autowired private MovieService movieService;

	private static final Logger LOGGER = LoggerFactory.getLogger(MovieController.class);


	@PostMapping(value = "")
	public ResponseEntity<Map<String,String>> addMovie(@RequestBody Movie movie){
		Movie resMovie= movieService.addMovie(movie);
		if (resMovie==null) { 
			LOGGER.error("Unable to save new movie: {}",movie);
			return ResponseEntity.badRequest().body(Collections.singletonMap("id", null));
		}
		else {
			LOGGER.info("Saved new movie: {}",resMovie);
			return ResponseEntity.ok(Collections.singletonMap("id", resMovie.getId()));
		}
	}

	@GetMapping(value = "/search")
	public ResponseEntity<List<Movie>> find(@RequestParam(required = true, value = "type") String type,
			@RequestParam(required = false, value = "value") List<String> value,
			@RequestParam(required = false, value = "min") Float min,
			@RequestParam(required = false, value = "max") Float max,
			@RequestParam(required = false, value = "size", defaultValue ="50") Integer size) {

		LOGGER.info("Method=GET, Path:/search, Field: {}, Value: {}, Min: {}, Max: {}, Size: {}",type, value, min, max, size);
		List<Movie> movie = movieService.findMovieByIn(MovieField.valueOf(type.toUpperCase()), value, min, max, size);
		if(movie.isEmpty())
			return ResponseEntity.badRequest().body(movie);
		return ResponseEntity.ok(movie);

	}
}
