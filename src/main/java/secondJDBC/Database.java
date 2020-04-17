package secondJDBC;

import java.sql.*;

import static firstJDBC.MyProperties.get;
import static java.sql.DriverManager.getConnection;

public class Database {

    private Connection makeConnection() throws SQLException {
        return getConnection(get("database.url"), get("database.user"), get("database.password"));
    }

    public int insert(Person person) {
        try (Connection connection = makeConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO person VALUES (?, ?, ?)");) {
            ps.setInt(1, 0);
            ps.setString(2, person.getName());
            ps.setInt(3, person.getAge());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    // TODO split into multiple functions (possibly for generating a query String, method with x amount of parameters
    public int insert(Stuff stuff) {
        try (Connection connection = makeConnection();
             Statement stm = connection.createStatement();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO stuff VALUES (?, ?, ?, ?)");) {

            String query = String.format("SELECT personId FROM person WHERE name='%s'", stuff.getOwner());
            ResultSet resultSet = stm.executeQuery(query);
            resultSet.next();
            int ownerId = resultSet.getInt(1);

            ps.setInt(1, 0);
            ps.setString(2, stuff.getName());
            ps.setInt(3, ownerId);
            ps.setString(4, stuff.getDescription());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // TODO create small delete helper method
    public int clearTable() {
        try (Connection connection = makeConnection()) {
            connection.setAutoCommit(false);
            int deleted = 0;

            try (Statement stm = connection.createStatement()) {
                deleted = stm.executeUpdate("DELETE FROM stuff");
                deleted += stm.executeUpdate("DELETE FROM person");
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
                return  0;
            } finally {
                connection.setAutoCommit(true);
            }
            return deleted;

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
