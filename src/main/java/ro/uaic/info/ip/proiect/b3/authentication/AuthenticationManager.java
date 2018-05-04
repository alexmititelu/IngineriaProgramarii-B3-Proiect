package ro.uaic.info.ip.proiect.b3.authentication;

import ro.uaic.info.ip.proiect.b3.database.Database;
import java.sql.*;

/**
 * Aceasta clasa reprezinta un ajutor pentru a verifica identitatea utilizatorilor.
 */
public class AuthenticationManager {
    /**
     * @param loginToken tokenul de login al unui utilizator
     * @return true in cazul in care tokenul de login al utilizatorului se afla in baza de date si fals in caz contrar
     */
    public static boolean isUserLoggedIn(String loginToken) {
        Connection connection = null;
        boolean isUserConnected = false;

        try {
            connection = Database.getInstance().getConnection();

            String query = "SELECT username FROM conturi_conectate WHERE token like ?";
            Statement preparedStatement = connection.prepareStatement(query);
            ((PreparedStatement) preparedStatement).setString(1, loginToken);

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

    /**
     * @param loginToken tokenul de login al unui utilizator
     * @return username-ul ce corespunde tokenului de login sau null in cazul in care tokenul nu este gasit in baza de date
     */
    public static String getUsernameLoggedIn(String loginToken) {
        Connection connection = null;
        String connectedUser = null;

        try {
            connection = Database.getInstance().getConnection();

            String query = "SELECT username FROM conturi_conectate WHERE token like ?";
            Statement preparedStatement = connection.prepareStatement(query);
            ((PreparedStatement) preparedStatement).setString(1, loginToken);

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

    /**
     * @param username username-ul unui utilzator
     * @param password parola hashuita al utilizatorului -- final String hashedPassword = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
     * @return true in cazul in care datele sunt valide si fals in caz contrar
     */
    public static boolean isLoginDataValid(String username, String password) {
        Connection connection = null;
        boolean isDataValid = false;

        try {
            connection = Database.getInstance().getConnection();

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

    /**
     * @param registerToken un token de inregistrare
     * @return true in cazul in care tokenul se afla in baza de date iar fals in caz contrar
     */
    public static boolean isRegisterTokenValid(String registerToken) {
        Connection connection = null;
        boolean isTokenValid = false;

        try {
            connection = Database.getInstance().getConnection();

            String query = "SELECT * FROM register_links WHERE token like ?";
            Statement preparedStatement = connection.prepareStatement(query);
            ((PreparedStatement) preparedStatement).setString(1, registerToken);

            ResultSet resultSet = ((PreparedStatement) preparedStatement).executeQuery();
            if (resultSet.next())
                isTokenValid = true;
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

        return isTokenValid;
    }

    /**
     * @param registerToken un token de inregistrare
     * @return emailul asociat tokenului de inregistrare dat ca parametru sau null in cazul in care tokenul nu este gasit in baza de date
     */
    public static String getEmailForRegisterToken(String registerToken) {
        Connection connection = null;
        String emailAssociatedWithToken = null;

        try {
            connection = Database.getInstance().getConnection();

            String query = "SELECT email FROM register_links WHERE token like ?";
            Statement preparedStatement = connection.prepareStatement(query);
            ((PreparedStatement) preparedStatement).setString(1, registerToken);

            ResultSet resultSet = ((PreparedStatement) preparedStatement).executeQuery();

            while(resultSet.next()) {
                emailAssociatedWithToken = resultSet.getString(1);
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

        return emailAssociatedWithToken;
    }
}