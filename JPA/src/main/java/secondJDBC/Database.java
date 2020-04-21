package secondJDBC;


import java.sql.*;
import java.util.List;

import static firstJDBC.MyProperties.get;
import static java.sql.DriverManager.getConnection;

public class Database {

    private Connection makeConnection() throws SQLException {
        return getConnection(get("database.url"), get("database.user"), get("database.password"));
    }

    public int[] insert(List<Person> personList) throws SQLException {
        try (Connection connection = makeConnection()) {

            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO person VALUES (?, ?, ?)")) {
                connection.setAutoCommit(false);
                int[] rowsInserted = processList(personList, ps);
                connection.commit();
                return rowsInserted;

            } catch (SQLException e) {
                connection.rollback();
                return null;
            } finally {
                connection.setAutoCommit(true);
            }
        }
    }

    private int[] processList(List<Person> personList, PreparedStatement ps) throws SQLException {
        for (Person person : personList) {
            ps.setInt(1, 0);
            ps.setString(2, person.getName());
            ps.setInt(3, person.getAge());
            ps.addBatch();
        }
        return ps.executeBatch();
    }


    // Testing inner join
    public void printStuffWithOwner() throws SQLException {
        try (Connection connection = makeConnection()) {
            try (Statement ps = connection.createStatement()) {
                String query = "SELECT p.name, s.name, s.description " +
                        "FROM person AS p " +
                        "INNER JOIN stuff AS s " +
                        "ON p.personId = s.owner";
                ResultSet resultSet = ps.executeQuery(query);
                print(resultSet);
            }
        }
    }


    // Print inner join test (for now)
    private void print(ResultSet set) throws SQLException {
        while (set.next()) {
            String owner = set.getString(1);
            String stuffName = set.getString(2);
            String description = set.getString(3);
            System.out.printf("Owner: %s, Thing: %s, Description: %s \n",
                    owner, stuffName, description);

        }
    }

    public int insert(Stuff stuff) {
        try (Connection connection = makeConnection();
             Statement stm = connection.createStatement();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO stuff VALUES (?, ?, ?, ?)")) {

            int ownerId = getOwnerId(stuff, stm);
            return executeStatement(stuff, ps, ownerId);

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private int executeStatement(Person person, PreparedStatement ps) throws SQLException {
        ps.setInt(1, 0);
        ps.setString(2, person.getName());
        ps.setInt(3, person.getAge());
        return ps.executeUpdate();
    }

    public int insert(Person person) {
        try (Connection connection = makeConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO person VALUES (?, ?, ?)")) {
            return executeStatement(person, ps);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Created for supporting printing of different resultsets.
    private String[] getMeta(ResultSet set) throws SQLException {
        ResultSetMetaData metaData = set.getMetaData();
        String[] columnTypes = new String[metaData.getColumnCount()];
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            columnTypes[i - 1] = metaData.getColumnTypeName(i);
        }
        return columnTypes;
    }


    private int getOwnerId(Stuff stuff, Statement stm) throws SQLException {
        String query = String.format("SELECT personId FROM person WHERE name='%s'", stuff.getOwner());
        ResultSet resultSet = stm.executeQuery(query);
        resultSet.next();
        return resultSet.getInt(1);
    }

    private int executeStatement(Stuff stuff, PreparedStatement ps, int ownerId) throws SQLException {
        ps.setInt(1, 0);
        ps.setString(2, stuff.getName());
        ps.setInt(3, ownerId);
        ps.setString(4, stuff.getDescription());
        return ps.executeUpdate();
    }


    public int clearTable() throws SQLException {
        try (Connection connection = makeConnection()) {
            connection.setAutoCommit(false);
            int deleted;

            try (Statement stm = connection.createStatement()) {
                deleted = stm.executeUpdate("DELETE FROM stuff");
                deleted += stm.executeUpdate("DELETE FROM person");
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
                return 0;
            } finally {
                connection.setAutoCommit(true);
            }

            return deleted;
        }
    }
}
