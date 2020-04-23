package firstJPA;

import firstJPA.dao.PersonDao;
import firstJPA.domain.Department;
import firstJPA.domain.HairColor;
import firstJPA.domain.Laptop;
import firstJPA.domain.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import static firstJPA.domain.Gender.*;
import static firstJPA.domain.HairColor.BLACK;

public class App {

    public static void main(String[] args) {
        new App().start();
    }

    private void start() {
        Logger log = LoggerFactory.getLogger(App.class);
        log.debug("Starting application.");
        log.error("Log file test");
        log.info("Info test");

        EntityManager em = Persistence.createEntityManagerFactory("MySQL").createEntityManager();

        PersonDao dao = new PersonDao(em);

//        insertSomePersons(dao);

        log.info("Get all ------------------------");
        dao.getAll().forEach(p -> log.info(p.toString()));

        log.info("Get all by name-----------------");
        dao.getAll("Bertrand").forEach(p -> log.info(p.toString()));

        log.info("Get by Id ----------------------");
        Person person = dao.getById(7);
        checkIfManaged(log, em, person);

        log.info("Update person's name -----------");
        dao.updateName(9, "Bertje");
        checkIfManaged(log, em, dao.getById(9));

        log.info("Detaching a person -------------");
        dao.detach(person);
        checkIfManaged(log, em, person);

        log.info("Merging person -------------");
        person.setName("Bassie");

        // Note: name is still Bertje, not Bassie
        log.info("Name of 'person' with id 9 in DB: " + dao.getById(9).getName());

        person  = dao.insertDetached(person);
        // Note: after merging (making entity managed again) name is now Bassie
        checkIfManaged(log, em, person);

        log.info("Update full person -------------");
        Person personUpdated = updateFullPerson(dao, person);
        checkIfManaged(log, em, personUpdated);

    }

    private void insertSomePersons(PersonDao dao) {
        dao.insert(new Person("Bert", 34, MALE, true));
        dao.insert(new Person("Jannie", 67, FEMALE, false));
        dao.insert(new Person("Bertrand", 55, MALE, true));
    }

    private void checkIfManaged(Logger log, EntityManager em, Person personUpdated) {
        log.info(personUpdated.toString() + " Entity is managed: " + em.contains(personUpdated));
    }

    private Person updateFullPerson(PersonDao dao, Person person) {
        person.setName("Freek");
        person.setAge(100);
        person.setEmployedAt(new Department("Gebouwbeheer"));
        person.setHairColor(BLACK);
        person.setEmail("Email444@test.com");
        person.addLaptop(new Laptop("DELL"));
        person.addLaptop(new Laptop("HP"));
        return dao.update(person);
    }
}
