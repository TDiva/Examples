package entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Media")
public class Media {

	@Id
	private String type;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "id.media")
	private Set<FilmMedia> films;

	public Media() {
	}

	public Media(String type) {
		super();
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Set<FilmMedia> getFilms() {
		return films;
	}

	public void setFilms(Set<FilmMedia> films) {
		this.films = films;
	}

	@Override
	public int hashCode() {
		return type.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Media) {
			Media m = (Media) o;
			return type.equals(m.getType());
		}
		return false;
	}
	
	@Override
	public String toString() {
		return type;
	}

}
