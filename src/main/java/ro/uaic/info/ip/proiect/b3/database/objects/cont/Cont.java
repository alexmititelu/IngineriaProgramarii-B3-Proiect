package ro.uaic.info.ip.proiect.b3.database.objects.cont;

import com.google.common.hash.Hashing;
import ro.uaic.info.ip.proiect.b3.database.Database;
import ro.uaic.info.ip.proiect.b3.database.objects.cont.exceptions.ContException;
import ro.uaic.info.ip.proiect.b3.database.objects.contconectat.ContConectat;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Cont {
    private long id;
    private String email;
    private String username;
    private String password;
    private int permission;

    public Cont(String email, String username, String password, int permission) throws SQLException, ContException {
        validateData(email, username, password, permission);

        this.email = email;
        this.username = username;
        this.password = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
        this.permission = permission;
    }

    private Cont(long id, String username, String email, String password, int permissions) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.permission = permissions;
    }

    public static Cont getById(long id) throws SQLException {
        Cont cont;
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, email, username, password, permission FROM conturi WHERE id = ?");

        preparedStatement.setLong(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            cont = new Cont(
                    resultSet.getLong(1),
                    resultSet.getString(3),
                    resultSet.getString(2),
                    resultSet.getString(4),
                    resultSet.getInt(5));
        } else {
            cont = null;
        }

        connection.close();
        return cont;
    }

    public static Cont getByEmail(String email) throws SQLException {
        Cont cont;
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, email, username, password, permission FROM conturi WHERE email = ?");

        preparedStatement.setString(1, email);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            cont = new Cont(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getInt(5));
        } else {
            cont = null;
        }

        connection.close();
        return cont;
    }

    public static Cont getByUsername(String username) throws SQLException {
        Cont cont;
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, email, username, password, permission FROM conturi WHERE username = ?");

        preparedStatement.setString(1, username);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            cont = new Cont(
                    resultSet.getLong(1),
                    resultSet.getString(3),
                    resultSet.getString(2),
                    resultSet.getString(4),
                    resultSet.getInt(5));
        } else {
            cont = null;
        }

        connection.close();
        return cont;
    }

    public static Cont getByLoginToken(String loginToken) throws SQLException {
        ContConectat contConectat = ContConectat.getByToken(loginToken);
        return (contConectat != null) ? Cont.getByUsername(contConectat.getUsername()) : null;
    }

    private void validateEmail(String email) throws SQLException, ContException {
        Cont cont = Cont.getByEmail(email);

        if (cont != null) {
            throw new ContException("Exista deja un cont asociat acestui email!");
        }
    }

    private void validateUsername(String username) throws SQLException, ContException {
        Cont cont = Cont.getByUsername(username);

        if (cont != null) {
            throw new ContException("Exista deja un cont cu acest username!");
        }

        if (username.length() < 6 || username.length() > 30) {
            throw new ContException("Numele de utilizator nu poate fi mai mic de 6 caractere sau mai mare de 30 de caractere!");
        }

        if (!username.matches("([A-Z]|[a-z]|[0-9])+")) {
            throw new ContException("Numele de utilizator poate contine doar caractere alfanumerice!");
        }
    }

    private void validatePassword(String password) throws ContException {
        if (!(password.length() > 8 && password.matches("([A-Z]|[a-z]|[0-9])+"))) {
            throw new ContException("Parola trebuie trebuie sa contina doar caractere alfanumerice si trebuie!");
        }
    }

    private void validatePermission(int permission) throws ContException {
        if (permission < 1 || permission > 3) {
            throw new ContException("Permisiunea unui cont poate lua valori intre 1 si 3! (1 - student 2 - profesor, 3 - admin)");
        }
    }

    private void validateData(String email, String username, String password, int permission) throws SQLException, ContException {
        validateEmail(email);
        validateUsername(username);
        validatePassword(password);
        validatePermission(permission);
    }

    public void insert() throws SQLException {
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO conturi (email, username, password, permission) VALUES (?, ?, ?, ?)");

        preparedStatement.setString(1, email);
        preparedStatement.setString(2, username);
        preparedStatement.setString(3, password);
        preparedStatement.setInt(4, permission);

        preparedStatement.executeUpdate();

        connection.close();
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

    public long getId() {
        return id;
    }

    public int getPermission() {
        return permission;
    }
}
