package ro.uaic.info.ip.proiect.b3.database.objects.plagiat;

import ro.uaic.info.ip.proiect.b3.database.Database;

import java.sql.*;

public class Plagiat {
    private long id;
    private String username1;
    private String username2;
    private long idTema;
    private int nrExercitiu;
    private int procentPlagiat;

    public Plagiat(String username1, String username2, long idTema, int nrExercitiu, int procentPlagiat) {
        this.id = id;
        this.username1 = username1;
        this.username2 = username2;
        this.idTema = idTema;
        this.nrExercitiu = nrExercitiu;
        this.procentPlagiat = procentPlagiat;
    }

    public long insert() throws SQLException {
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO plagiat (username1, username2, id_tema, nr_exercitiu, procent_plagiat) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setString(1, username1);
        preparedStatement.setString(2, username2);
        preparedStatement.setLong(3, idTema);
        preparedStatement.setInt(4, nrExercitiu);
        preparedStatement.setInt(5, procentPlagiat);

        preparedStatement.executeUpdate();

        long id = -1;
        ResultSet rs = preparedStatement.getGeneratedKeys();
        if (rs.next()) {
            id = rs.getLong(1);
        }

        connection.close();
        return id;
    }

    public long getId() {
        return id;
    }

    public String getUsername1() {
        return username1;
    }

    public String getUsername2() {
        return username2;
    }

    public long getIdTema() {
        return idTema;
    }

    public int getNrExercitiu() {
        return nrExercitiu;
    }

    public int getProcentPlagiat() {
        return procentPlagiat;
    }
}
