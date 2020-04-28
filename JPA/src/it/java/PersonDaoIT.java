import firstJPA.dao.CarDao;
import firstJPA.dao.PersonDao;
import firstJPA.domain.Car;
import firstJPA.domain.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

import static firstJPA.domain.Gender.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class PersonDaoIT {

    EntityManager em;
    PersonDao dao;
    Person bert;

    @BeforeEach
    void setUp() {
        em = Persistence.createEntityManagerFactory("h2").createEntityManager();
        dao = new PersonDao(em);
        bert = new Person("Bert", 33, MALE, true);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void whenInsertingPersonShouldBeSavedToDB() {
        dao.insert(bert);
        Person DBPerson = dao.getById(1);

        assertThat(DBPerson.toString())
                .isNotNull()
                .contains("Bert", "33", "MALE", "true");
    }

    @Test
    void whenUpdatingPersonShouldBeSavedToDB() {
        dao.insert(bert);
        bert.setBiography("A long and quite boring story about his eventless upbringing.");

        dao.update(bert);
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
        bert.setLeaseCar(new Car("Mazda", "Green"));
        CarDao carDao = new CarDao(em);
        dao.insert(bert);

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

    @Test
    void whenGetAllShouldReturnListOfAllPersons() {
        Person linda = new Person("Linda", 55, FEMALE, true);
        Person x33 = new Person("X33", 6, UNKNOWN, false);
        dao.insert(bert);
        dao.insert(linda);
        dao.insert(x33);

        List<Person> DBPersons = dao.getAll();

        assertAll("DBPersons list",
                () -> assertThat(DBPersons.size()).isEqualTo(3),
                () -> assertThat(DBPersons).contains(bert, linda, x33)
        );
    }

    @Test
    void whenGetAllByNameShouldReturnListOfPeopleWithThatName() {
        Person linda = new Person("Linda", 55, FEMALE, true);
        Person x33 = new Person("X33", 6, UNKNOWN, false);
        Person bert2 = new Person("Bert", 20, MALE, true);
        dao.insert(bert);
        dao.insert(bert2);
        dao.insert(linda);
        dao.insert(x33);

        List<Person> bertList = dao.getAll("Bert");

        assertThat(bertList).contains(bert, bert2);
    }

    @Test
    void whenDeletingPersonShouldBeDeletedFromDB() {
        dao.insert(bert);

        dao.delete(bert);

        assertThat(dao.getAll("Bert"))
                .doesNotContain(bert)
                .isNotNull();
    }

    @Test
    void whenUpdateNamePersonsNameShouldBeChangedinDB() {
        dao.insert(bert);
        Person beforeChange = dao.getById(1);
        em.detach(beforeChange); // Detaching necessary to make entity unmanaged

        dao.updateName(1, "Bertus");
        Person afterChange = dao.getById(1);

        assertThat(beforeChange.getName()).isEqualTo("Bert");
        assertThat(afterChange.getName()).isEqualTo("Bertus");
    }

    // TODO: testing more relationships


}
