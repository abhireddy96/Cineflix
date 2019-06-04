package cineflix.models;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegistry implements Serializable{

	private static final long serialVersionUID = 6359376122298915644L;

	@NotNull(message = "`name` field is mandatory")
	@Size(min = 3, message = "`name` must be at least 3 characters long")
	private String name;

	@NotNull(message = "`email` field is mandatory")
	@Email(message = "`email` must be an well-formed email address")
	private String email;

	@NotNull(message = "`password` field is mandatory")
	@Size(min = 8, message = "`password` must be at least 8 characters long")
	private String password;

	public UserRegistry() {}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
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
		UserRegistry other = (UserRegistry) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("UserRegistry [name=%s, email=%s, password=%s]", name, email, password);
	}


}
