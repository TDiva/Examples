package repo;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

import config.HibernateUtil;
import entity.Media;

public class MediaRepository {

	private static MediaRepository instance = new MediaRepository();

	public static MediaRepository getInstance() {
		return instance;
	}

	private MediaRepository() {
	};

	private Session getSession() {
		return HibernateUtil.getSession();
	}

	@SuppressWarnings("unchecked")
	public List<Media> getAll() {
		getSession().beginTransaction();
		Criteria c = getSession().createCriteria(Media.class);
		List<Media> list = c.list();
		getSession().getTransaction().commit();
		return list;
	}

	public void saveOrUpdate(Media m) {
		getSession().beginTransaction();
		getSession().saveOrUpdate(m);
		getSession().getTransaction().commit();
	}

	public void delete(Media m) {
		getSession().beginTransaction();
		getSession().delete(m);
		getSession().getTransaction().commit();
	}

}
