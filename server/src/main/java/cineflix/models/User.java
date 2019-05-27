package cineflix.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document("user")
public class User {

	@Id
	@JsonProperty("id")
	private String id;
	private String name;
	private String email;
	@JsonIgnore private String hashedpw;
	private boolean isAdmin;
	private UserPreference preferences ;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UserPreference getPreferences() {
		return preferences;
	}

	public void setPreferences(UserPreference preferences) {
		this.preferences = preferences;
	}

	public User() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHashedpw() {
		return hashedpw;
	}

	public void setHashedpw(String hashedpw) {
		this.hashedpw = hashedpw;
	}

	@JsonIgnore
	public boolean isEmpty() {
		return this.email == null || "".equals(this.getEmail());
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean admin) {
		isAdmin = admin;
	}
}
