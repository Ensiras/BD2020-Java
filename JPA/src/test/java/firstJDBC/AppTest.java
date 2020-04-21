package firstJDBC;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import secondJDBC.Person;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.assertj.core.api.Assertions.assertThat;

class AppTest {

    Connection connection;
    Statement statement;
    Person hannes = new Person("Hannes", 43);
    Person linda = new Person("Linda", 35);
    Person jannie = new Person("Jannie", 66);


    @BeforeEach
    void setUp() {
        try {
            connection = App.connectDB();
            statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM test");
            statement.executeUpdate(App.insert(hannes));
            statement.executeUpdate(App.insert(jannie));
            statement.executeUpdate(App.insert(linda));
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Something went wrong!");
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        statement.close();
        connection.close();
    }

    @Test
    void insertRow() {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM test WHERE naam='Hannes'");
            resultSet.next();

            int resultHash = resultSet.getInt(1);
            String resultName = resultSet.getString(2);

            assertThat(resultHash).isEqualTo(hannes.hashCode());
            assertThat(resultName).isEqualTo("Hannes");

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Something went wrong!");
        }
    }

    @Test
    void deleteRow() throws SQLException {
        statement.executeUpdate(App.delete(jannie));

        ResultSet resultSet = statement.executeQuery("SELECT * FROM test");

        while(resultSet.next()) {
            String name = resultSet.getString(2);
            assertThat(name).isNotNull().isNotEqualTo("Jannie");
        }
    }

    @Test
    void updatePersonInDatabase() throws SQLException {
        statement.executeUpdate(App.update(jannie, 55));
        int id = jannie.hashCode();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM test WHERE id=" +id);
        resultSet.next();
        String resultName = resultSet.getString(2);
        int updatedAge = resultSet.getInt(3);

        assertThat(updatedAge).isEqualTo(55);
        assertThat(resultName).isEqualTo("Jannie");

    }
}