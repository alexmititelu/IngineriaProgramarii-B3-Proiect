package ro.uaic.info.ip.proiect.b3.clientinfo;

import ro.uaic.info.ip.proiect.b3.database.Database;
import ro.uaic.info.ip.proiect.b3.database.objects.cont.Cont;
import ro.uaic.info.ip.proiect.b3.database.objects.student.Student;
import ro.uaic.info.ip.proiect.b3.database.objects.temaexercitiuextensie.TemaExercitiuExtensie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TemaPlagiata {
    private String username1;
    private String nume1;
    private String prenume1;
    private String username2;
    private String nume2;
    private String prenume2;
    private int procentPlagiarism;

    public TemaPlagiata(String username1, String nume1, String prenume1, String username2, String nume2, String prenume2, int procentPlagiarism) {
        this.username1 = username1;
        this.nume1 = nume1;
        this.prenume1 = prenume1;
        this.username2 = username2;
        this.nume2 = nume2;
        this.prenume2 = prenume2;
        this.procentPlagiarism = procentPlagiarism;
    }

    public static ArrayList<TemaPlagiata> getAllForExercise(TemaExercitiuExtensie exercitiu) throws SQLException {
        ArrayList<TemaPlagiata> temePlagiate = new ArrayList<>();
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT username1, username2, procent_plagiat FROM plagiat WHERE id_tema = ? AND nr_exercitiu = ?");
        preparedStatement.setLong(1, exercitiu.getIdTema());
        preparedStatement.setInt(2, exercitiu.getNrExercitiu());

        ResultSet rs = preparedStatement.executeQuery();

        while(rs.next()) {
            Cont cont1 = Cont.getByUsername(rs.getString(1));
            Student student1 = Student.getByEmail(cont1.getEmail());

            Cont cont2 = Cont.getByUsername(rs.getString(2));
            Student student2 = Student.getByEmail(cont2.getEmail());

            temePlagiate.add(new TemaPlagiata(
               cont1.getUsername(),
               student1.getNume(),
               student1.getPrenume(),
               cont2.getUsername(),
               student2.getNume(),
               student2.getPrenume(),
               rs.getInt(3)
            ));
        }

        connection.close();
        return temePlagiate;
    }

    public String getUsername1() {
        return username1;
    }

    public void setUsername1(String username1) {
        this.username1 = username1;
    }

    public String getNume1() {
        return nume1;
    }

    public void setNume1(String nume1) {
        this.nume1 = nume1;
    }

    public String getPrenume1() {
        return prenume1;
    }

    public void setPrenume1(String prenume1) {
        this.prenume1 = prenume1;
    }

    public String getUsername2() {
        return username2;
    }

    public void setUsername2(String username2) {
        this.username2 = username2;
    }

    public String getNume2() {
        return nume2;
    }

    public void setNume2(String nume2) {
        this.nume2 = nume2;
    }

    public String getPrenume2() {
        return prenume2;
    }

    public void setPrenume2(String prenume2) {
        this.prenume2 = prenume2;
    }

    public int getProcentPlagiarism() {
        return procentPlagiarism;
    }

    public void setProcentPlagiarism(int procentPlagiarism) {
        this.procentPlagiarism = procentPlagiarism;
    }
}
