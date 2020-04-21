package secondJDBC;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) {
        try {
            Database db = new Database();

            Person hannes = new Person("Hannes", 45);
            Person jannie = new Person("Jannie", 55);
            Person rita = new Person("Rita", 65);

            List<Person> people = new ArrayList<>();
            people.add(hannes);
            people.add(jannie);
            people.add(rita);

            Stuff teaCup = new Stuff("Tea Cup", hannes, "A common tea cup.");
            Stuff coffeeCup = new Stuff("Coffee Cup", hannes, "A common coffee cup.");
            Stuff bowlingBall = new Stuff("Bowling Ball", rita, "Just a bowling ball.");

            // Clear database tables
            int removed = db.clearTable();
            System.out.println("Rows removed: " + removed);

            int[] inserted = db.insert(people);
            System.out.println("Rows added: " + inserted.length);

            db.insert(teaCup);
            db.insert(coffeeCup);
            db.insert(bowlingBall);

            db.printStuffWithOwner();


//            int inserted2 = db.insert(hannes);
//            System.out.println("Rows inserted: " + inserted2);
//
//            inserted2 = db.insert(teaCup);
//            System.out.println("Rows inserted: " + inserted2);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
