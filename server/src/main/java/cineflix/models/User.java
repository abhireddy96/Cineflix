package cineflix.models;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document("user")
public class User implements Serializable{

	private static final long serialVersionUID = -3235850039268631882L;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((hashedpw == null) ? 0 : hashedpw.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isAdmin ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((preferences == null) ? 0 : preferences.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (hashedpw == null) {
			if (other.hashedpw != null)
				return false;
		} else if (!hashedpw.equals(other.hashedpw))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isAdmin != other.isAdmin)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (preferences == null) {
			if (other.preferences != null)
				return false;
		} else if (!preferences.equals(other.preferences))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("User [id=%s, name=%s, email=%s, hashedpw=%s, isAdmin=%s, preferences=%s]", id, name,
				email, hashedpw, isAdmin, preferences);
	}

}
