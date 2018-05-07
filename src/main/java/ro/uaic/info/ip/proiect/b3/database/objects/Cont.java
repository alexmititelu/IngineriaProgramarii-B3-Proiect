package ro.uaic.info.ip.proiect.b3.database.objects;

import ro.uaic.info.ip.proiect.b3.database.Database;

import java.sql.*;

public class Cont {
    private String username;
    private String email;
    private String password;

    private Cont(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public static Cont get(String email) {
        String username = null, password = null;
        Connection dbConnection = null;

        try {
            dbConnection = Database.getInstance().getConnection();

            String query = "SELECT username, password FROM conturi WHERE email LIKE ?";
            Statement queryStatement = dbConnection.prepareStatement(query);
            ((PreparedStatement) queryStatement).setString(1, email);

            ResultSet resultSet = ((PreparedStatement) queryStatement).executeQuery();

            if (resultSet.next()) {
                username = resultSet.getString(1);
                password = resultSet.getString(2);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                dbConnection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        if (username != null) {
            return new Cont(username, email, password);
        } else {
            return null;
        }
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
