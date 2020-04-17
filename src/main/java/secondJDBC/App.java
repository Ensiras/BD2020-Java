package secondJDBC;

public class App {
    //TODO Batch updates, support for stuff table

    public static void main(String[] args) {
        Database db = new Database();
        Person hannes = new Person("Hannes", 45);
        Stuff teaCup = new Stuff("Tea Cup", hannes, "A common tea cup.");

        int removed = db.clearTable();
        System.out.println("Rows removed: " + removed);

        int inserted = db.insert(hannes);
        System.out.println("Rows inserted: " + inserted);

        inserted = db.insert(teaCup);
        System.out.println("Rows inserted: " + inserted);

    }
}
