package ro.uaic.info.ip.proiect.b3.database.objects;

import ro.uaic.info.ip.proiect.b3.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class RegisterLink {
    private static void deleteRegisterLinks(String email) {
        Connection dbConnection = null;

        try {
            dbConnection = Database.getInstance().getConnection();
            
            String query = "DELETE FROM register_links WHERE email LIKE ?";
            Statement queryStatement = dbConnection.prepareStatement(query);
            ((PreparedStatement) queryStatement).setString(1, email);

            ((PreparedStatement) queryStatement).executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                dbConnection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void add(String token, String email) {
        Connection dbConnection = null;

        try {
            deleteRegisterLinks(email);

            dbConnection = Database.getInstance().getConnection();
            String query = "INSERT INTO register_links (token,email) VALUES (?,?)";
            Statement queryStatement = dbConnection.prepareStatement(query);
            ((PreparedStatement) queryStatement).setString(1, token);
            ((PreparedStatement) queryStatement).setString(2, email);

            ((PreparedStatement) queryStatement).executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                dbConnection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
