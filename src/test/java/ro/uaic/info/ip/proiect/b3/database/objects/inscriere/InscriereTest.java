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

    @Before
    public void setUp() throws SQLException, InscriereException {
        inscriere = new Inscriere(20,33);
    }

    @Test(expected = InscriereException.class)
    public void expExceptions() throws SQLException, InscriereException {
        inscriere3 = new Inscriere(-1,-1);
    }

    @Test(expected = Exception.class)
    public void insert() throws SQLException, InscriereException {
        inscriere3 = Mockito.mock(Inscriere.class);
        inscriere3 = inscriere.getByIdCont(-1);
        doThrow(new SQLException())
                .when(inscriere3).insert();
        inscriere3.insert();
        catchException(inscriere3).insert();
        assertThat(caughtException(),instanceOf(SQLException.class));
    }

    @Test
    public void getByIdCont() throws SQLException, InscriereException {
        inscriere2 = inscriere.getByIdCont(inscriere.getIdCont());
        inscriere3 = inscriere.getByIdCont(-1);
        assertEquals(null,inscriere3);
        assertEquals(inscriere2.getIdCont(),inscriere.getIdCont());
        assertEquals(inscriere2.getIdMaterie(),inscriere.getIdMaterie());
    }

    @Test
    public void getByIdMaterie() throws SQLException, InscriereException {
        inscriere2 = inscriere.getByIdMaterie(inscriere.getIdMaterie());
        inscriere3 = inscriere.getByIdMaterie(-1);
        assertEquals(null,inscriere3);
        assertEquals(inscriere2.getIdCont(),inscriere.getIdCont());
        assertEquals(inscriere2.getIdMaterie(),inscriere.getIdMaterie());
    }

    @Test
    public void getIdCont() {
        assertEquals(20,inscriere.getIdCont());
        assertNotEquals(-1,inscriere.getIdCont());
    }

    @Test
    public void getIdMaterie() {
        assertEquals(33,inscriere.getIdMaterie());
        assertNotEquals(-1,inscriere.getIdMaterie());
    }
}