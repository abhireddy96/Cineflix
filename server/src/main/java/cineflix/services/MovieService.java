package cineflix.services;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Service;

import cineflix.models.Movie;
import cineflix.models.MovieField;
import cineflix.repository.MovieRepository;

@Service
public class MovieService {


	@Autowired private MovieRepository movieRepository;

	@Autowired private MongoTemplate mongoTemplate;

	private static Logger LOGGER = LoggerFactory.getLogger(MovieService.class);

	public Movie addMovie(Movie movie) {

		return movieRepository.save(movie);
	}

	public List<Movie> searchText(String text){

		Criteria regexCriteria= Criteria.where("title").regex(Pattern.compile(".*"+text.trim()+".*" , Pattern.CASE_INSENSITIVE));
		Query query = TextQuery.query(regexCriteria);

		return mongoTemplate.find(query,Movie.class);

		/*	TextIndexDefinition textIndex = new TextIndexDefinition.TextIndexDefinitionBuilder()
		.onField("title", 2F)
		.onField("cast")
		.build();
         mongoTemplate.indexOps(Movie.class).ensureIndex(textIndex);
         TextCriteria criteria = TextCriteria.forDefaultLanguage()
		  .matchingAny(text.trim().split(" "));
         Query query = TextQuery.queryText(criteria).sortByScore();
         */

	}

	public List<Movie> findMovieByIn(MovieField field, List<String> value, Float min, Float max, int size) {

		LOGGER.info("Field: {}, Value: {}, Min: {}, Max: {}, Size: {}",field, value, min, max, size);
		switch(field) {

		case ALL: {
			@SuppressWarnings("deprecation")
			Pageable pageable = new PageRequest(0, size);
			return movieRepository.findAll(pageable).getContent();
		}
		case ID: {
			return movieRepository.findByIdIn(value);
		}
		case TITLE: {
			return movieRepository.findByTitleIn(value);
		}
		case DIRECTORS: {
			return movieRepository.findByDirectorsIn(value);
		}
		case CAST: {
			return movieRepository.findByCastIn(value);
		}
		case GENRES: {
			return movieRepository.findByGenresIn(value);
		}
		case COUNTRIES: {
			return movieRepository.findByCountriesIn(value);
		}
		case CREW: {
			return movieRepository.findByCrewNameIn(value);
		}
		case RUNTIME: {
			return movieRepository.findByRuntimeBetween(Math.round(min),Math.round(max));
		}
		case IMDB_RATING: {
			return movieRepository.findByImdbRatingBetween(min, max);
		}
		case YEAR: {
			return movieRepository.findByYearBetween(Math.round(min),Math.round(max));
		}
		case QUERY: {
			return searchText(value.get(0));
		}
		default: {
			return Collections.emptyList();
		}
		}
	}
}
