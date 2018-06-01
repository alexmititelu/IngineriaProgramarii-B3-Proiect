package ro.uaic.info.ip.proiect.b3.database.objects.inscriere;

import ro.uaic.info.ip.proiect.b3.database.Database;
import ro.uaic.info.ip.proiect.b3.database.objects.cont.Cont;
import ro.uaic.info.ip.proiect.b3.database.objects.inscriere.exceptions.InscriereException;
import ro.uaic.info.ip.proiect.b3.database.objects.materie.Materie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Inscriere {
    private long idCont;
    private long idMaterie;

    public Inscriere(long idCont, long idMaterie) throws SQLException, InscriereException {
        validateData(idCont, idMaterie);

        this.idCont = idCont;
        this.idMaterie = idMaterie;
    }

    public static Inscriere get(long idCont, long idMaterie) throws SQLException, InscriereException {
        Inscriere inscriere;
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id_cont, id_materie FROM inscrieri WHERE id_cont = ? AND id_materie = ?");

        preparedStatement.setLong(1, idCont);
        preparedStatement.setLong(2, idMaterie);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            inscriere = new Inscriere(
                    resultSet.getLong(1),
                    resultSet.getLong(2));
        } else {
            inscriere = null;
        }

        connection.close();
        return inscriere;
    }

    private void validateIdCont(long idCont) throws SQLException, InscriereException {
        Cont cont = Cont.getById(idCont);

        if (cont == null) {
            throw new InscriereException("Contul pentru care se incearca inscrierea la materie nu exista!");
        }
    }

    private void validateIdMaterie(long idMaterie) throws SQLException, InscriereException {
        Materie materie = Materie.getById(idMaterie);

        if (materie == null) {
            throw new InscriereException("Materia pentru care se incearca inscrierea nu exista!");
        }
    }

    private void validateData(long idCont, long idMaterie) throws SQLException, InscriereException {
        validateIdCont(idCont);
        validateIdMaterie(idMaterie);
    }

    public void insert() throws SQLException {
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO inscrieri (id_cont, id_materie) VALUES (?, ?)");

        preparedStatement.setLong(1, idCont);
        preparedStatement.setLong(2, idMaterie);

        preparedStatement.executeUpdate();

        connection.close();
    }

    public static int getNumberOfInscrieriForSubject(long idMaterie) throws SQLException {
        Connection connection = Database.getInstance().getConnection();
        int numberOfSubscribers = 0;

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT count(*) FROM inscrieri WHERE id_materie = ?");
        preparedStatement.setLong(1, idMaterie);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        numberOfSubscribers = resultSet.getInt(1);

        connection.close();
        return numberOfSubscribers;
    }

    public long getIdCont() {
        return idCont;
    }

    public long getIdMaterie() {
        return idMaterie;
    }
}
