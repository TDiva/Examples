package entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class FilmGenreId implements Serializable {

	private static final long serialVersionUID = 3502674941735457595L;

	@ManyToOne
	private Film film;

	@ManyToOne
	private Genre genre;

	public FilmGenreId() {
	}

	public FilmGenreId(Film film, Genre genre) {
		this.film = film;
		this.genre = genre;
	}

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	@Override
	public int hashCode() {
		final int prime = 3179;
		return (int) (film.hashCode() * prime + genre.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FilmGenreId) {
			FilmGenreId id = (FilmGenreId) obj;
			return film.equals(id.getFilm()) && genre.equals(id.getGenre());
		}
		return false;
	}

}
