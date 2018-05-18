
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USER = "STUDENT";
    private static final String PASSWORD = "STUDENT";
    private static Connection connection = null;
    private Database() {
        
    }
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (connection == null) {
            createConnection(); 
        }
        return connection;
    }
    private static void createConnection() throws SQLException, ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");  
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }
    public static void closeConnection() throws SQLException {
        connection.close();
    }
    public static void commit() throws SQLException {
        connection.commit();
    }
    public static void rollback() throws SQLException {
        connection.rollback();
    }   
}