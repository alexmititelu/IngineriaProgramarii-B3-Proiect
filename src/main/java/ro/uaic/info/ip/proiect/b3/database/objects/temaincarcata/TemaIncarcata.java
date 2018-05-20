package ro.uaic.info.ip.proiect.b3.database.objects.temaincarcata;

import ro.uaic.info.ip.proiect.b3.database.Database;

import java.sql.*;

public class TemaIncarcata {
    private long id;
    private long idCont;
    private long idTema;
    private Date dataIncarcarii;
    private int nrExercitiu;
    private String numeFisierTema;
    private Integer nota;

    private TemaIncarcata(long id, long idCont, long idTema, Date dataIncarcarii, int nrExercitiu, String numeFisierTema, Integer nota) {
        this.id = id;
        this.idCont = idCont;
        this.idTema = idTema;
        this.dataIncarcarii = dataIncarcarii;
        this.nrExercitiu = nrExercitiu;
        this.numeFisierTema = numeFisierTema;
        this.nota = nota;
    }

    public static TemaIncarcata getById(long id) throws SQLException {
        TemaIncarcata temaIncarcata;
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, id_cont, id_tema, data_incarcarii, nr_exercitiu, nume_fisier_tema, nota FROM teme_incarcate WHERE id = ?"
        );

        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            temaIncarcata = new TemaIncarcata(
                    resultSet.getLong(1),
                    resultSet.getLong(2),
                    resultSet.getLong(3),
                    resultSet.getDate(4),
                    resultSet.getInt(5),
                    resultSet.getString(6),
                    resultSet.getInt(7)
            );
        } else {
            temaIncarcata = null;
        }

        connection.close();
        return temaIncarcata;
    }

    public long getId() {
        return id;
    }

    public long getIdCont() {
        return idCont;
    }

    public long getIdTema() {
        return idTema;
    }

    public Date getDataIncarcarii() {
        return dataIncarcarii;
    }

    public int getNrExercitiu() {
        return nrExercitiu;
    }

    public String getNumeFisierTema() {
        return numeFisierTema;
    }

    public Integer getNota() {
        return nota;
    }
}
