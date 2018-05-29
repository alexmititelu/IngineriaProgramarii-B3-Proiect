package ro.uaic.info.ip.proiect.b3.database.objects.notitatema;

import ro.uaic.info.ip.proiect.b3.database.Database;
import ro.uaic.info.ip.proiect.b3.database.objects.cont.Cont;
import ro.uaic.info.ip.proiect.b3.database.objects.materie.Materie;
import ro.uaic.info.ip.proiect.b3.database.objects.student.Student;
import ro.uaic.info.ip.proiect.b3.database.objects.tema.Tema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NotitaTema {
    private long id;
    private long idTema;
    private String continut;
    private boolean isProfesor;
    private String nume;

    private NotitaTema(long id, long idTema, String continut, boolean isProfesor, String nume) {
        this.id = id;
        this.idTema = idTema;
        this.continut = continut;
        this.isProfesor = isProfesor;
        this.nume = nume;
    }

    public NotitaTema(long idTema, String continut, boolean isProfesor, String nume) {
        this.idTema = idTema;
        this.continut = continut;
        this.isProfesor = isProfesor;
        this.nume = nume;
    }

    public void insert() throws SQLException {
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO notite_tema (id_tema, continut, is_profesor, nume) VALUES (?, ?, ?, ?)");

        preparedStatement.setLong(1, idTema);
        preparedStatement.setString(2, continut);
        preparedStatement.setBoolean(3, isProfesor);
        preparedStatement.setString(4, nume);

        preparedStatement.executeUpdate();

        connection.close();
    }

    public static ArrayList<NotitaTema> getForStudent(String numeMaterie, String numeTema, String loginToken) throws SQLException {
        Cont cont = Cont.getByLoginToken(loginToken);
        if (cont == null) return null;

        Student student = Student.getByEmail(cont.getEmail());
        if (student == null) return null;

        Materie materie = Materie.getByTitlu(numeMaterie);
        if (materie == null) return null;

        Tema tema = Tema.getByMaterieIdAndNumeTema(materie.getId(), numeTema);
        if (tema == null) return null;

        Connection connection = Database.getInstance().getConnection();
        ArrayList<NotitaTema> notite = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, id_tema, continut, is_profesor, nume FROM notite_tema WHERE (is_profesor = 1 OR nume like ?) AND id_tema = ? ");
        preparedStatement.setString(1, student.getNume() + " " + student.getPrenume());
        preparedStatement.setLong(2, tema.getId());

        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            notite.add(new NotitaTema(
                    rs.getLong(1),
                    rs.getLong(2),
                    rs.getString(3),
                    rs.getBoolean(4),
                    rs.getString(5)
            ));
        }

        connection.close();
        return notite;
    }

    public static ArrayList<NotitaTema> getForProfesor(String numeMaterie, String numeTema) throws SQLException {
        Materie materie = Materie.getByTitlu(numeMaterie);
        if (materie == null) return null;

        Tema tema = Tema.getByMaterieIdAndNumeTema(materie.getId(), numeTema);
        if (tema == null) return null;

        Connection connection = Database.getInstance().getConnection();
        ArrayList<NotitaTema> notite = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, id_tema, continut, is_profesor, nume FROM notite_tema WHERE id_tema = ?");
        preparedStatement.setLong(1, tema.getId());

        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            notite.add(new NotitaTema(
                    rs.getLong(1),
                    rs.getLong(2),
                    rs.getString(3),
                    rs.getBoolean(4),
                    rs.getString(5)
            ));
        }

        connection.close();
        return notite;
    }

    public long getId() {
        return id;
    }

    public long getIdTema() {
        return idTema;
    }

    public String getContinut() {
        return continut;
    }

    public boolean isProfesor() {
        return isProfesor;
    }

    public String getNume() {
        return nume;
    }
}
