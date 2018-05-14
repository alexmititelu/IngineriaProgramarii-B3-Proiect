package ro.uaic.info.ip.proiect.b3.materii;

import ro.uaic.info.ip.proiect.b3.controllers.subject.Objects.Tema;
import ro.uaic.info.ip.proiect.b3.database.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TemeService {
    public static List<Tema> getAll(String numeMaterie) throws SQLException {
        Connection dbConnection = null;
        ResultSet resultSet = null;
        List<Tema> listaTeme = new ArrayList<>();

        dbConnection = Database.getInstance().getConnection();

        resultSet = Database.getInstance().selectQuery(dbConnection, "SELECT titlu,deadline,enunt,nr_exercitii,extensie_fisier,nume_tema FROM materii JOIN teme on materii.id = teme.id_materie where materii.titlu = ?", numeMaterie);

        while (resultSet.next()) {
            Tema tema = new Tema();

            tema.setTitluMaterie(resultSet.getString(1));
            tema.setDeadline(resultSet.getString(2));
            tema.setEnunt(resultSet.getString(3));
            tema.setNrExercitii(resultSet.getInt(4));
            tema.setExtensieFisier(resultSet.getString(5));
            tema.setNumeTema(resultSet.getString(6));

            listaTeme.add(tema);
        }
        return listaTeme;

    }
}
