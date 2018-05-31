package ro.uaic.info.ip.proiect.b3.database.objects.solutiepublica;

import ro.uaic.info.ip.proiect.b3.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SolutiePublica {
    private long idTema;
    private int nrExercitiu;
    private long idTemaIncarcata;

    public SolutiePublica(long idTema, int nrExercitiu, long idTemaIncarcata) {
        this.idTema = idTema;
        this.nrExercitiu = nrExercitiu;
        this.idTemaIncarcata = idTemaIncarcata;
    }

    public void insert() throws SQLException {
        SolutiePublica solutiePublica = null;
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO solutii_publice (id_tema, nr_exercitiu, id_tema_incarcata) VALUES (?, ?, ?)");

        preparedStatement.setLong(1, idTema);
        preparedStatement.setInt(2, nrExercitiu);
        preparedStatement.setLong(3, idTemaIncarcata);

        preparedStatement.executeUpdate();

        connection.close();
    }

    public static SolutiePublica get(long idTema, int nrExercitiu, long idTemaIncarcata) throws SQLException {
        SolutiePublica solutiePublica = null;
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id_tema, nr_exercitiu, id_tema_incarcata FROM solutii_publice WHERE id_tema = ? AND nr_exercitiu = ? AND id_tema_incarcata = ?");

        preparedStatement.setLong(1, idTema);
        preparedStatement.setInt(2, nrExercitiu);
        preparedStatement.setLong(3, idTemaIncarcata);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            solutiePublica = new SolutiePublica(
                    idTema,
                    nrExercitiu,
                    idTemaIncarcata);
        }

        connection.close();
        return solutiePublica;
    }

    public static ArrayList<SolutiePublica> getAllForHomeworkAndExercise(long idTema, int nrExercitiu) throws SQLException {
        ArrayList<SolutiePublica> solutiiPublice = new ArrayList<>();
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id_tema, nr_exercitiu, id_tema_incarcata FROM solutii_publice " +
                        "WHERE id_tema = ? AND nr_exercitiu = ?"
        );
        preparedStatement.setLong(1, idTema);
        preparedStatement.setInt(2, nrExercitiu);

        ResultSet solutiiPubliceRs = preparedStatement.executeQuery();

        while (solutiiPubliceRs.next()) {
            solutiiPublice.add(new SolutiePublica(
                    solutiiPubliceRs.getLong(1),
                    solutiiPubliceRs.getInt(2),
                    solutiiPubliceRs.getLong(3)
            ));
        }

        connection.close();
        return solutiiPublice;
    }

    public long getIdTema() {
        return idTema;
    }

    public int getNrExercitiu() {
        return nrExercitiu;
    }

    public long getIdTemaIncarcata() {
        return idTemaIncarcata;
    }
}
