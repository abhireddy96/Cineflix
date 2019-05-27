package cineflix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class CineflixApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(CineflixApplication.class, args);
	}

}
