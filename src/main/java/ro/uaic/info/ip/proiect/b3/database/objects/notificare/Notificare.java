package ro.uaic.info.ip.proiect.b3.database.objects.notificare;

import ro.uaic.info.ip.proiect.b3.database.Database;
import ro.uaic.info.ip.proiect.b3.database.objects.cont.Cont;
import ro.uaic.info.ip.proiect.b3.database.objects.notificare.exceptions.NotificareException;

import java.sql.*;
import java.util.ArrayList;

public class Notificare {
    private long id;
    private String continut;
    private long idCont;
    private Date creationTime;

    public Notificare(String continut, long idCont) throws SQLException, NotificareException {
        validateData(idCont);

        this.continut = continut;
        this.idCont = idCont;
    }

    private Notificare(long id, String continut, long id_cont, Date creationTime) {
        this.id = id;
        this.continut = continut;
        this.idCont = id_cont;
        this.creationTime = creationTime;
    }

    public static ArrayList<Notificare> getNotificationsForUser(long idCont) throws SQLException {
        Connection connection = Database.getInstance().getConnection();
        ArrayList<Notificare> notificari = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, continut, id_cont, creation_time FROM notificari " +
                        "WHERE (id_cont = ? OR id_cont = 0) ORDER BY creation_time desc LIMIT 10");
        preparedStatement.setLong(1, idCont);

        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            notificari.add(new Notificare(
                    rs.getLong(1),
                    rs.getString(2),
                    rs.getLong(3),
                    rs.getDate(4)
            ));
        }

        connection.close();
        return notificari;
    }

    private void validateIdCont(long idCont) throws SQLException, NotificareException {
        Cont cont = Cont.getById(idCont);
        if (idCont != 0 && cont == null) {
            throw new NotificareException("Contul pentru care se incearca adaugarea notificarii nu exista!");
        }
    }

    private void validateData(long idCont) throws SQLException, NotificareException {
        validateIdCont(idCont);
    }

    public void insert() throws SQLException {
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO notificari (continut, id_cont, creation_time) VALUES (?, ?, CURRENT_TIMESTAMP )");

        preparedStatement.setString(1, continut);
        preparedStatement.setLong(2, idCont);

        preparedStatement.executeUpdate();

        connection.close();
    }
}
