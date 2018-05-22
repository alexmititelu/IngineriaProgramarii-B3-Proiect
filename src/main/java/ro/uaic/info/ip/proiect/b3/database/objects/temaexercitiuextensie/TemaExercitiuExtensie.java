package ro.uaic.info.ip.proiect.b3.database.objects.temaexercitiuextensie;

import ro.uaic.info.ip.proiect.b3.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TemaExercitiuExtensie {
    private long id;
    private long idTema;
    private int nrExercitiu;
    private String extensieAcceptata;
    private String enunt;

    private TemaExercitiuExtensie(long id, long idTema, int nrExercitiu, String extensieAcceptata, String enunt) {
        this.id = id;
        this.idTema = idTema;
        this.nrExercitiu = nrExercitiu;
        this.extensieAcceptata = extensieAcceptata;
        this.enunt = enunt;
    }

    public TemaExercitiuExtensie(long idTema, int nrExercitiu, String extensieAcceptata, String enunt) {
        this.idTema = idTema;
        this.nrExercitiu = nrExercitiu;
        this.extensieAcceptata = extensieAcceptata;
        this.enunt = enunt;
    }

    public static TemaExercitiuExtensie get(long idTema, int nrExercitiu) throws SQLException {
        TemaExercitiuExtensie temaExercitiuExtensie;
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, id_tema, nr_exercitiu, extensie_acceptata, enunt FROM tema_exercitiu_extensie WHERE id_tema = ? and nr_exercitiu = ?");

        preparedStatement.setLong(1, idTema);
        preparedStatement.setInt(2, nrExercitiu);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            temaExercitiuExtensie = new TemaExercitiuExtensie(
                    resultSet.getLong(1),
                    resultSet.getLong(2),
                    resultSet.getInt(3),
                    resultSet.getString(4),
                    resultSet.getString(5));
        } else {
            temaExercitiuExtensie = null;
        }

        connection.close();
        return temaExercitiuExtensie;
    }

    public static ArrayList<TemaExercitiuExtensie> getAllExercises(long idTema) throws SQLException {
        ArrayList<TemaExercitiuExtensie> exercitii = new ArrayList<>();
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, id_tema, nr_exercitiu, extensie_acceptata, enunt FROM tema_exercitiu_extensie " +
                        "WHERE id_tema = ?"
        );

        preparedStatement.setLong(1, idTema);
        ResultSet exercitiiToBeUpdatedForPlagiat = preparedStatement.executeQuery();

        while (exercitiiToBeUpdatedForPlagiat.next()) {
            exercitii.add(new TemaExercitiuExtensie(
                    exercitiiToBeUpdatedForPlagiat.getLong(1),
                    exercitiiToBeUpdatedForPlagiat.getLong(2),
                    exercitiiToBeUpdatedForPlagiat.getInt(3),
                    exercitiiToBeUpdatedForPlagiat.getString(4),
                    exercitiiToBeUpdatedForPlagiat.getString(5)
            ));
        }

        return exercitii;
    }

    public void insert() throws SQLException {
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO tema_exercitiu_extensie (id_tema, nr_exercitiu, extensie_acceptata, enunt) VALUES (?, ?, ?, ?)");

        preparedStatement.setLong(1, idTema);
        preparedStatement.setInt(2, nrExercitiu);
        preparedStatement.setString(3, extensieAcceptata);
        preparedStatement.setString(4, enunt);

        preparedStatement.executeUpdate();

        connection.close();
    }

    public long getId() {
        return id;
    }

    public long getIdTema() {
        return idTema;
    }

    public int getNrExercitiu() {
        return nrExercitiu;
    }

    public String getExtensieAcceptata() {
        return extensieAcceptata;
    }

    public String getEnunt() {
        return enunt;
    }
}
