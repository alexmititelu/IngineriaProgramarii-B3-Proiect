package ro.uaic.info.ip.proiect.b3.database.objects.didactic;

import ro.uaic.info.ip.proiect.b3.database.Database;
import ro.uaic.info.ip.proiect.b3.database.objects.didactic.exceptions.DidacticException;
import ro.uaic.info.ip.proiect.b3.database.objects.materie.Materie;
import ro.uaic.info.ip.proiect.b3.database.objects.profesor.Profesor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Didactic {
    private long idMaterie;
    private long idProfesor;

    private void validateIdMaterie(long idMaterie) throws SQLException, DidacticException {
        Materie materie = Materie.getById(idMaterie);

        if (materie == null) {
            throw new DidacticException("Materia la care se incearca asignarea profesorului nu exista!");
        }
    }

    private void validateIdProfesor(long idProfesor) throws SQLException, DidacticException {
        Profesor profesor = Profesor.getById(idProfesor);

        if (profesor == null) {
            throw new DidacticException("Profesorul nu exista!");
        }
    }

    private void validateData(long idMaterie, long idProfesor) throws SQLException, DidacticException {
        validateIdMaterie(idMaterie);
        validateIdProfesor(idProfesor);
    }

    public Didactic(long idMaterie, long idProfesor) throws SQLException, DidacticException {
        validateData(idMaterie, idProfesor);

        this.idMaterie = idMaterie;
        this.idProfesor = idProfesor;
    }

    public void insert() throws SQLException {
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO didactic (id_materie, id_profesor) VALUES (?, ?)");

        preparedStatement.setLong(1, idMaterie);
        preparedStatement.setLong(2, idProfesor);

        preparedStatement.executeUpdate();

        connection.close();
    }

    public static Didactic getByIdMaterie(long idMaterie) throws SQLException, DidacticException {
        Didactic didactic;
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id_materie, id_profesor FROM didactic WHERE id_materie = ?");

        preparedStatement.setLong(1, idMaterie);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            didactic = new Didactic(
                    resultSet.getLong(1),
                    resultSet.getLong(2));
        } else {
            didactic = null;
        }

        connection.close();
        return didactic;
    }

    public static Didactic getByIdProfesor(long idProfesor) throws SQLException, DidacticException {
        Didactic didactic;
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id_materie, id_profesor FROM didactic WHERE id_profesor = ?");

        preparedStatement.setLong(1, idProfesor);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            didactic = new Didactic(
                    resultSet.getLong(1),
                    resultSet.getLong(2));
        } else {
            didactic = null;
        }

        connection.close();
        return didactic;
    }

    public long getIdMaterie() {
        return idMaterie;
    }

    public long getIdProfesor() {
        return idProfesor;
    }
}
