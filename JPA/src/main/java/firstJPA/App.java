package firstJPA;

import firstJPA.dao.PersonDao;
import firstJPA.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

import java.util.Date;
import java.util.List;

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
        List<Person> freek = dao.getAll("Freek");
        freek.forEach(p -> log.info(p.toString()));

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

        List<Person> persons = dao.getAll("Freek");

        Person person1 = em.find(Person.class, 9);
        person1.setEmployedAt(new Department("Het Frietkot"));
        dao.update(person1);

        Query query = em.createQuery("SELECT p.employedAt.name, p.name FROM Person p");
        List<Object[]> resultList = query.getResultList();

        Query query2 = em.createQuery("SELECT department.name, p.name FROM Person p LEFT OUTER JOIN p.employedAt department");
        List<Object[]> resultList2 = query2.getResultList();

        Query query3 = em.createQuery("SELECT d FROM Department d JOIN FETCH d.workers");
        List<Object[]> resultList3 = query3.getResultList();


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
        person.setDateOfEntry(new Date());
        person.setBiography("Hello, this is a very long text about my life. Since it is so long, I do not want" +
                "to fetch it every time I retrieve this record. So I've annotated it with @Blob and @Basic(fetch = LAZY)" +
                "let's see if it works like it should!");
        person.addPhone(new Phone("Samsung"));
        person.setParkingSpace(new ParkingSpace());
        person.addDepartment(new Department("De ballentent"));
        return dao.update(person);
    }
}
