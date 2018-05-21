package ro.uaic.info.ip.proiect.b3.database.objects;


import org.junit.Test;
import ro.uaic.info.ip.proiect.b3.database.objects.tema.Tema;
import ro.uaic.info.ip.proiect.b3.database.objects.tema.exceptions.TemaException;

import java.sql.Date;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class TemaTest {


    public static final long id = 1;
    public static final long idMaterie = 1;
    public static final Date deadline = null;
    public static final String enunt = "Enunt";
    public static final int nrExercitii = 3;
    public static final String numeTema = "2";

    public static final long MOCK_ID = 1;


    @Test
    public void testGetById() throws SQLException, TemaException {
        Tema tema = new Tema(idMaterie, deadline, enunt, nrExercitii, numeTema, null, null);
        assertNotEquals(null, Tema.getById(MOCK_ID));
    }

    @Test
    public void testGetByMaterieIdAndNumeTema() throws SQLException, TemaException {
        Tema tema = new Tema(idMaterie, deadline, enunt, nrExercitii, numeTema, null, null);
        assertNotEquals(null, Tema.getByMaterieIdAndNumeTema(idMaterie, numeTema));
    }

    @Test
    public void testGetAllByIdMaterie() throws SQLException, TemaException {
        Tema tema = new Tema(idMaterie, deadline, enunt, nrExercitii, numeTema, null, null);
        assertNotEquals(null, Tema.getAllByIdMaterie(idMaterie));
    }

    @Test
    public void testGetId() throws SQLException, TemaException {
        Tema tema = new Tema(idMaterie, deadline, enunt, nrExercitii, numeTema, null, null);
        assertEquals(id, tema.getId());
    }

    @Test
    public void testGetIdMaterie() throws SQLException, TemaException {
        Tema tema = new Tema(idMaterie, deadline, enunt, nrExercitii, numeTema, null, null);
        assertEquals(idMaterie, tema.getIdMaterie());
    }

    @Test
    public void testGetDeadline() throws SQLException, TemaException {
        Tema tema = new Tema(idMaterie, deadline, enunt, nrExercitii, numeTema, null, null);
        assertEquals(null, tema.getDeadline());
    }


    @Test
    public void testGetEnunt() throws SQLException, TemaException {
        Tema tema = new Tema(idMaterie, deadline, enunt, nrExercitii, numeTema, null, null);
        assertEquals(enunt, tema.getEnunt());
    }

    @Test
    public void testGetNrExercitii() throws SQLException, TemaException {
        Tema tema = new Tema(idMaterie, deadline, enunt, nrExercitii, numeTema, null, null);
        assertEquals(nrExercitii, tema.getNrExercitii());
    }

    @Test
    public void testGetNumeTema() throws SQLException, TemaException {
        Tema tema = new Tema(idMaterie, deadline, enunt, nrExercitii, numeTema, null, null);
        assertEquals(numeTema, tema.getNumeTema());
    }
}