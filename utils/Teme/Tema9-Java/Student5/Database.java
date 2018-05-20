
//import oracle.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/musicalbums?autoReconnect=true&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "Reformulare31.";

    private static Connection connection = null;

    private Database() { }

    public static void createConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
            connection.setAutoCommit(false);
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {

        if (connection == null) {
            createConnection();
        }

        return connection;
    }

    public static void commit() {

        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void rollback() {

        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection() {

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}