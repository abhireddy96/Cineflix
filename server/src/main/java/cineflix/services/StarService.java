package cineflix.services;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cineflix.models.Star;
import cineflix.models.StarField;
import cineflix.repository.StarRepository;

@Service
public class StarService {

	@Autowired private StarRepository starRepository;

	private static Logger LOGGER = LoggerFactory.getLogger(StarService.class);
	
	public Star addStar(Star star) {

		return starRepository.save(star);
	
	}

	public List<Star> findStarByIn(StarField field, List<String> value, Float min, Float max, int size) {

		LOGGER.info("Field: {}, Value: {}, Min: {}, Max: {}, Size: {}",field, value, min, max, size);
		switch(field) {

		case ALL: {
			@SuppressWarnings("deprecation")
			Pageable pageable = new PageRequest(0, size);
			return starRepository.findAll(pageable).getContent();
		}
		case ID: {
			return starRepository.findByIdIn(value);
		}
		case NAME: {
			return starRepository.findByNameIn(value);
			}

		default: {
			return Collections.emptyList();
		}
		}
	}
}
