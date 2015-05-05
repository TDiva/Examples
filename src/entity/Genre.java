package entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "Genre")
public class Genre {

	@Id
	@Column(name = "name")
	private String name;

	@Fetch(FetchMode.SELECT)
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "id.genre")
	private Set<FilmGenre> films;

	public Genre() {
	}

	public Genre(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<FilmGenre> getFilms() {
		return films;
	}

	public void setFilms(Set<FilmGenre> films) {
		this.films = films;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Genre) {
			Genre g = (Genre) o;
			return name.equals(g.getName());
		}
		return false;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
