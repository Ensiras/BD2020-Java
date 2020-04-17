package secondJDBC;

import java.sql.*;
import java.util.List;

import static firstJDBC.MyProperties.get;
import static java.sql.DriverManager.getConnection;

public class Database {

    private Connection makeConnection() throws SQLException {
        return getConnection(get("database.url"), get("database.user"), get("database.password"));
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

    private int executeStatement(Person person, PreparedStatement ps) throws SQLException {
        ps.setInt(1, 0);
        ps.setString(2, person.getName());
        ps.setInt(3, person.getAge());
        return ps.executeUpdate();
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

    public int insert (Stuff stuff){
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

        private int getOwnerId (Stuff stuff, Statement stm) throws SQLException {
            String query = String.format("SELECT personId FROM person WHERE name='%s'", stuff.getOwner());
            ResultSet resultSet = stm.executeQuery(query);
            resultSet.next();
            return resultSet.getInt(1);
        }

        private int executeStatement (Stuff stuff, PreparedStatement ps,int ownerId) throws SQLException {
            ps.setInt(1, 0);
            ps.setString(2, stuff.getName());
            ps.setInt(3, ownerId);
            ps.setString(4, stuff.getDescription());
            return ps.executeUpdate();
        }


        public int clearTable () {
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

            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }
