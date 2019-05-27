package cineflix.models;

import java.util.List;

public class UserPreference {

	private List<String> movies;
	private List<String> stars;
	private List<String> genres;
	
	public List<String> getMovies() {
		return movies;
	}
	public void setMovies(List<String> movies) {
		this.movies = movies;
	}
	public List<String> getStars() {
		return stars;
	}
	public void setStars(List<String> stars) {
		this.stars = stars;
	}
	public List<String> getGenres() {
		return genres;
	}
	public void setGenres(List<String> genres) {
		this.genres = genres;
	}
}
