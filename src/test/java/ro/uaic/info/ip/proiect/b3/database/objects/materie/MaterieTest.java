package ro.uaic.info.ip.proiect.b3.database.objects.materie;

import org.junit.Test;
import ro.uaic.info.ip.proiect.b3.database.objects.materie.Materie;
import ro.uaic.info.ip.proiect.b3.database.objects.materie.exceptions.MaterieException;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class MaterieTest {

    public static final String MOCK_NAME = "Algebra";
    public static final int MOCK_YEAR = 2;
    public static final int MOCK_SEMESTER = 2;

    @Test
    public void testGetSetName() throws MaterieException, SQLException {
        Materie materie = new Materie(MOCK_NAME , MOCK_YEAR , MOCK_SEMESTER , "Dificila");
        assertEquals(MOCK_NAME, materie.getTitlu());
    }

    @Test
    public void testGetSetAn() throws MaterieException, SQLException {
        Materie materie = new Materie(MOCK_NAME , MOCK_YEAR , MOCK_SEMESTER , "Dificila");
        assertEquals(MOCK_YEAR, materie.getAn());
    }

    @Test
    public void testGetSetSemester() throws MaterieException, SQLException {
        Materie materie = new Materie(MOCK_NAME , MOCK_YEAR , MOCK_SEMESTER , "Dificila");
        assertEquals(MOCK_SEMESTER, materie.getSemestru());
    }

    @Test
    public void When_GetByIdWithId1IsCalled_TitluMaterieShouldBeDificila() throws MaterieException, SQLException {
        Materie materie = new Materie(MOCK_NAME , MOCK_YEAR , MOCK_SEMESTER , "Dificila");
        Materie materieFromDB = Materie.getById(1);

        assertEquals(materie.getTitlu(), materieFromDB.getTitlu());
    }

}