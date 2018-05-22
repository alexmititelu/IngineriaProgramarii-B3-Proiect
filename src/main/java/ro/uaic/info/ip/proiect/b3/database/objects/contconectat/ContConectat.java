package ro.uaic.info.ip.proiect.b3.database.objects.contconectat;

import ro.uaic.info.ip.proiect.b3.database.Database;
import ro.uaic.info.ip.proiect.b3.database.objects.cont.Cont;
import ro.uaic.info.ip.proiect.b3.database.objects.contconectat.exceptions.ContConectatException;
import ro.uaic.info.ip.proiect.b3.generators.TokenGenerator;

import java.sql.*;

public class ContConectat {
    private long id;
    private String username;
    private String token;
    private Date creationTime;

    public ContConectat(String username) throws ContConectatException, SQLException {
        validateData(username);

        this.username = username;
        this.token = TokenGenerator.getToken(64, "conturi_conectate");
    }

    private ContConectat(long id, String username, String token, Date creationTime) {
        this.id = id;
        this.username = username;
        this.token = token;
        this.creationTime = creationTime;
    }

    public static ContConectat getById(long id) throws SQLException {
        ContConectat contConectat;
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, username, token, creation_time FROM conturi_conectate WHERE id = ?");

        preparedStatement.setLong(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            contConectat = new ContConectat(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(4));
        } else {
            contConectat = null;
        }

        connection.close();
        return contConectat;
    }

    public static ContConectat getByUsername(String username) throws SQLException {
        ContConectat contConectat;
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, username, token, creation_time FROM conturi_conectate WHERE username = ?");

        preparedStatement.setString(1, username);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            contConectat = new ContConectat(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(4));
        } else {
            contConectat = null;
        }

        connection.close();
        return contConectat;
    }

    public static ContConectat getByToken(String token) throws SQLException {
        ContConectat contConectat;
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, username, token, creation_time FROM conturi_conectate WHERE token = ?");

        preparedStatement.setString(1, token);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            contConectat = new ContConectat(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(4));
        } else {
            contConectat = null;
        }

        connection.close();
        return contConectat;
    }

    private void validateUsername(String username) throws SQLException, ContConectatException {
        Cont cont = Cont.getByUsername(username);

        if (cont == null) {
            throw new ContConectatException("Numele de utilizator este invalid!");
        }
    }

    private void validateData(String username) throws ContConectatException, SQLException {
        validateUsername(username);
    }

    public void insert() throws SQLException {
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO conturi_conectate (username, token, creation_time) VALUES (?, ?, CURRENT_TIMESTAMP)");

        preparedStatement.setString(1, username);
        preparedStatement.setString(2, token);

        preparedStatement.executeUpdate();

        connection.close();
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    public Date getCreationTime() {
        return creationTime;
    }
}
