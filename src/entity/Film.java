package entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

@NamedQueries(value = {
		@NamedQuery(name = Film.GET_BY_GENRE_QUERY, query = "select film.id as id, "
				+ "film.originalName as originalName, "
				+ "film.russianName as russianName, "
				+ "film.year as year, "
				+ "film.country as country "
				+ "from Film film "
				+ "inner join film.genres as genreRate "
				+ "where genreRate.level >=3 "
				+ "and genreRate.id.genre.name = :genre"),
		@NamedQuery(name = Film.GET_NEW_FILMS, query = "select film.id as id, "
				+ "film.originalName as originalName, "
				+ "film.russianName as russianName, "
				+ "film.year as year, "
				+ "film.country as country "
				+ "from Film film "
				+ "where film.year = YEAR(NOW()) - 1 or film.year = YEAR(NOW())")

})
@Entity
@Table(name = "Films")
public class Film {

	public final static String GET_BY_GENRE_QUERY = "Film.GET_BY_GENRE_QUERY";
	public final static String GET_NEW_FILMS = "Film.GET_NEW_FILMS";

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;

	@Column(name = "original_name")
	private String originalName;

	@Column(name = "russian_name", nullable = false)
	private String russianName;

	@Column(name = "year", nullable = false)
	private Integer year;

	@Column(name = "country", nullable = false)
	private String country;

	@Fetch(FetchMode.SELECT)
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "Films_Actors", joinColumns = { @JoinColumn(name = "film_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "actor_id", nullable = false, updatable = false) })
	private Set<Person> actors;

	@Fetch(FetchMode.SELECT)
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "Films_Directors", joinColumns = { @JoinColumn(name = "film_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "director_id", nullable = false, updatable = false) })
	private Set<Person> directors;

	@Fetch(FetchMode.SELECT)
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "id.film", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<FilmGenre> genres;

	@Fetch(FetchMode.SELECT)
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "id.film", cascade = CascadeType.ALL)
	private Set<FilmMedia> media;

	public Film() {
	}

	public Film(String originalName, String russianName, Integer year,
			String country) {
		this.originalName = originalName;
		this.russianName = russianName;
		this.year = year;
		this.country = country;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getRussianName() {
		return russianName;
	}

	public void setRussianName(String russianName) {
		this.russianName = russianName;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Set<Person> getActors() {
		return actors;
	}

	public void setActors(Set<Person> actors) {
		this.actors = actors;
	}

	public Set<Person> getDirectors() {
		return directors;
	}

	public void setDirectors(Set<Person> directors) {
		this.directors = directors;
	}

	public Set<FilmGenre> getGenres() {
		return genres;
	}

	public void setGenres(Set<FilmGenre> genres) {
		this.genres = genres;
	}

	public Set<FilmMedia> getMedia() {
		return media;
	}

	public void setMedia(Set<FilmMedia> media) {
		this.media = media;
	}

	public int hashCode() {
		if (id == null) {
			return originalName.hashCode();
		}
		return id.intValue();
	}

	public boolean equals(Object o) {
		if (o instanceof Film) {
			Film f = (Film) o;
			return id.equals(f.getId());
		}
		return false;
	}

}
