package cineflix.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import cineflix.models.Star;

public interface StarRepository extends MongoRepository<Star, String> {

	public Star findByName(String name);

	public List<Star> findByIdIn(List<String> value);

	public List<Star> findByNameIn(List<String> value);


}
