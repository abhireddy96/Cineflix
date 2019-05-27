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

import cineflix.models.Star;
import cineflix.models.StarField;
import cineflix.services.StarService;

@RestController
@RequestMapping(path = "/star")
public class StarController {

	@Autowired private StarService starService;

	private static final Logger LOGGER = LoggerFactory.getLogger(StarController.class);

	@PostMapping(value = "")
	public ResponseEntity<Map<String, String>> savestar(@RequestBody Star star){
		Star resStar= starService.addStar(star);
		if (resStar==null) { 
			LOGGER.error("Unable to save new star: {}",star);
			return ResponseEntity.badRequest().body(Collections.singletonMap("id", null));
		}
		else {
			LOGGER.info("Saved new star: {}",resStar);
			return ResponseEntity.ok(Collections.singletonMap("id", resStar.getId()));
		}
	}

	
	@GetMapping(value = "/search")
	public ResponseEntity<List<Star>> find(@RequestParam(required = true, value = "type") String type,
			@RequestParam(required = false, value = "value") List<String> value,
			@RequestParam(required = false, value = "min") Float min,
			@RequestParam(required = false, value = "max") Float max,
			@RequestParam(required = false, value = "size", defaultValue ="50") Integer size) {

		LOGGER.info("Method=GET, Path:/search, Field: {}, Value: {}, Min: {}, Max: {}, Size: {}",type, value, min, max, size);
		List<Star> star = starService.findStarByIn(StarField.valueOf(type.toUpperCase()), value, min, max, size);
		return ResponseEntity.ok(star);

	}
	
}
