package JPA.multiPK;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class App {
    public static void main(String[] args) {

        EntityManager em = Persistence.createEntityManagerFactory("MySQL").createEntityManager();

        Person person = new Person();
        person.setFirstName("Thomas");
        person.setLastName("De Vries");
        person.setAge(34);

        em.getTransaction().begin();
        em.persist(person);
        em.getTransaction().commit();
        

    }
}
