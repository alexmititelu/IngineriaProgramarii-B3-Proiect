package ro.uaic.info.ip.proiect.b3.database.objects.student;

import ro.uaic.info.ip.proiect.b3.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Student {
    private long id;
    private String nume;
    private String prenume;
    private String email;
    private String nrMatricol;

    private Student(long id, String nume, String prenume, String email, String nrMatricol) {
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.nrMatricol = nrMatricol;
    }

    public static Student getById(long id) throws SQLException {
        Student student;
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, nume, prenume, email, nr_matricol FROM studenti WHERE id = ?"
        );

        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            student = new Student(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
        } else {
            student = null;
        }

        connection.close();
        return student;
    }

    public static Student getByEmail(String email) throws SQLException {
        Student student;
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, nume, prenume, email, nr_matricol FROM studenti WHERE email = ?"
        );

        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            student = new Student(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
        } else {
            student = null;
        }

        connection.close();
        return student;
    }

    public static Student getByNrMatricol(String nrMatricol) throws SQLException {
        Student student;
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, nume, prenume, email, nr_matricol FROM studenti WHERE nr_matricol = ?"
        );

        preparedStatement.setString(1, nrMatricol);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            student = new Student(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
        } else {
            student = null;
        }

        connection.close();
        return student;
    }

    public long getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getEmail() {
        return email;
    }

    public String getNrMatricol() {
        return nrMatricol;
    }
}
