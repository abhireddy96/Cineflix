package cineflix.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cineflix.models.Movie;
import cineflix.models.Session;
import cineflix.models.Star;
import cineflix.models.User;
import cineflix.models.UserPreference;
import cineflix.models.UserPrincipal;
import cineflix.models.UserRegistry;
import cineflix.models.enums.UserField;
import cineflix.repository.MovieRepository;
import cineflix.repository.SessionRepository;
import cineflix.repository.StarRepository;
import cineflix.repository.UserRepository;

@Service
@Configuration
public class UserService implements UserDetailsService{

	@Autowired private PasswordEncoder passwordEncoder;

	@Autowired private AuthenticationManager authenticationManager;

	@Autowired private TokenAuthenticationService authService;

	@Autowired private UserRepository userRepository;

	@Autowired private MovieRepository movieRepository;

	@Autowired private StarRepository starRepository;

	@Autowired private SessionRepository sessionRepository;

	private static Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	public UserService() {
		super();
	}

	public User loadUser(String userId) {
		return userRepository.findByEmail(userId);
	}

	public User createUser(UserRegistry register, Map<String, String> errors) {
		User user = getUserFromRegistry(register);
		return userRepository.save(user);
	}

	public User createAdminUser(UserRegistry register) {
		User user = getUserFromRegistry(register);
		user.setAdmin(true);
		return userRepository.save(user);
	}

	public boolean deleteUser(String email, String password, Map<String, String> results) {
		String hashedpwd = passwordEncoder.encode(password);

		if (!passwordEncoder.matches(password, hashedpwd)) {
			results.put("msg", "passwords do not match");
			return false;
		}
		return userRepository.deleteByEmail(email);
	}

	private User getUserFromRegistry(UserRegistry register) {
		User user = new User();
		user.setHashedpw(passwordEncoder.encode(register.getPassword()));
		user.setEmail(register.getEmail());
		user.setName(register.getName());
		user.setPreferences(new UserPreference());
		return user;
	}

	public boolean logoutUser(String email) {
		try {
			sessionRepository.deleteAllByUserId(email);
		}catch(Exception e) {
			LOGGER.error("Error while deleting all sessions of a user", e);
			return false;
		}
		return true;
	}

	public String getEmailFromRequest(String request) {
		String jwt = request.substring(7);
		return authService.getAuthenticationUser(jwt);
	}

	public User authenticate(String email, String password, Map<String, Object> results) {
		LOGGER.info("Authenticate Email: {}, Password:{}",email,password);
		String jwt = generateUserToken(email, password);
		LOGGER.info("Generated JWT Token:{}",jwt);
		Session session=new Session();
		session.setJwt(jwt);
		session.setUserId(email);
		sessionRepository.save(session);
		results.put("auth_token", new String(jwt));
		return userRepository.findByEmail(email);
	}

	private String generateUserToken(String email, String password) {
		Authentication authentication =
				authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(email, password));
		return authService.generateToken(authentication);
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByEmail(username);
		if (user == null || user.isEmpty()) {
			throw new UsernameNotFoundException("Cannot find username.");
		}
		return UserPrincipal.create(user);
	}

	public User findUserByIn(UserField field, String value, Float min, Float max, int size) {

		LOGGER.info("Field: {}, Value: {}, Min: {}, Max: {}, Size: {}",field, value, min, max, size);
		switch(field) {

		case ID: {
			Optional<User> user= userRepository.findById(value);
			if(user.isPresent())
				return user.get();
			return null;
		}
		case NAME: {
			return userRepository.findByName(value);
		}
		case EMAIL: {
			return userRepository.findByEmail(value);
		}

		default: {
			return null;
		}
		}
	}


	public User addToUser(UserField field, String userID, String subscribeID) {
		LOGGER.info("Field: {}, User: {}, Program: {}",field, userID, subscribeID);
		switch(field) {

		case MOVIES: {
			Optional<User> user= userRepository.findById(userID);
			Optional<Movie> movie = movieRepository.findById(subscribeID);
			List<String> movies;

			if(user.isPresent() && movie.isPresent()) {
				UserPreference  userPreference =  user.get().getPreferences();

				if(userPreference.getMovies()!=null) 
					movies = userPreference.getMovies();
				else 
					movies = new ArrayList<>();
				if(movies.contains(movie.get().getId()))
					return null;
				movies.add(movie.get().getId());
				userPreference.setMovies(movies);
				user.get().setPreferences(userPreference);
				return userRepository.save(user.get());
			}
			return null;
		}

		case GENRES: {
			Optional<User> user= userRepository.findById(userID);
			List<String> genres;
			if(user.isPresent() ) {
				UserPreference  userPreference =  user.get().getPreferences();

				if(userPreference.getGenres()!=null) 
					genres = userPreference.getGenres();
				else 
					genres = new ArrayList<>();
				genres.add(userID);
				userPreference.setGenres(genres);
				user.get().setPreferences(userPreference);
				return userRepository.save(user.get());
			}
			return null;
		}
		case STARS: {
			Optional<User> user= userRepository.findById(userID);
			Optional<Star> star = starRepository.findById(subscribeID);
			List<String> stars;

			if(user.isPresent() && star.isPresent()) {
				UserPreference  userPreference =  user.get().getPreferences();

				if(userPreference.getStars()!=null) 
					stars = userPreference.getStars();
				else 
					stars = new ArrayList<>();
				stars.add(star.get().getId());
				userPreference.setStars(stars);
				user.get().setPreferences(userPreference);
				return userRepository.save(user.get());
			}
			return null;
		}

		default: {
			return null;
		}
		}
	}

	public User deleteFromUser(UserField field, String userID, String subscribeID) {
		LOGGER.info("Field: {}, User: {}, Program: {}",field, userID, subscribeID);
		switch(field) {

		case MOVIES: {
			Optional<User> user= userRepository.findById(userID);
			Optional<Movie> movie = movieRepository.findById(subscribeID);
			List<String> movies;

			if(user.isPresent() && movie.isPresent()) {
				UserPreference  userPreference =  user.get().getPreferences();

				if(userPreference.getMovies()!=null) {
					movies = userPreference.getMovies();
					if(!movies.contains(movie.get().getId()))
						return null;
					movies.remove(movie.get().getId());
					userPreference.setMovies(movies);
					user.get().setPreferences(userPreference);
					return userRepository.save(user.get());
				}
			}
			return null;
		}

		default: {
			return null;
		}
		}
	}
}
