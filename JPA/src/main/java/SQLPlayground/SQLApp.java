package SQLPlayground;


import SQLPlayground.DAO.PersonDAO;

import java.util.ArrayList;
import java.util.List;

public class SQLApp {

    public static void main(String[] args) {
        Person hannes = new Person("Hannes", 45);
        Person jannie = new Person("Jannie", 55);
        Person rita = new Person("Rita", 65);
        Person johnny = new Person("Johnny", 56);
        Person bertus = new Person("Bertus", 87);

        List<Person> personList = new ArrayList<>();
        personList.add(hannes);
        personList.add(jannie);
        personList.add(rita);
        personList.add(johnny);
        personList.add(bertus);

        Stuff teaCup = new Stuff("Tea Cup", hannes, "A common tea cup.");
        Stuff coffeeCup = new Stuff("Coffee Cup", hannes, "A common coffee cup.");
        Stuff bowlingBall = new Stuff("Bowling Ball", rita, "Just a bowling ball.");

        List<Stuff> stuffList = new ArrayList<>();
        stuffList.add(teaCup);
        stuffList.add(coffeeCup);
        stuffList.add(bowlingBall);

        PersonDAO PAccess = new PersonDAO();
        try {
//            int inserted = PAcces.insert(personList);
//            System.out.println("Rows inserted: " + inserted);
            List<Person> personListDB= new ArrayList<Person>();
            personListDB = PAccess.getPersons();
            for (Person person : personList) {
                System.out.println(person);
            }
        } catch (Exception e) {
            System.err.println("Some kind of problem occurred, insertion aborted " + e.getMessage());
        }

    }
}
