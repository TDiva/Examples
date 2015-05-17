package entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@NamedQuery(name = Person.GET_ACTORS_DIRECT, query = "select distinct p.id as id, "
		+ "p.originalName as originalName, "
		+ "p.russianName as russianName "
		+ "from Person p inner join p.filmsActed a inner join p.filmsDirected d ") 
@Entity
@Table(name = "actors")
public class Person {
	
	public final static String GET_ACTORS_DIRECT = "Person.GET_ACTORS_DIRECT";
	
	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;

	@Column(name = "original_name")
	private String originalName;

	@Column(name = "russian_name")
	private String russianName;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "actors")
	private Set<Film> filmsActed;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "directors")
	private Set<Film> filmsDirected;

	public Person() {
	}

	public Person(String originalName, String russianName) {
		this.originalName = originalName;
		this.russianName = russianName;
	}
	
	public Person(Long id) {
		this.id = id;
		this.originalName = "";
		this.russianName = "";
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

	public Set<Film> getFilmsActed() {
		return filmsActed;
	}

	public void setFilmsActed(Set<Film> films_acted) {
		this.filmsActed = films_acted;
	}

	public Set<Film> getFilmsDirected() {
		return filmsDirected;
	}

	public void setFilmsDirected(Set<Film> films_directed) {
		this.filmsDirected = films_directed;
	}

	@Override
	public int hashCode() {
		if (id == null) {
			return originalName.hashCode();
		}
		return id.intValue();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Person) {
			Person p = (Person) o;
			return id.equals(p.getId());
		}
		return false;
	}

	public String toString() {
		if (id == null) {
			return "";
		} else if (id == -1) {
			return "не указан";
		}
		return russianName + " (" + id + ")";
	}

}
