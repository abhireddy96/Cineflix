package cineflix.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import cineflix.models.User;

public interface UserRepository extends MongoRepository<User, String>{

	User findByEmail(String email);

	boolean deleteByEmail(String email);

	User findByName(String value);

}
