package ro.uaic.info.ip.proiect.b3.database.objects.notificare;

import ro.uaic.info.ip.proiect.b3.database.Database;
import ro.uaic.info.ip.proiect.b3.database.objects.cont.Cont;
import ro.uaic.info.ip.proiect.b3.database.objects.materie.Materie;
import ro.uaic.info.ip.proiect.b3.database.objects.notificare.exceptions.NotificareException;

import java.sql.*;
import java.util.ArrayList;

public class Notificare {
    private long id;
    private String continut;
    private long idCont;
    private Date creationTime;
    private long idMaterie;

    private void validateIdCont(long idCont) throws SQLException, NotificareException {
        Cont cont = Cont.getById(idCont);
        if (idCont != 0 && cont == null) {
            throw new NotificareException("Contul pentru care se incearca adaugarea notificarii nu exista!");
        }
    }

    private void validateIdMaterie(long idMaterie) throws SQLException, NotificareException {
        Materie materie = Materie.getById(idMaterie);
        if (idMaterie != 0 && materie == null) {
            throw new NotificareException("Materia pentru care se incearca adaugarea notificarii nu exista!");
        }
    }

    private void validateData(long idCont, long idMaterie) throws SQLException, NotificareException {
        validateIdCont(idCont);
        validateIdMaterie(idMaterie);
    }

    public Notificare(String continut, long idCont, long idMaterie) throws SQLException, NotificareException {
        validateData(idCont, idMaterie);

        this.continut = continut;
        this.idCont = idCont;

    }

    private Notificare(long id, String continut, long id_cont, Date creationTime, long idMaterie) {
        this.id = id;
        this.continut = continut;
        this.idCont = id_cont;
        this.creationTime = creationTime;
        this.idMaterie = idMaterie;
    }

    public static ArrayList<Notificare> getNotificationsForUser(long idCont) throws SQLException {
        Connection connection = Database.getInstance().getConnection();
        ArrayList<Notificare> notificari = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, continut, id_cont, creation_time, id_materie FROM notificari " +
                        "WHERE " +
                        "(id_cont = 0 AND id_materie = 0) OR " +
                        "(id_cont = ?) OR " +
                        "(id_materie in (SELECT id_materie FROM inscrieri WHERE id_cont = ?)) " +
                        "ORDER BY creation_time desc LIMIT 10");
        preparedStatement.setLong(1, idCont);

        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            notificari.add(new Notificare(
                    rs.getLong(1),
                    rs.getString(2),
                    rs.getLong(3),
                    rs.getDate(4),
                    rs.getLong(5)
            ));
        }

        connection.close();
        return notificari;
    }

    public void insert() throws SQLException {
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO notificari (continut, id_cont, creation_time, id_materie) VALUES (?, ?, CURRENT_TIMESTAMP, ?)");

        preparedStatement.setString(1, continut);
        preparedStatement.setLong(2, idCont);
        preparedStatement.setLong(3, idMaterie);

        preparedStatement.executeUpdate();

        connection.close();
    }
}
