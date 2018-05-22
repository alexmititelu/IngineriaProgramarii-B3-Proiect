package ro.uaic.info.ip.proiect.b3.database.objects.registerlink;

import ro.uaic.info.ip.proiect.b3.database.Database;
import ro.uaic.info.ip.proiect.b3.database.objects.profesor.Profesor;
import ro.uaic.info.ip.proiect.b3.database.objects.registerlink.exceptions.RegisterLinkException;
import ro.uaic.info.ip.proiect.b3.database.objects.student.Student;
import ro.uaic.info.ip.proiect.b3.generators.TokenGenerator;

import java.sql.*;

public class RegisterLink {
    private long id;
    private String email;
    private String token;
    private Date creationTime;

    public RegisterLink(String email) throws SQLException, RegisterLinkException {
        validateData(email);

        this.email = email;
        this.token = TokenGenerator.getToken(64, "register_links");
    }

    private RegisterLink(long id, String email, String token, Date creationTime) {
        this.id = id;
        this.email = email;
        this.token = token;
        this.creationTime = creationTime;
    }

    public static RegisterLink getById(long id) throws SQLException {
        RegisterLink registerLink;
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, email, token, creation_time FROM register_links WHERE id = ?"
        );

        preparedStatement.setLong(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            registerLink = new RegisterLink(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(4)
            );
        } else {
            registerLink = null;
        }

        connection.close();
        return registerLink;
    }

    public static RegisterLink getByEmail(String email) throws SQLException {
        RegisterLink registerLink;
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, email, token, creation_time FROM register_links WHERE email = ?"
        );

        preparedStatement.setString(1, email);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            registerLink = new RegisterLink(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(4)
            );
        } else {
            registerLink = null;
        }

        connection.close();
        return registerLink;
    }

    public static RegisterLink getByToken(String token) throws SQLException {
        RegisterLink registerLink;
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, email, token, creation_time FROM register_links WHERE token = ?"
        );

        preparedStatement.setString(1, token);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            registerLink = new RegisterLink(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(4)
            );
        } else {
            registerLink = null;
        }

        connection.close();
        return registerLink;
    }

    private void validateEmail(String email) throws SQLException, RegisterLinkException {
        Student student = Student.getByEmail(email);
        Profesor profesor = Profesor.getByEmail(email);

        if (student == null && profesor == null) {
            throw new RegisterLinkException("Nu se poate genera un link de inregistrare pentru un email ce nu apartine unui student sau profesor!");
        }
    }

    private void validateData(String email) throws SQLException, RegisterLinkException {
        validateEmail(email);
    }

    public void insert() throws SQLException {
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO register_links (email, token, creation_time) VALUES (?, ?, CURRENT_TIMESTAMP)"
        );

        preparedStatement.setString(1, email);
        preparedStatement.setString(2, token);

        preparedStatement.executeUpdate();

        connection.close();
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public Date getCreationTime() {
        return creationTime;
    }
}
