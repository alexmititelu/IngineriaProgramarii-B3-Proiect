package ro.uaic.info.ip.proiect.b3.clientinfo;

import ro.uaic.info.ip.proiect.b3.database.Database;
import ro.uaic.info.ip.proiect.b3.database.objects.temaexercitiuextensie.TemaExercitiuExtensie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class StudentNotat extends StudentNenotat {
    private int nota;
    private Date dataUpload;

    public StudentNotat(String username, String nume, String prenume, Date dataUpload, int nota) {
        super(username, nume, prenume);
        this.nota = nota;
        this.dataUpload = dataUpload;
    }

    public static ArrayList<StudentNotat> getAllNotati(TemaExercitiuExtensie exercitiu) throws SQLException {
        ArrayList<StudentNotat> studentiNotati = new ArrayList<>();
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT c.username, s.nume, s.prenume, t.nota, t.data_incarcarii FROM studenti s JOIN conturi c ON s.email = c.email JOIN teme_incarcate t ON t.id_cont = c.id " +
                        "WHERE t.id_tema = ? AND t.nr_exercitiu = ? AND t.nota IS NOT null");
        preparedStatement.setLong(1, exercitiu.getIdTema());
        preparedStatement.setInt(2, exercitiu.getNrExercitiu());

        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            studentiNotati.add(new StudentNotat(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getDate(5),
                    rs.getInt(4)
            ));
        }

        connection.close();
        return studentiNotati;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }
}
