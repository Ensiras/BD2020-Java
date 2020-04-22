package firstJPA;

import firstJPA.dao.PersonDao;
import firstJPA.domain.Department;
import firstJPA.domain.Laptop;
import firstJPA.domain.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import static firstJPA.domain.Gender.MALE;
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

//        dao.insert(new Person("Bert", 34, MALE));
//        dao.insert(new Person("Jannie", 67, FEMALE));
        dao.insert(new Person("Bertrand", 55, MALE, true));

        log.info("Get all ------------------------");
        dao.getAll().forEach(p -> log.info(p.toString()));

        log.info("Get all by name-----------------");
        dao.getAll("Bertrand").forEach(p -> log.info(p.toString()));

//        dao.delete(8);

        dao.updateName(7, "Frits");

        log.info("Get by Id ----------------------");
        Person person = dao.getById(7);
        log.info(person.toString());

        log.info("Update full person -------------");
        person.setName("Freek");
        person.setAge(103);
        person.setEmployedAt(new Department("Gebouwbeheer"));
        person.setHairColor(BLACK);
        person.setEmail("Email@test.com");
        person.addLaptop(new Laptop("DELL"));
        person.addLaptop(new Laptop("HP"));

        Person updated = dao.update(person);
        log.info(updated.toString());

    }
}
