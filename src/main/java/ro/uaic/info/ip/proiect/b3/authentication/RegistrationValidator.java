package ro.uaic.info.ip.proiect.b3.authentication;

import ro.uaic.info.ip.proiect.b3.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class RegistrationValidator {
    public boolean isEmailValid(String email) {
        boolean response = true;
        Connection dbConnection = null;

        try {
            dbConnection = Database.getInstance().getConnection();

            String query = "SELECT email FROM conturi WHERE email = ?";
            String query2 = "SELECT email FROM studenti WHERE email = ?";

            Statement queryStatement = dbConnection.prepareStatement(query);
            Statement queryStatement2 = dbConnection.prepareStatement(query2);
            ((PreparedStatement) queryStatement).setString(1, email);
            ((PreparedStatement) queryStatement2).setString(1, email);

            ResultSet resultSet = ((PreparedStatement) queryStatement).executeQuery();
            ResultSet resultSet2 = ((PreparedStatement) queryStatement2).executeQuery();

            if ((!resultSet2.next()) || (resultSet.next())) {
                response = false;
            }
        } catch (Exception e) {
            System.out.println("[" + System.nanoTime() + "] " + e.getMessage());
        } finally {
            try {
                dbConnection.close();
            } catch (Exception e) {
                System.out.println("[" + System.nanoTime() + "] " + e.getMessage());
            }
        }

        return response;
    }

    public boolean isUsernameValid(String username) {
        boolean isUsernameValid = true;
        Connection dbConnection = null;

        try {
            dbConnection = Database.getInstance().getConnection();
            String query = "SELECT username FROM conturi WHERE username = ?";
            Statement queryStatement = dbConnection.prepareStatement(query);
            ((PreparedStatement) queryStatement).setString(1, username);

            ResultSet resultSet = ((PreparedStatement) queryStatement).executeQuery();

            if (resultSet.next() || !checkUsername(username)) {
                isUsernameValid = false;
            }

        } catch (Exception e) {
            System.out.println("[" + System.nanoTime() + "] " + e.getMessage());
        } finally {
            try {
                dbConnection.close();
            } catch (Exception e) {
                System.out.println("[" + System.nanoTime() + "] " + e.getMessage());
            }
        }

        return isUsernameValid;
    }

    private boolean checkUsername(String username) {
        if (username.length() < 6 || username.length() > 30)
            return false;

        if (!username.matches("([A-Z]|[a-z]|[0-9])+")) {
            return false;
        }

        return true;
    }

    public boolean isPasswordValid(String password) {
        if (password.length() < 8)
            return false;

        if (!password.matches("([A-Z]|[a-z]|[0-9])+")) {
            return false;
        }
        return true;
    }
}
