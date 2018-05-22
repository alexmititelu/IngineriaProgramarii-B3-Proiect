package ro.uaic.info.ip.proiect.b3.database.objects.materie;

import ro.uaic.info.ip.proiect.b3.database.Database;
import ro.uaic.info.ip.proiect.b3.database.objects.materie.exceptions.MaterieException;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Materie implements Serializable {
    private long id;
    private String titlu;
    private int an;
    private int semestru;
    private String descriere;

    public Materie(String titlu, int an, int semestru, String descriere) throws SQLException, MaterieException {
        validateData(titlu, an, semestru);

        this.titlu = titlu;
        this.an = an;
        this.semestru = semestru;
        this.descriere = descriere;
    }

    private Materie(long id, String titlu, int an, int semestru, String descriere) {
        this.id = id;
        this.titlu = titlu;
        this.an = an;
        this.semestru = semestru;
        this.descriere = descriere;
    }

    public static Materie getById(long id) throws SQLException {
        Materie materie;
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, titlu, an, semestru, descriere FROM materii WHERE id = ?");

        preparedStatement.setLong(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            materie = new Materie(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getInt(4),
                    resultSet.getString(5));
        } else {
            materie = null;
        }

        connection.close();
        return materie;
    }

    public static Materie getByTitlu(String titlu) throws SQLException {
        Materie materie;
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, titlu, an, semestru, descriere FROM materii WHERE titlu = ?");

        preparedStatement.setString(1, titlu);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            materie = new Materie(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getInt(4),
                    resultSet.getString(5));
        } else {
            materie = null;
        }

        connection.close();
        return materie;
    }

    public static ArrayList<Materie> getAll() throws SQLException {
        ArrayList<Materie> materii = new ArrayList<>();
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, titlu, an, semestru, descriere FROM materii ORDER BY an ASC, semestru ASC");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            materii.add(new Materie(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getInt(4),
                    resultSet.getString(5))
            );
        }

        return materii;
    }

    public static ArrayList<Materie> getAllByOwnership(long profesorId) throws SQLException {
        ArrayList<Materie> materii = new ArrayList<>();
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT m.id, titlu, an, semestru, descriere FROM materii m JOIN didactic d on m.id = d.id_materie JOIN profesori p ON p.id = d.id_profesor WHERE p.id = ? ORDER BY an ASC, semestru ASC;"
        );
        preparedStatement.setLong(1, profesorId);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            materii.add(new Materie(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getInt(4),
                    resultSet.getString(5))
            );
        }

        return materii;
    }

    private void validateTitlu(String titlu) throws SQLException, MaterieException {
        Materie materie = Materie.getByTitlu(titlu);

        if (materie != null) {
            throw new MaterieException("Exista deja o materie cu acest nume!");
        }

        if (!titlu.matches("[A-Za-z0-9 ]+")) {
            throw new MaterieException("Numele materiei poate contine doar caractere alfanumerice si spatiu!");
        }
    }

    private void validateAn(int an) throws MaterieException {
        if (an < 1 || an > 3) {
            throw new MaterieException("Anul materiei poate lua valori intre 1 si 3!");
        }
    }

    private void validateSemestru(int semestru) throws MaterieException {
        if (semestru < 1 || semestru > 2) {
            throw new MaterieException("Semestrul materiei poate lua valori intre 1 si 2!");
        }
    }

    private void validateData(String titlu, int an, int semestru) throws SQLException, MaterieException {
        validateTitlu(titlu);
        validateAn(an);
        validateSemestru(semestru);
    }

    public void insert() throws SQLException {
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO materii (titlu, an, semestru, descriere) VALUES (?, ?, ?, ?)");

        preparedStatement.setString(1, titlu);
        preparedStatement.setInt(2, an);
        preparedStatement.setInt(3, semestru);
        preparedStatement.setString(4, descriere);

        preparedStatement.executeUpdate();

        connection.close();
    }

    public long getId() {
        return id;
    }

    public String getTitlu() {
        return titlu;
    }

    public int getAn() {
        return an;
    }

    public int getSemestru() {
        return semestru;
    }

    public String getDescriere() {
        return descriere;
    }
}
