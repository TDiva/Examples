package repo;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

import config.HibernateUtil;
import entity.Genre;

public class GenreRepository {

	private static GenreRepository instance = new GenreRepository();

	public static GenreRepository getInstance() {
		return instance;
	}

	private GenreRepository() {
	};

	private Session getSession() {
		return HibernateUtil.getSession();
	}

	@SuppressWarnings("unchecked")
	public List<Genre> getAll() {
		getSession().beginTransaction();
		Criteria c = getSession().createCriteria(Genre.class);
		List<Genre> list = c.list();
		getSession().getTransaction().commit();
		return list;
	}

	public void saveOrUpdate(Genre g) {
		getSession().beginTransaction();
		getSession().saveOrUpdate(g);
		getSession().getTransaction().commit();
	}

	public void delete(Genre g) {
		getSession().beginTransaction();
		getSession().delete(g);
		getSession().getTransaction().commit();
	}

}
