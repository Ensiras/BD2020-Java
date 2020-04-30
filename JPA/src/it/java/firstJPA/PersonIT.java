package firstJPA;

import firstJPA.dao.CarDao;
import firstJPA.dao.EmployedRecord;
import firstJPA.dao.PersonDao;
import firstJPA.domain.Car;
import firstJPA.domain.Department;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonIT {

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

        assertAll("Updated name",
                () -> assertThat(beforeChange.getName()).isEqualTo("Bert"),
                () -> assertThat(afterChange.getName()).isEqualTo("Bertus")
        );
    }

    @Test
    void whenGettingEmployedPersonsNewEmployedRecordsAreCreated() {
        Person linda = new Person("Linda", 55, FEMALE, true);
        Person x33 = new Person("X33", 6, UNKNOWN, false);
        Person bert2 = new Person("Bert", 20, MALE, true);

        bert.setEmployedAt(new Department("Binnentuin"));
        bert2.setEmployedAt(new Department("De boom in"));
        x33.setEmployedAt(new Department("Bijna ontslagen"));

        dao.insert(bert);
        dao.insert(x33);
        dao.insert(bert2);
        dao.insert(linda);
        List<EmployedRecord> employedPersons = dao.getEmployedPersons();

        assertThat(employedPersons.size()).isEqualTo(3);
    }

    @Test
    void whenGettingPersonsAndDepartmentsAlsoUnemployedShouldBeIncluded() {
        Person linda = new Person("Linda", 55, FEMALE, true);
        Person x33 = new Person("X33", 6, UNKNOWN, false);
        Person bert2 = new Person("Bert", 20, MALE, true);

        bert.setEmployedAt(new Department("Binnentuin"));
        bert2.setEmployedAt(new Department("De boom in"));
        x33.setEmployedAt(new Department("Bijna ontslagen"));

        dao.insert(bert);
        dao.insert(x33);
        dao.insert(bert2);
        dao.insert(linda);
        List<EmployedRecord> personsAndDepartments = dao.getPersonsAndDepartments();

        assertThat(personsAndDepartments.size()).isEqualTo(4);
    }

    @Test
    void whenGettingPersonsAndDepartmentsNonTypedResultsShouldBeTheSame() {
        Person linda = new Person("Linda", 55, FEMALE, true);
        Person x33 = new Person("X33", 6, UNKNOWN, false);
        Person bert2 = new Person("Bert", 20, MALE, true);

        bert.setEmployedAt(new Department("Binnentuin"));
        bert2.setEmployedAt(new Department("De boom in"));
        x33.setEmployedAt(new Department("Bijna ontslagen"));

        dao.insert(bert);
        dao.insert(x33);
        dao.insert(bert2);
        dao.insert(linda);
        List<Object[]> PDNonTyped = dao.getPersonsAndDepartmentNonTyped();

        assertThat(PDNonTyped.size()).isEqualTo(4);
        assertThat(PDNonTyped.get(0)[0]).isEqualTo("Bert");
    }

    @Test
    void whenSearchingForNameShouldReturnRecordsContainingThatName() {
        Person bertus = new Person("Bertus", 20, MALE, true);
        Person linda = new Person("Linda", 55, FEMALE, true);
        Person janBert = new Person("Jan-Bert", 45, MALE, true);

        dao.insert(bert);
        dao.insert(bertus);
        dao.insert(linda);
        dao.detach(bert);
        dao.insert(janBert);
        List<Object[]> berts = dao.getAllByNameGuess("Bert");

        assertThat(berts.size()).isEqualTo(3);
    }

    @Test
    void prefixingAllNamesShouldBeDoneInBatches() {
        Person bertus = new Person("Bertus", 20, MALE, true);
        Person linda = new Person("Linda", 55, FEMALE, true);
        Person janBert = new Person("Jan-Bert", 45, MALE, true);

        dao.insert(bert);
        dao.insert(bertus);
        dao.insert(linda);
        dao.insert(janBert);

        dao.prefixAllNames("Prefixed!");

        String name = dao.getById(1).getName();

        assertThat(name).contains("Prefixed!");
    }

    @Test
    void whenGettingByIdUsingCriteriaPersonsShouldBeReturned() {
        dao.insert(bert);
        dao.detach(bert);

        List<Person> byIdCriteria = dao.getByIdCriteria(1);
        Person person = byIdCriteria.get(0);

        assertEquals(bert.getName(), person.getName());

    }
}
