package entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class FilmMediaId implements Serializable {

	private static final long serialVersionUID = 6191631695925795907L;

	@ManyToOne
	private Film film;

	@ManyToOne
	private Media media;

	public FilmMediaId() {
	}

	public FilmMediaId(Film film, Media media) {
		this.film = film;
		this.media = media;
	}

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}

	@Override
	public int hashCode() {
		final int prime = 3179;
		int result = 1;
		result = prime * result + ((film == null) ? 0 : film.hashCode());
		result = prime * result + ((media == null) ? 0 : media.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof FilmMediaId) {
			FilmMediaId f = (FilmMediaId) o;
			return film.equals(f.getFilm()) && media.equals(f.getMedia());
		}
		return false;
	}

}
