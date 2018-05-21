package ro.uaic.info.ip.proiect.b3.database.objects;

import org.junit.Before;
import ro.uaic.info.ip.proiect.b3.database.Database;
import ro.uaic.info.ip.proiect.b3.database.objects.profesor.Profesor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.Test;
import ro.uaic.info.ip.proiect.b3.database.objects.profesor.exceptions.ProfesorException;

import static org.junit.Assert.*;

public class ProfesorTest {

    public static final String NAME = "Hitler";
    public static final String MIDDLE_NAME = "Adolf";
    public static final String EMAIL = "hitleradolfhate@fabrica.nr";
    public Profesor profesor;
    public static final int MOCK_ID = 1;


    @Before
    public void setUp() throws SQLException, ProfesorException {
        profesor = new Profesor(NAME, MIDDLE_NAME, EMAIL);
    }

    @Test
    public void testInsert() {
        try {
            profesor.insert();
            assertEquals(false, true);
        } catch (SQLException e) {
            assertEquals(false, true);
        }
    }

    @Test
    public void testGetById() throws SQLException {
        assertNotEquals(null, Profesor.getById(MOCK_ID));
    }

    @Test
    public void testGetByEmail() throws SQLException {
        assertNotEquals(null, Profesor.getByEmail(EMAIL));
    }

    @Test
    public void testGetId() {
        assertNotEquals(-1, profesor.getId());
    }

    @Test
    public void testGetNume() {
        assertEquals(NAME, profesor.getNume());
    }

    @Test
    public void testGetPrenume() {
        assertEquals(MIDDLE_NAME, profesor.getPrenume());
    }
}