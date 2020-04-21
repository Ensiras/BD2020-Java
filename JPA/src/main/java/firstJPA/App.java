package firstJPA;

import firstJPA.dao.PersonDao;
import firstJPA.domain.Gender;
import firstJPA.domain.Person;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

import static firstJPA.domain.Gender.*;

public class App {

    public static void main(String[] args) {
        new App().start();
    }

    private void start() {
        EntityManager em = Persistence.createEntityManagerFactory("MySQL").createEntityManager();

        PersonDao dao = new PersonDao(em);

        dao.insert(new Person("Bert", 34, MALE));
//        dao.insert(new Person("Jannie", 67, FEMALE));
//        dao.insert(new Person("Herbert", 32, UNKNOWN));

        List<Person> personList = dao.getAll();
        List<Person> bertList = dao.getByName("Bert");

        for (Person person : bertList) {
            System.out.println(person.getName() + " " + person.getAge());
        }

        Person person = dao.getById(7);
        System.out.printf("Name: %s \nAge: %d \nGender: %s",
                person.getName(), person.getAge(), person.getGender());
    }
}
