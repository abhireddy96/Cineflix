package cineflix.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import cineflix.models.Movie;

public interface MovieRepository extends MongoRepository<Movie, String> {

	public List<Movie> findByCastIn(List<String> cast);

	public List<Movie> findByDirectorsIn(List<String> director);

	public Optional<Movie> findByTitle(String title);

	public List<Movie> findByGenresIn(List<String> genres);

	public List<Movie> findByIdIn(List<String> ids);
	
	public List<Movie> findByCountriesIn(List<String> value);

	public List<Movie> findByTitleIn(List<String> value);

	@Query(value="{'imdb.rating': { $lte: ?1, $gte: ?0}}")
	public List<Movie> findByImdbRatingBetween(float min,float max);

	@Query(value = "{'runtime':{ $lte: ?1, $gte: ?0}}")
	public List<Movie> findByRuntimeBetween(int min,int max);

	@Query(value = "{'year':{ $lte: ?1, $gte: ?0}}")
	public List<Movie> findByYearBetween(int min,int max);

	@Query(value="{$or:[ {cast: {'$in': ?0}}, {directors: {'$in': ?0}}, {writers: {'$in': ?0}} ] }", fields="{id:1, title:1, year:1, poster:1, imdb:1}")
	public List<Movie> findByCrewNameIn(List<String> cast);

}
