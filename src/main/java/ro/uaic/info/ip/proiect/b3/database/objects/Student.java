package ro.uaic.info.ip.proiect.b3.database.objects;

import ro.uaic.info.ip.proiect.b3.database.Database;

import java.sql.*;

public class Student {
    String nrMatricol;
    String email;

    public Student(String nrMatricol, String email) {
        this.nrMatricol = nrMatricol;
        this.email = email;
    }

    public static Student get(String nrMatricol) {
        Connection dbConnection = null;
        String email = null;
        try {
            dbConnection = Database.getInstance().getConnection();
            String query = "SELECT email FROM studenti WHERE nr_matricol LIKE ?";
            Statement queryStatement = dbConnection.prepareStatement(query);
            ((PreparedStatement) queryStatement).setString(1, nrMatricol);

            ResultSet resultSet = ((PreparedStatement) queryStatement).executeQuery();

            while (resultSet.next()) {
                email = resultSet.getString(1);
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
        return new Student(nrMatricol,email);
    }

    public String getEmail() {
        return email;
    }
}
