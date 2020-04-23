package firstJPA.dao;

import firstJPA.domain.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.util.List;


public class PersonDao {


    private Logger log = LoggerFactory.getLogger(PersonDao.class);
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
                .setParameter("name", name)
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

    public void detach(Person p) {
        em.detach(p);
    }

    public void delete(Person p) {
        em.getTransaction().begin();
        em.remove(p);
        em.getTransaction().commit();
    }

    public void updateName(int id, String name) {
        Person person = em.find(Person.class, id);
        log.info("Changing name of person: " + id + " to: " + name);
        em.getTransaction().begin();
        person.setName(name);
        em.getTransaction().commit();
    }

    // Note on managed entities, merge() and transactions
    /* Using merge() in update(Person p) is not strictly necessary, since the entity (p)
       is still in managed state. In fact, even getTransaction is not
       necessary, as long as at some point before the application ends a transaction is
       began and committed. But doing it like this is much cleaner, of course.

       Why is this the case?
       Most likely because managed entities are directly linked to their
       respective rows in the database. Any changes to these entities (or in de DB)
       are automatically sent to the DB. However, these changes still have to be
       committed in a transaction to make them permanent. So strictly speaking
       you only need one transaction for the entire application, which will
       commit/save all the changes to the DB. Of course, this is, for multiple reasons
       a very bad idea. But it is possible. */

    public Person update(Person p) {
        log.info("Updating person, person is managed: " + em.contains(p));
        em.getTransaction().begin();
        Person merged = em.merge(p);
        em.getTransaction().commit();
        return merged;
    }

    public Person insertDetached(Person p) {
        em.getTransaction().begin();
        Person merged = em.merge(p);
        em.getTransaction().commit();
        return merged;
    }
}
