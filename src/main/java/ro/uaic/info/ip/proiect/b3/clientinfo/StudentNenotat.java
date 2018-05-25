package ro.uaic.info.ip.proiect.b3.clientinfo;

import ro.uaic.info.ip.proiect.b3.database.Database;
import ro.uaic.info.ip.proiect.b3.database.objects.temaexercitiuextensie.TemaExercitiuExtensie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentNenotat {
    private String username;
    private String nume;
    private String prenume;

    public StudentNenotat(String username, String nume, String prenume) {
        this.username = username;
        this.nume = nume;
        this.prenume = prenume;
    }

    public static ArrayList<StudentNenotat> getAllNenotati(TemaExercitiuExtensie exercitiu) throws SQLException {
        ArrayList<StudentNenotat> studentiNenotati = new ArrayList<>();
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT c.username, s.nume, s.prenume FROM studenti s JOIN conturi c ON s.email = c.email JOIN teme_incarcate t ON t.id_cont = c.id " +
                        "WHERE t.id_tema = ? AND t.nr_exercitiu = ? AND t.nota IS null");
        preparedStatement.setLong(1, exercitiu.getIdTema());
        preparedStatement.setInt(2, exercitiu.getNrExercitiu());

        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            studentiNenotati.add(new StudentNenotat(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3)
            ));
        }

        connection.close();
        return studentiNenotati;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }
}
