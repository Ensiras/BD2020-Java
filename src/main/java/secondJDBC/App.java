package secondJDBC;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) {
        Database db = new Database();
        Person hannes = new Person("Hannes", 45);
        Person jannie = new Person("Jannie", 55);
        Person rita = new Person("Rita", 65);

        Stuff teaCup = new Stuff("Tea Cup", hannes, "A common tea cup.");

        List<Person> people = new ArrayList<>();
        people.add(hannes);
        people.add(jannie);
        people.add(rita);


        int removed = db.clearTable();
        System.out.println("Rows removed: " + removed);

        try {
            int[] inserted = db.insert(people);
            System.out.println("Rows added: " + inserted.length);
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        int inserted = db.insert(hannes);
//        System.out.println("Rows inserted: " + inserted);
//
//        inserted = db.insert(teaCup);
//        System.out.println("Rows inserted: " + inserted);



    }
}
