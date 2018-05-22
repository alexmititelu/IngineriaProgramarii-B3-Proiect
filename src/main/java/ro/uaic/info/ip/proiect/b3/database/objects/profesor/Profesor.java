package ro.uaic.info.ip.proiect.b3.database.objects.profesor;

import ro.uaic.info.ip.proiect.b3.database.Database;
import ro.uaic.info.ip.proiect.b3.database.objects.profesor.exceptions.ProfesorException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Profesor {
    private long id;
    private String nume;
    private String prenume;
    private String email;

    public Profesor(String nume, String prenume, String email) throws SQLException, ProfesorException {
        validateData(email);

        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
    }

    private Profesor(long id, String nume, String prenume, String email) {
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
    }

    public static Profesor getById(long id) throws SQLException {
        Profesor profesor;
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, nume, prenume, email FROM profesori WHERE id = ?"
        );

        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            profesor = new Profesor(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
        } else {
            profesor = null;
        }

        connection.close();
        return profesor;
    }

    public static Profesor getByEmail(String email) throws SQLException {
        Profesor profesor;
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, nume, prenume, email FROM profesori WHERE email = ?"
        );

        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            profesor = new Profesor(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
        } else {
            profesor = null;
        }

        connection.close();
        return profesor;
    }

    private void validateEmail(String email) throws SQLException, ProfesorException {
        Profesor profesor = Profesor.getByEmail(email);

        if (profesor != null) {
            throw new ProfesorException("Exista deja un profesor cu acest email!");
        }
    }

    private void validateData(String email) throws SQLException, ProfesorException {
        validateEmail(email);
    }

    public void insert() throws SQLException {
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO profesori (nume, prenume, email) VALUES (?, ?, ?)"
        );

        preparedStatement.setString(1, nume);
        preparedStatement.setString(2, prenume);
        preparedStatement.setString(3, email);

        preparedStatement.executeUpdate();

        connection.close();
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
}
