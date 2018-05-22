package ro.uaic.info.ip.proiect.b3.database.objects.hotzone;

import ro.uaic.info.ip.proiect.b3.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HotZone {
    private long idPlagiat;
    private int student1[];
    private int student2[];
    private int procent;

    public HotZone() {
    }

    public HotZone(int[] student1, int[] student2, int procent) {
        this.student1 = student1;
        this.student2 = student2;
        this.procent = procent;
    }

    public HotZone(long idPlagiat, int[] student1, int[] student2, int procent) {
        this.idPlagiat = idPlagiat;
        this.student1 = student1;
        this.student2 = student2;
        this.procent = procent;
    }

    public void insert() throws SQLException {
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO hot_zone (id_plagiat, start_row_user1, end_row_user1, start_row_user2, end_row_user2, procent_plagiat) VALUES (?, ?, ?, ?, ?, ?)");

        preparedStatement.setLong(1, idPlagiat);
        preparedStatement.setInt(2, student1[0]);
        preparedStatement.setInt(3, student1[1]);
        preparedStatement.setInt(4, student2[0]);
        preparedStatement.setInt(5, student2[1]);
        preparedStatement.setInt(6, procent);

        preparedStatement.executeUpdate();

        connection.close();
    }

    public int[] getStudent1() {
        return student1;
    }

    public void setStudent1(int[] student1) {
        this.student1 = student1;
    }

    public int[] getStudent2() {
        return student2;
    }

    public void setStudent2(int[] student2) {
        this.student2 = student2;
    }

    public int getProcent() {
        return procent;
    }

    public void setProcent(int procent) {
        this.procent = procent;
    }
}
