package ro.uaic.info.ip.proiect.b3.database.objects.inscriere;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ro.uaic.info.ip.proiect.b3.database.objects.inscriere.exceptions.InscriereException;

import java.sql.SQLException;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;

public class InscriereTest {

    private Inscriere inscriere;
    private Inscriere inscriere2;
    private Inscriere inscriere3;
    private Inscriere inscriere4;

    @Before
    public void setUp() throws SQLException, InscriereException {
        inscriere = new Inscriere(20,1);
    }

    @Test
    public void insert1() throws SQLException {
        inscriere.insert();
    }
    @Test(expected = InscriereException.class)
    public void expExceptions() throws SQLException, InscriereException {
        inscriere2 = new Inscriere(20,-1);
        assertEquals("Materia pentru care se incearca inscrierea nu exista!",inscriere2.getIdMaterie());
    }

    @Test(expected = InscriereException.class)
    public void expExceptions2() throws SQLException, InscriereException {
        inscriere3 = new Inscriere(-1,1);
        assertEquals("Contul pentru care se incearca inscrierea la materie nu exista!",inscriere3.getIdCont());

    }

    @Test
    public void get() throws SQLException, InscriereException {
        assertEquals(null, inscriere.get(-1,-1));
    }

    @Test(expected = Exception.class)
    public void insert() throws SQLException, InscriereException {
        inscriere4 = Mockito.mock(Inscriere.class);
        inscriere4 = inscriere.get(20,1);
        doThrow(new SQLException())
                .when(inscriere4).insert();
        inscriere4.insert();
        catchException(inscriere4).insert();
        assertThat(caughtException(),instanceOf(SQLException.class));
    }

    @Test
    public void getIdCont() {
        assertEquals(20,inscriere.getIdCont());
        assertNotEquals(-1,inscriere.getIdCont());
    }

    @Test
    public void getIdMaterie() {
        assertEquals(1,inscriere.getIdMaterie());
        assertNotEquals(-1,inscriere.getIdMaterie());
    }
}
