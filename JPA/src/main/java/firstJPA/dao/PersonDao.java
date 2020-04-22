package firstJPA.dao;

import SQLPlayground.DAO.PersonDAO;
import firstJPA.domain.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;


public class PersonDao {


    private Logger log = LoggerFactory.getLogger(PersonDAO.class);
    private final EntityManager em;

    public PersonDao(EntityManager em) {
        this.em = em;
    }

    public void insert(Person person) {
        em.getTransaction().begin();
        em.persist(person);
        em.getTransaction().commit();
    }

    public Person getById(int id) {
        return em.find(Person.class, id);
    }

    public List<Person> getAll() {
        log.debug("Getting all persons");
//        Using named query
        return em.createNamedQuery("getAll", Person.class)
                .getResultList();

//        Using normal query
//        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
//        return query.getResultList();
    }

    public List<Person> getAll(String name) {

//        Using named query
        return em.createNamedQuery("getAllByName", Person.class)
                .setParameter("name" , name)
                .getResultList();

//        Using normal query
//        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.name = :name", Person.class);
//        query.setParameter("name", name);
//        return query.getResultList();
    }

    public void delete(int id) {
        log.debug("Removing person with id: " + id);

        Person person = em.find(Person.class, id);
        em.getTransaction().begin();
        em.remove(person);
        em.getTransaction().commit();
    }

    public void delete(Person p) {
        em.getTransaction().begin();
        em.remove(p);
        em.getTransaction().commit();
    }

    public void updateName(int id, String name) {
        log.debug("Changing name of person: " + id + " to: " + name);
        Person person = em.find(Person.class, id);
        em.getTransaction().begin();
        person.setName(name);
        em.getTransaction().commit();
    }

    public Person update(Person p) {
        em.getTransaction().begin();
        Person merged = em.merge(p);
        em.getTransaction().commit();
        return merged;
    }
}
