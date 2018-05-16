package ro.uaic.info.ip.proiect.b3.database.objects.temaexercitiuextensie;

import ro.uaic.info.ip.proiect.b3.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TemaExercitiuExtensie {
    private long id;
    private long idTema;
    private int nrExercitiu;
    private String extensieAcceptata;

    public TemaExercitiuExtensie(long idTema, int nrExercitiu, String extensieAcceptata) {
        this.idTema = idTema;
        this.nrExercitiu = nrExercitiu;
        this.extensieAcceptata = extensieAcceptata;
    }

    public void insert() throws SQLException  {
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO tema_extensie_fisier (id_tema, nr_exercitiu, extensie_acceptata) VALUES (?, ?, ?)");

        preparedStatement.setLong(1, idTema);
        preparedStatement.setInt(2, nrExercitiu);
        preparedStatement.setString(3, extensieAcceptata);

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
}
