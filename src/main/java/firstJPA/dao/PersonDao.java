package firstJPA.dao;

import firstJPA.domain.Person;


import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;


public class PersonDao {

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
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
        return query.getResultList();
    }

    public List<Person> getByName(String name) {
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.name = :name", Person.class);
        query.setParameter("name", name);
        return query.getResultList();
    }


}
