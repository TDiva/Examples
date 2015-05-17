package repo;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import config.HibernateUtil;
import entity.Film;

public class FilmRepository {

	private static FilmRepository instance = new FilmRepository();

	public static FilmRepository getInstance() {
		return instance;
	}

	private Session getSession() {
		return HibernateUtil.getSession();
	}

	private FilmRepository() {
	};

	@SuppressWarnings("unchecked")
	public List<Film> getAll() {
		getSession().beginTransaction();
		Criteria c = getSession().createCriteria(Film.class);
		List<Film> list = c.list();
		getSession().getTransaction().commit();
		return list;
	}

	public void saveOrUpdate(Film f) {
		getSession().beginTransaction();
		getSession().saveOrUpdate(f);
		getSession().getTransaction().commit();
	}

	public void delete(Film f) {
		getSession().beginTransaction();
		getSession().delete(f);
		getSession().getTransaction().commit();
	}

	public void deleteGenreRate(Long long1) {
		getSession().beginTransaction();
		Query q = getSession().createSQLQuery(
				"delete from genre_rate where film_id = " + long1);
		q.executeUpdate();
		getSession().getTransaction().commit();
	}

	public void deleteMedia(Long long1) {
		getSession().beginTransaction();
		Query q = getSession().createSQLQuery(
				"delete from video where film_id = " + long1);
		q.executeUpdate();
		getSession().getTransaction().commit();
	}

	@SuppressWarnings("unchecked")
	public List<Film> findByGenre(String genre) {
		getSession().beginTransaction();
		Query q = getSession().getNamedQuery(Film.GET_BY_GENRE_QUERY);
		q.setParameter("genre", genre);
		q.setResultTransformer(Transformers.aliasToBean(Film.class));
		List<Film> f = q.list();
		getSession().getTransaction().commit();
		return f;
	}

	public Film findById(Long id) {
		getSession().beginTransaction();
		Criteria c = getSession().createCriteria(Film.class);
		c.add(Restrictions.eq("id", id));
		Film f = (Film) c.uniqueResult();
		getSession().getTransaction().commit();
		return f;
	}

	@SuppressWarnings("unchecked")
	public List<Film> findNew() {
		getSession().beginTransaction();

		Query q = getSession().getNamedQuery(Film.GET_NEW_FILMS);
		q.setDate("time", new Date());
		q.setResultTransformer(Transformers.aliasToBean(Film.class));
		List<Film> f = q.list();

		getSession().getTransaction().commit();
		return f;
	}

}
