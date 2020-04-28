import firstJPA.dao.PersonDao;
import firstJPA.domain.Person;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import static firstJPA.domain.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;

public class testingIT {

    EntityManager em = Persistence.createEntityManagerFactory("h2").createEntityManager();
    PersonDao dao = new PersonDao(em);

    @Test
    void testing() {
//        em.getTransaction().begin();
        em.persist(new Person("Bert", 33, MALE, true));
        Person person = em.find(Person.class, 1);
        assertThat(person.getName()).isEqualTo("Bert");

    }
}
