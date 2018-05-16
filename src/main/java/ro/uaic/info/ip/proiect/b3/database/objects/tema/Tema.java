package ro.uaic.info.ip.proiect.b3.database.objects.tema;

import ro.uaic.info.ip.proiect.b3.database.Database;
import ro.uaic.info.ip.proiect.b3.database.objects.materie.Materie;
import ro.uaic.info.ip.proiect.b3.database.objects.tema.exceptions.TemaException;
import ro.uaic.info.ip.proiect.b3.database.objects.temaexercitiuextensie.TemaExercitiuExtensie;

import java.sql.*;
import java.util.ArrayList;

public class Tema {
    private long id;
    private long idMaterie;
    private Date deadline;
    private String enunt;
    private int nrExercitii;
    private String numeTema;
    private String[] extensiiFisiere;

    private void validateIdMaterie(long idMaterie) throws SQLException, TemaException {
        Materie materie = Materie.getById(idMaterie);

        if (materie == null) {
            throw new TemaException("Materia pentru care se incearca creare temei nu exista!");
        }
    }

    private void validateNrExercitii(int nrExercitii) throws TemaException {
        if (nrExercitii < 1) {
            throw new TemaException("Numarul de exercitii pentru tema nu poate fi mai mic decat 1!");
        }
    }

    private void validateNumeTema(String numeTema) throws TemaException {
        if (!numeTema.matches("[A-Za-z0-9 ]+")) {
            throw new TemaException("Numele temei poate contine doar caractere alfanumerice si spatiu!");
        }
    }

    private void validateData(long idMaterie, int nrExercitii, String numeTema) throws SQLException, TemaException {
        validateIdMaterie(idMaterie);
        validateNrExercitii(nrExercitii);
        validateNumeTema(numeTema);

        Tema tema = Tema.getByMaterieIdAndNumeTema(idMaterie, numeTema);
        if (tema != null) {
            throw new TemaException("Exista deja o tema cu acest nume la aceasta materie!");
        }
    }

    public Tema(long idMaterie, Date deadline, String enunt, int nrExercitii, String numeTema, String[] extensiiFisiere) throws SQLException, TemaException {
        validateData(idMaterie, nrExercitii, numeTema);

        this.idMaterie = idMaterie;
        this.deadline = deadline;
        this.enunt = enunt;
        this.nrExercitii = nrExercitii;
        this.numeTema = numeTema;
    }

    public void insert() throws SQLException {
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO teme (id_materie, deadline, enunt, nr_exercitii, nume_tema) VALUES (?, ?, ?, ?, ?)");

        preparedStatement.setLong(1, idMaterie);
        preparedStatement.setDate(2, deadline);
        preparedStatement.setString(3, enunt);
        preparedStatement.setInt(4, nrExercitii);
        preparedStatement.setString(5, numeTema);

        preparedStatement.executeUpdate();

        connection.close();

        Tema tema = Tema.getByMaterieIdAndNumeTema(idMaterie, numeTema);
        for (int i = 0; i < extensiiFisiere.length; ++i) {
            TemaExercitiuExtensie temaExercitiuExtensie = new TemaExercitiuExtensie(tema.getId(), i + 1, extensiiFisiere[i]);
            temaExercitiuExtensie.insert();
        }
    }

    private Tema(long id, long idMaterie, Date deadline, String enunt, int nrExercitii, String numeTema) {
        this.id = id;
        this.idMaterie = idMaterie;
        this.deadline = deadline;
        this.nrExercitii = nrExercitii;
        this.numeTema = numeTema;
        this.enunt = enunt;
    }

    public static Tema getById(long id) throws SQLException {
        Tema tema;
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, id_materie, deadline, enunt, nr_exercitii, nume_tema FROM teme WHERE id = ?"
        );

        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            tema = new Tema(
                    resultSet.getLong(1),
                    resultSet.getLong(2),
                    resultSet.getDate(3),
                    resultSet.getString(4),
                    resultSet.getInt(5),
                    resultSet.getString(6)
            );
        } else {
            tema = null;
        }

        connection.close();
        return tema;
    }

    public static Tema getByMaterieIdAndNumeTema(long idMaterie, String numeTema) throws SQLException {
        Tema tema;
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, id_materie, deadline, enunt, nr_exercitii, nume_tema FROM teme WHERE id_materie = ? AND nume_tema = ?"
        );

        preparedStatement.setLong(1, idMaterie);
        preparedStatement.setString(2, numeTema);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            tema = new Tema(
                    resultSet.getLong(1),
                    resultSet.getLong(2),
                    resultSet.getDate(3),
                    resultSet.getString(4),
                    resultSet.getInt(5),
                    resultSet.getString(6)
            );
        } else {
            tema = null;
        }

        connection.close();
        return tema;
    }

    public static ArrayList<Tema> getAllByIdMaterie(long idMaterie) throws SQLException {
        ArrayList<Tema> teme = new ArrayList<>();
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, id_materie, deadline, enunt, nr_exercitii, nume_tema FROM teme WHERE id_materie = ?");
        preparedStatement.setLong(1, idMaterie);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            teme.add(new Tema(
                    resultSet.getLong(1),
                    resultSet.getLong(2),
                    resultSet.getDate(3),
                    resultSet.getString(4),
                    resultSet.getInt(5),
                    resultSet.getString(6))
            );
        }

        return teme;
    }

    public long getId() {
        return id;
    }

    public long getIdMaterie() {
        return idMaterie;
    }

    public Date getDeadline() {
        return deadline;
    }

    public String getEnunt() {
        return enunt;
    }

    public int getNrExercitii() {
        return nrExercitii;
    }

    public String getNumeTema() {
        return numeTema;
    }
}
