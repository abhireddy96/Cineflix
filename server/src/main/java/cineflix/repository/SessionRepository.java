package cineflix.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import cineflix.models.Session;

public interface SessionRepository extends MongoRepository<Session, String>{

	 void deleteAllByUserId(String email) ;

}
