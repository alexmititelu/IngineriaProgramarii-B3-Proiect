package ro.uaic.info.ip.proiect.b3.database.objects;

import ro.uaic.info.ip.proiect.b3.database.Database;

import java.sql.*;

public class Student {
    private String nrMatricol;
    private String email;

    private Student(String nrMatricol, String email) {
        this.nrMatricol = nrMatricol;
        this.email = email;
    }

    public static Student getByNrMatricol(String nrMatricol) {
        Connection dbConnection = null;
        String email = null;

        try {
            dbConnection = Database.getInstance().getConnection();
            String query = "SELECT email FROM studenti WHERE nr_matricol LIKE ?";
            Statement queryStatement = dbConnection.prepareStatement(query);
            ((PreparedStatement) queryStatement).setString(1, nrMatricol);

            ResultSet resultSet = ((PreparedStatement) queryStatement).executeQuery();

            if (resultSet.next()) {
                email = resultSet.getString(1);
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

        if (email != null) {
            return new Student(nrMatricol, email);
        } else {
            return null;
        }
    }

    public static Student getByEmail(String email) {
        Connection dbConnection = null;
        String nrMatricol = null;

        try {
            dbConnection = Database.getInstance().getConnection();
            String query = "SELECT nr_matricol FROM studenti WHERE email LIKE ?";
            Statement queryStatement = dbConnection.prepareStatement(query);
            ((PreparedStatement) queryStatement).setString(1, email);

            ResultSet resultSet = ((PreparedStatement) queryStatement).executeQuery();

            if (resultSet.next()) {
                nrMatricol = resultSet.getString(1);
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

        if (email != null) {
            return new Student(nrMatricol, email);
        } else {
            return null;
        }
    }

    public String getEmail() {
        return email;
    }
}
