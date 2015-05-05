package repo;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import config.HibernateUtil;
import entity.Person;

public class PersonRepository {

	private static PersonRepository instance = new PersonRepository();

	public static PersonRepository getInstance() {
		return instance;
	}

	private PersonRepository() {
	};

	private Session getSession() {
		return HibernateUtil.getSession();
	}

	@SuppressWarnings("unchecked")
	public List<Person> getAll() {
		getSession().beginTransaction();
		Criteria c = getSession().createCriteria(Person.class);
		List<Person> list = c.list();
		getSession().getTransaction().commit();
		return list;
	}

	public void saveOrUpdate(Person p) {
		getSession().beginTransaction();
		getSession().saveOrUpdate(p);
		getSession().getTransaction().commit();
	}

	public void delete(Person p) {
		getSession().beginTransaction();
		getSession().delete(p);
		getSession().getTransaction().commit();
	}
	
	@SuppressWarnings("unchecked")
	public List<Person> findActorsDirected() {
		getSession().beginTransaction();
		Query q = getSession().getNamedQuery(Person.GET_ACTORS_DIRECT);
		q.setResultTransformer(Transformers.aliasToBean(Person.class));
		List<Person> p = q.list();
		getSession().getTransaction().commit();
		return p;
	}

}
