package test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import config.HibernateUtil;
import entity.Film;
import entity.FilmGenre;
import entity.FilmGenreId;
import entity.Genre;
import entity.Person;

public class HibernateTest {

	private SessionFactory sessionFactory;
	private Session session;

	@Before
	public void init() {
		sessionFactory = HibernateUtil.getSessionAnnotationFactory();
		session = sessionFactory.getCurrentSession();
		session.beginTransaction();
	}

	@Ignore
	@Test
	public void testSaving() {
		Film film = new Film("test1", "test1", 2014, "test1");
		Person person = new Person("test1", "test1");
		Set<Person> actors = new HashSet<>();
		actors.add(person);
		film.setActors(actors);

		session.persist(film);
		System.out.println("Saved film with id = " + film.getId());
		System.out.println("Saved actor with id = " + person.getId());

	}

	@Ignore
	@Test
	public void testManyToManyWithAdditionalColumns() {
		Film film = new Film("test1", "test1", 2014, "test1");
		Genre testGenre = new Genre("test_genre");
		FilmGenreId id = new FilmGenreId();
		id.setFilm(film);
		id.setGenre(testGenre);

		FilmGenre bean = new FilmGenre();
		bean.setId(id);
		bean.setLevel(5l);

		Set<FilmGenre> genres = new HashSet<>();
		genres.add(bean);

		film.setGenres(genres);

		session.save(film);
		// session.save(testGenre);
		// session.save(bean);

	}

	@Ignore
	@Test
	public void testSelect() {
		Criteria c = session.createCriteria(Film.class);
		c.add(Restrictions.eq("id", 10l));
		List<Film> films = c.list();
		for (Film f : films) {
			System.out.println(f.getId() + "\t" + f.getOriginalName());
		}
	}

	@Ignore
	@Test
	public void testComplexPersistence() {
		Criteria c1 = session.createCriteria(Film.class);
		List<Film> films = c1.list();

		Criteria c2 = session.createCriteria(Person.class);
		List<Person> persons = c2.list();

		Film f = films.get(0);
		f.setActors(new HashSet<>(persons));

		session.saveOrUpdate(f);
		System.out.println(f.getId());
	}

	@Ignore
	@Test
	public void testDelete() {
		Criteria c1 = session.createCriteria(Film.class);
		List<Film> films = c1.list();

		Film f = films.get(0);

		System.out.println(f.getId());
		session.delete(f);
		session.getTransaction().commit();
		session = sessionFactory.openSession();
		session.beginTransaction();
		System.out.println(f.getId());
		f.setActors(null);
		session.save(f);
		System.out.println(f.getId());
	}

	@Ignore
	@Test
	public void testUpdate() {
		Criteria c1 = session.createCriteria(Film.class);
		List<Film> films = c1.list();

		Criteria c2 = session.createCriteria(Person.class);
		List<Person> p = c2.list();

		Film f = films.get(0);
		Set<Person> actors = f.getActors();
		actors.remove(actors.iterator().next());
		actors.add(p.get(0));

		session.saveOrUpdate(f);

	}

	@Ignore
	@Test
	public void testSavingDifferentEntities() {
		Criteria c1 = session.createCriteria(Film.class);
		List<Film> films = c1.list();
		Film f = films.get(0);

		Criteria c2 = session.createCriteria(Genre.class);
		List<Genre> genres = c2.list();
		Genre g = genres.get(0);

		FilmGenreId id = new FilmGenreId(f, g);
		FilmGenre fg = new FilmGenre();
		fg.setId(id);
		fg.setLevel(3l);
		Set<FilmGenre> fgset = new HashSet<>();
		fgset.add(fg);
		f.setGenres(null);

		session.saveOrUpdate(f);

		session.getTransaction().commit();
		session = HibernateUtil.getSession();
		session.beginTransaction();

		f.setGenres(fgset);
		session.saveOrUpdate(f);
	}
	
	@Ignore
	@Test
	public void testDeleteRef() {
		Query q = session.createSQLQuery("delete from genre_rate where film_id = " + 4l);
		q.executeUpdate();
	}
	
	@Ignore
	@Test
	public void testNonExistentGenre() {
		Query q = session.getNamedQuery(Film.GET_BY_GENRE_QUERY);
		q.setParameter("genre", "Мистика1");
		q.setResultTransformer(Transformers.aliasToBean(Film.class));
		List<Film> fs = q.list();
		
		if (fs == null) {
			System.out.println("*");
		}
		for (Film f: fs) {
			System.out.println(f.getId());
		}
	}
	
	@Ignore
	@Test
	public void testFunctions() {
		Query q = session.getNamedQuery(Film.GET_NEW_FILMS);
		q.setResultTransformer(Transformers.aliasToBean(Film.class));
		List<Film> fs = q.list();
		
		if (fs == null) {
			System.out.println("*");
		}
		for (Film f: fs) {
			System.out.println(f.getId());
		}
	}

	@Ignore
	@Test
	public void testComplexJoins() {
		Query q = session.getNamedQuery(Person.GET_ACTORS_DIRECT);
		q.setResultTransformer(Transformers.aliasToBean(Person.class));
		List<Person> fs = q.list();
		
		if (fs == null) {
			System.out.println("*");
		}
		for (Person f: fs) {
			System.out.println(f.getId());
		}
	}

	@After
	public void finish() {
		session.getTransaction().commit();
		sessionFactory.close();

	}
}
