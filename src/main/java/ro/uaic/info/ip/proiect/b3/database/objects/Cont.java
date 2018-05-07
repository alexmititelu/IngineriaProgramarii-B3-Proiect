package ro.uaic.info.ip.proiect.b3.database.objects;

import ro.uaic.info.ip.proiect.b3.database.Database;

import java.sql.*;

public class Cont {
    String username;
    String email;
    String password;

    public Cont(String username, String email) {
        this.username = username;
        this.email = email;
        this.password = null;
    }

    public Cont(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public static Cont get(String email) {
        String username = null;
        Connection dbConnection = null;
        try {
            dbConnection = Database.getInstance().getConnection();
            String query = "SELECT username FROM conturi WHERE email LIKE ?";
            Statement queryStatement = dbConnection.prepareStatement(query);
            ((PreparedStatement) queryStatement).setString(1, email);

            ResultSet resultSet = ((PreparedStatement) queryStatement).executeQuery();

            while (resultSet.next()) {
                username = resultSet.getString(1);
                break;
            }

        } catch (SQLException e) {
            System.out.println("[" + System.nanoTime() + "] " + e.getMessage());
        } finally {
            try {
                dbConnection.close();
            } catch (SQLException e) {
                System.out.println("[" + System.nanoTime() + "] " + e.getMessage());
            }
        }
        return new Cont(username,email);
    }

    public String getUsername() {
        return username;
    }
}
