package ro.uaic.info.ip.proiect.b3.database.objects.comentariuprofesor;

import ro.uaic.info.ip.proiect.b3.database.Database;
import ro.uaic.info.ip.proiect.b3.database.objects.comentariuprofesor.exceptions.ComentariuProfesorException;
import ro.uaic.info.ip.proiect.b3.database.objects.tema.Tema;
import ro.uaic.info.ip.proiect.b3.database.objects.temaincarcata.TemaIncarcata;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ComentariuProfesor {
    private long id;
    private long idTemaIncarcata;
    private int numarExercitiu;
    private int startRow;
    private int endRow;
    private String comentariu;

    private void validateIdTemaIncarcata(long idTemaIncarcata) throws SQLException, ComentariuProfesorException {
        TemaIncarcata temaIncarcata = TemaIncarcata.getById(idTemaIncarcata);

        if (temaIncarcata == null) {
            throw new ComentariuProfesorException("Tema la care se incearca adaugarea comentariului nu exista!");
        }
    }

    private void validateNumarExercitiu(long idTemaIncarcata, int numarExercitiu) throws SQLException, ComentariuProfesorException {
        TemaIncarcata temaIncarcata = TemaIncarcata.getById(idTemaIncarcata);
        Tema tema = Tema.getById(temaIncarcata.getIdTema());

        if (numarExercitiu > tema.getNrExercitii() || numarExercitiu < 1) {
            throw new ComentariuProfesorException("Exercitiul cu numarul " + numarExercitiu + " nu exista pentru aceasta tema!");
        }
    }

    private void validateStartRow(int startRow) throws ComentariuProfesorException{
        if (startRow < 1) {
            throw new ComentariuProfesorException("Randul de start al comentariului nu poate fi mai mic decat 1!");
        }
    }

    private void validateEndRow(int endRow) throws ComentariuProfesorException {
        if (endRow < 1) {
            throw new ComentariuProfesorException("Randul de sfarsit al comentariului nu poate fi mai mic decat 1!");
        }
    }

    private void validateData(long idTemaIncarcata, int numarExercitiu, int startRow, int endRow) throws SQLException, ComentariuProfesorException {
        validateIdTemaIncarcata(idTemaIncarcata);
        validateNumarExercitiu(idTemaIncarcata, numarExercitiu);
        validateStartRow(startRow);
        validateEndRow(endRow);

        if (startRow > endRow) {
            throw new ComentariuProfesorException("Randul de start al comentariului nu poate fi mai mare decat randul de sfarsit al acestuia!");
        }
    }

    public ComentariuProfesor(long idTemaIncarcata, int numarExercitiu, int startRow, int endRow, String comentariu) throws SQLException, ComentariuProfesorException {
        validateData(idTemaIncarcata, numarExercitiu, startRow, endRow);

        this.idTemaIncarcata = idTemaIncarcata;
        this.numarExercitiu = numarExercitiu;
        this.startRow = startRow;
        this.endRow = endRow;
        this.comentariu = comentariu;
    }

    public void insert() throws SQLException, ComentariuProfesorException {
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO comentarii_profesori (id_tema_incarcata, nr_exercitiu, start_row, end_row, comentariu) VALUES (?, ?, ?, ?, ?)");

        preparedStatement.setLong(1, idTemaIncarcata);
        preparedStatement.setInt(2, numarExercitiu);
        preparedStatement.setInt(3, startRow);
        preparedStatement.setInt(4, endRow);
        preparedStatement.setString(5, comentariu);

        preparedStatement.executeUpdate();

        connection.close();
    }

    private ComentariuProfesor(long id, long idTemaIncarcata, int numarExercitiu, int startRow, int endRow, String comentariu) {
        this.id = id;
        this.idTemaIncarcata = idTemaIncarcata;
        this.numarExercitiu = numarExercitiu;
        this.startRow = startRow;
        this.endRow = endRow;
        this.comentariu = comentariu;
    }

    public static ComentariuProfesor getById(long id) throws SQLException {
        ComentariuProfesor comentariuProfesor;
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, id_tema_incarcata, nr_exercitiu, start_row, end_row, comentariu FROM comentarii_profesori WHERE id = ?");

        preparedStatement.setLong(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            comentariuProfesor = new ComentariuProfesor(
                    resultSet.getLong(1),
                    resultSet.getLong(2),
                    resultSet.getInt(3),
                    resultSet.getInt(4),
                    resultSet.getInt(5),
                    resultSet.getString(6));
        } else {
            comentariuProfesor = null;
        }

        connection.close();
        return comentariuProfesor;
    }

    public static ArrayList<ComentariuProfesor> getByIdTemaIncarcataAndNrExercitiu(long idTemaIncarcata, int numarExercitiu) throws SQLException {
        Connection connection = Database.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, id_tema_incarcata, nr_exercitiu, start_row, end_row, comentariu FROM comentarii_profesori WHERE id_tema_incarcata = ? AND nr_exercitiu = ?");

        preparedStatement.setLong(1, idTemaIncarcata);
        preparedStatement.setInt(2, numarExercitiu);

        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<ComentariuProfesor> comentariiExercitiu = new ArrayList<>();

        while (resultSet.next()) {
            comentariiExercitiu.add(new ComentariuProfesor(
                    resultSet.getLong(1),
                    resultSet.getLong(2),
                    resultSet.getInt(3),
                    resultSet.getInt(4),
                    resultSet.getInt(5),
                    resultSet.getString(6)));
        }

        connection.close();
        return comentariiExercitiu;
    }

    public long getId() {
        return id;
    }

    public long getIdTemaIncarcata() {
        return idTemaIncarcata;
    }

    public int getNumarExercitiu() {
        return numarExercitiu;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public String getComentariu() {
        return comentariu;
    }
}
