package SQLPlayground.DAO;

import SQLPlayground.Person;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static firstJDBC.MyProperties.get;

public class PersonDAO {

    public int insert(List<Person> personList) throws SQLException {
        try (Connection con = getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("INSERT INTO person (name, age) VALUES (?, ?)")) {
                con.setAutoCommit(false);
                int insertedRows = 0;

                for (Person person : personList) {
                    insert(person, ps);
                    insertedRows++;
                }

                ps.executeBatch();
                con.commit();
                con.setAutoCommit(true);
                return insertedRows;

            } catch (SQLException e) {
                System.err.println("Something went wrong while inserting a list of persons " + e.getMessage());
                con.rollback();
                return 0;
            }
        }
    }

    public int insert(Person person) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO person (name, age) VALUES (?, ?)")) {
            insert(person, ps);
            return 1;
        } catch (SQLException e) {
            System.err.println("Something went wrong while inserting a person " + e.getMessage());
            return 0;
        }
    }

    public List<Person> getPersons() {
        try (Connection conn = getConnection();
             Statement stm = conn.createStatement()) {
            List<Person> personList = new ArrayList<>();

            ResultSet set = stm.executeQuery("SELECT * FROM person");
            while (set.next()) {
                personList.add(new Person(
                        set.getString("name"),
                        set.getInt("age")));
            }
            return personList;

        } catch (SQLException e) {
            System.err.println("Could not get persons from table. " + e.getMessage());
            return null;
        }
    }


    public int clearDB() throws SQLException {
        try (Connection con = getConnection()) {
            Statement statement = con.createStatement();
            return statement.executeUpdate("DELETE FROM person, stuff");
        }
    }

    private void insert(Person person, PreparedStatement ps) throws SQLException {
        ps.setString(1, person.getName());
        ps.setInt(2, person.getAge());
        ps.addBatch();
    }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/playground", get("database.user"), get("database.password"));
        } catch (SQLException e) {
            System.err.println("Could not get a connection to the Database " + e.getMessage());
            return null;
        }
    }
}
