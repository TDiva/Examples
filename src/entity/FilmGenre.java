package entity;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "genre_rate")
@AssociationOverrides({
		@AssociationOverride(name = "id.film", joinColumns = @JoinColumn(name = "film_id")),
		@AssociationOverride(name = "id.genre", joinColumns = @JoinColumn(name = "genre")) })
public class FilmGenre {

	@EmbeddedId
	private FilmGenreId id = new FilmGenreId();

	@Column(name = "genre_level")
	private Long level;
	
	public FilmGenre() {
	}

	public FilmGenre(FilmGenreId id, Long level) {
		this.id = id;
		this.level = level;
	}



	public FilmGenreId getId() {
		return id;
	}

	public void setId(FilmGenreId id) {
		this.id = id;
	}

	@Transient
	public Film getFilm() {
		return getId().getFilm();
	}

	public void setFilm(Film film) {
		getId().setFilm(film);
	}

	@Transient
	public Genre getGenre() {
		return getId().getGenre();
	}

	public void setGenre(Genre genre) {
		getId().setGenre(genre);
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof FilmGenre) {
			FilmGenre f = (FilmGenre) o;
			return id.equals(f.getId());
		}
		return false;
	}

}
