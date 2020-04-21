package firstJDBC;

import secondJDBC.Person;

import java.sql.*;

import static firstJDBC.MyProperties.get;

public class App {
    public static void main(String[] args) {

        Person hannes = new Person("Hannes", 34);
        Person linda = new Person("Linda", 45);
        Person linda2 = new Person("Linda", 55);

        try (Connection connection = connectDB()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM test");

            statement.executeUpdate(insert(hannes));
            statement.executeUpdate(insert(linda));
            statement.executeUpdate(insert(linda2));

            statement.executeUpdate(update(linda2, "Belinda"));
            statement.executeUpdate(update(linda2, 77));

            statement.executeUpdate(delete(linda));

            System.out.println("Printing table contents ---------------------------");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM test");
            printResults(resultSet);

            System.out.println("Printing table metadata ---------------------------");
            ResultSetMetaData metaData = resultSet.getMetaData();
            printMetaData(metaData);

            resultSet.close();
            statement.close();
            connectDB().close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void printMetaData(ResultSetMetaData metaData) throws SQLException {
        int columns = metaData.getColumnCount();
        System.out.println("Catalog name: " + metaData.getCatalogName(1));
        System.out.println("Table name: " + metaData.getTableName(1));

        for (int i = 1; i <= columns; i++) {
            System.out.println("Column: " + metaData.getColumnLabel(i)
                    + ", type: " + metaData.getColumnTypeName(i)
                    + ", precision: " + metaData.getPrecision(i)
                    + ", auto-increment: " + metaData.isAutoIncrement(i));
        }

    }

    public static String insert(Person person) {
        int id = person.hashCode();
        String name = person.getName();
        int age = person.getAge();
        return String.format("INSERT INTO test VALUES ('%s', '%s', %s)", id, name, age);
    }

    public static String delete(Person person) {
        String name = person.getName();
        int age = person.getAge();
        return String.format("DELETE FROM test WHERE naam='%s' AND leeftijd=%s", name, age);
    }

    public static String update(Person person, String newName, int newAge) {
        int id = person.hashCode();
        String name = person.getName();
        int age = person.getAge();
        return String.format("UPDATE test SET naam='%s', leeftijd=%s WHERE id='%s'",
                newName, newAge, id);
    }

    public static String update(Person person, String newName) {
        return update(person, newName, person.getAge());
    }

    public static String update(Person person, int newAge) {
        return update(person, person.getName(), newAge);
    }


    public static Connection connectDB() throws SQLException {
        return DriverManager.getConnection(get("database.url"), get("database.user"), get("database.password"));
    }

    private static void printResults(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            String naam = resultSet.getString("naam");
            int leeftijd = resultSet.getInt("leeftijd");
            System.out.println("Naam: " + naam);
            System.out.println("Leeftijd: " + leeftijd);
        }
    }
}
