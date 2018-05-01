package ro.uaic.info.ip.proiect.b3.authentication;

import ro.uaic.info.ip.proiect.b3.database.DatabaseConnectionPool;
import java.sql.*;

public class AuthenticationManager {
    public static boolean isUserLoggedIn(String loginCookie) {
        Connection connection = null;
        boolean isUserConnected = false;

        try {
            connection = DatabaseConnectionPool.getInstance().getConnection();

            String query = "SELECT username FROM conturi_conectate WHERE token like ?";
            Statement preparedStatement = connection.prepareStatement(query);
            ((PreparedStatement) preparedStatement).setString(1, loginCookie);

            ResultSet resultSet = ((PreparedStatement) preparedStatement).executeQuery();
            if (resultSet.next())
                isUserConnected = true;
        } catch (SQLException e) {
            System.err.println("SQLException: " + e);
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("SQLException: " + e);
                e.printStackTrace();
            }
        }

        return isUserConnected;
    }

    public static String getUserLoggedIn(String loginCookie) {
        Connection connection = null;
        String connectedUser = null;

        try {
            connection = DatabaseConnectionPool.getInstance().getConnection();

            String query = "SELECT username FROM conturi_conectate WHERE token like ?";
            Statement preparedStatement = connection.prepareStatement(query);
            ((PreparedStatement) preparedStatement).setString(1, loginCookie);

            ResultSet resultSet = ((PreparedStatement) preparedStatement).executeQuery();

            while(resultSet.next()) {
                connectedUser = resultSet.getString(1);
            }

        } catch (SQLException e) {
            System.err.println("SQLException: " + e);
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("SQLException: " + e);
                e.printStackTrace();
            }
        }

        return connectedUser;
    }

    public static boolean isLoginDataValid(String username, String password) {
        Connection connection = null;
        boolean isDataValid = false;

        try {
            connection = DatabaseConnectionPool.getInstance().getConnection();

            String query = "SELECT * FROM conturi WHERE username like ? and password like ?";
            Statement preparedStatement = connection.prepareStatement(query);
            ((PreparedStatement) preparedStatement).setString(1, username);
            ((PreparedStatement) preparedStatement).setString(2, password);

            ResultSet resultSet = ((PreparedStatement) preparedStatement).executeQuery();
            if (resultSet.next())
                isDataValid = true;
        } catch (SQLException e) {
            System.err.println("SQLException: " + e);
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("SQLException: " + e);
                e.printStackTrace();
            }
        }

        return isDataValid;
    }
}
