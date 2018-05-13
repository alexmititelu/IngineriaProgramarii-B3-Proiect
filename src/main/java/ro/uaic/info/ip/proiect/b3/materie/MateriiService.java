package ro.uaic.info.ip.proiect.b3.materie;

import ro.uaic.info.ip.proiect.b3.database.Database;
import ro.uaic.info.ip.proiect.b3.database.objects.Materie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MateriiService {
    public static List<Materie> getAll() {
        Connection dbConnection = null;
        List<Materie> materieList = new ArrayList<>();
        try {
            dbConnection = Database.getInstance().getConnection();
            String query = "SELECT titlu,semestru,an from materii";
            Statement queryStatement = dbConnection.prepareStatement(query);
            ResultSet resultSet = ((PreparedStatement) queryStatement).executeQuery();

            while(resultSet.next()) {
                Materie materie = new Materie();
                materie.setNume(resultSet.getString(1));
                materie.setSemestru(resultSet.getInt(2));
                materie.setAn(resultSet.getInt(3));
                materieList.add(materie);
            }
            return materieList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
