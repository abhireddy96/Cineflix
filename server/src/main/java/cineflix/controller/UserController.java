package cineflix.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cineflix.models.Login;
import cineflix.models.User;
import cineflix.models.UserRegistry;
import cineflix.models.enums.UserField;
import cineflix.services.UserService;

@CrossOrigin(origins = "*")
@RequestMapping(path = "/user")
@RestController
@SuppressWarnings("rawtypes")
public class UserController{

	@Autowired UserService userService;

	private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	public UserController() {
		super();
	}

	@GetMapping(value = "/id/{userId}")
	public ResponseEntity<User> getMovieById(@PathVariable(value = "userId") String userId) {
		User user = userService.loadUser(userId);
		return ResponseEntity.ok(user);
	}

	@GetMapping(value = "/search")
	public ResponseEntity<User> find(@RequestParam(required = true, value = "type") String type,
			@RequestParam(required = false, value = "value") String value,
			@RequestParam(required = false, value = "min") Float min,
			@RequestParam(required = false, value = "max") Float max,
			@RequestParam(required = false, value = "size", defaultValue ="50") Integer size) {

		LOGGER.info("Method=GET, Path:/search, Field: {}, Value: {}, Min: {}, Max: {}, Size: {}",type, value, min, max, size);
		User user = userService.findUserByIn(UserField.valueOf(type.toUpperCase()), value, min, max, size);
		if(user!=null)
			return ResponseEntity.ok(user);
		return ResponseEntity.badRequest().body(null);

	}
	
	@PostMapping(value = "/subscribe")
	public ResponseEntity<User> add(@RequestParam(required = true, value = "type") String type,
			@RequestParam(required = false, value = "user") String userID,
			@RequestParam(required = false, value = "program") String subscribeID) {

		LOGGER.info("Method=POST, Path:/subscribe, Field: {}, User: {}, Program: {}",type, userID, subscribeID);
		User user = userService.addToUser(UserField.valueOf(type.toUpperCase()), userID, subscribeID);
		if(user!=null)
			return ResponseEntity.ok(user);
		return ResponseEntity.badRequest().body(null);

	}
	
	@DeleteMapping(value = "/subscribe")
	public ResponseEntity<User> delete(@RequestParam(required = true, value = "type") String type,
			@RequestParam(required = false, value = "user") String userID,
			@RequestParam(required = false, value = "program") String subscribeID) {

		LOGGER.info("Method=DELETE, Path:/subscribe, Field: {}, User: {}, Program: {}",type, userID, subscribeID);
		User user = userService.deleteFromUser(UserField.valueOf(type.toUpperCase()), userID, subscribeID);
		if(user!=null)
			return ResponseEntity.ok(user);
		return ResponseEntity.badRequest().body(null);

	}


	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody Login login) {

		Map<String, Object> results = new HashMap<>();
		
		LOGGER.info("Method: POST, Path: /user/login, Body: {}",login);

		User user = userService.authenticate(login.getEmail(), login.getPassword(), results);
		if (user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(results);
		}
		results.put("user", user);

		return ResponseEntity.ok(results);
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody UserRegistry register) {

		Map<String, String> results = new HashMap<>();

		User user = userService.createUser(register, results);
		if (user == null || user.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(results);
		}

		return authenticateUser(new Login(register.getEmail(), register.getPassword()));
	}

	@PostMapping("/logout")
	public ResponseEntity<Map> logout(@NotNull @RequestHeader("Authorization") String logoutRequest) {
		String email = userService.getEmailFromRequest(logoutRequest);

		if (userService.logoutUser(email)) {
			Map<String, String> response = new HashMap<>();
			response.put("status", "logged out");
			return ResponseEntity.ok(response);
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/delete")
	public ResponseEntity<Map> delete(
			@RequestHeader("Authorization") String authorizationToken,
			@NotNull @Size(min = 8) 
			@RequestBody String password) {
		String email = userService.getEmailFromRequest(authorizationToken);
		Map<String, String> results = new HashMap<>();
		if (!userService.deleteUser(email, password, results)) {
			return ResponseEntity.badRequest().body(results);
		}
		Map<String, String> response = new HashMap<>();
		response.put("status", "deleted");
		return ResponseEntity.ok(response);
	}

	@PostMapping("/make-admin")
	public ResponseEntity<?> makeUserAdmin(@RequestBody UserRegistry registry) {
		Map<String, String> results = new HashMap<>();
		User user = userService.createAdminUser(registry);

		if (user == null || user.isEmpty()) {
			results.put("status", "fail");
			return ResponseEntity.badRequest().body(results);
		}

		return authenticateUser(new Login(registry.getEmail(), registry.getPassword()));
	}

	@GetMapping(value = "/")
	public ResponseEntity<Map> index() {
		return ResponseEntity.ok(Collections.emptyMap());
	}
}
