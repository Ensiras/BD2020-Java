import firstJPA.dao.CarDao;
import firstJPA.dao.PersonDao;
import firstJPA.domain.Car;
import firstJPA.domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import static firstJPA.domain.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class PersonDaoTest {

    EntityManager em;
    PersonDao dao;
    Person person;

    @BeforeEach
    void setUp() {
        em = Persistence.createEntityManagerFactory("h2").createEntityManager();
        dao = new PersonDao(em);
        person = new Person("Bert", 33, MALE, true);
    }

    @Test
    void whenInsertingPersonShouldBeSavedToDB() {
        dao.insert(person);
        Person DBPerson = dao.getById(1);

        assertThat(DBPerson.toString())
                .isNotNull()
                .contains("Bert", "33", "MALE", "true");
    }

    @Test
    void whenUpdatingPersonShouldBeSavedToDB() {
        dao.insert(person);
        person.setBiography("A long and quite boring story about his eventless upbringing.");

        dao.update(person);
        Person DBPerson = dao.getById(1);

        assertAll("DBPerson",
                () -> assertThat(DBPerson.toString())
                        .isNotNull()
                        .contains("Bert", "33", "MALE", "true"),
                () -> assertThat(DBPerson.getBiography())
                        .isNotNull()
                        .isEqualTo("A long and quite boring story about his eventless upbringing.")
        );
    }

    @Test
    void whenAddingNonExistentCarToPersonCarShouldBeCreatedAndLinkedToPerson() {
        person.setLeaseCar(new Car("Mazda", "Green"));
        CarDao carDao = new CarDao(em);
        dao.insert(person);

        Person DBPerson = dao.getById(1);
        Car personCar = carDao.getByBrand("Mazda");

        assertAll("DBPerson lease car",
                () -> assertThat(DBPerson.getLeaseCar())
                        .isNotNull(),
                () -> assertThat(DBPerson.getLeaseCar().toString())
                        .contains("Mazda", "Green"),
                () -> assertThat(personCar)
                        .isNotNull(),
                () -> assertThat(personCar.toString())
                        .contains("Mazda", "Green"));
    }


}
