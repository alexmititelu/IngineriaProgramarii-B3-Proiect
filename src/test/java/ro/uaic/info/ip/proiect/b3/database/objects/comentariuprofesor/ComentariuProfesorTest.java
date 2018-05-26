package ro.uaic.info.ip.proiect.b3.database.objects.comentariuprofesor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ro.uaic.info.ip.proiect.b3.database.objects.comentariuprofesor.exceptions.ComentariuProfesorException;
import ro.uaic.info.ip.proiect.b3.database.objects.contconectat.exceptions.ContConectatException;

import java.lang.reflect.Constructor;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;

public class ComentariuProfesorTest {

    /*
        private Constructor<ComentariuProfesor> constructor= (Constructor<ComentariuProfesor>) ComentariuProfesor.class.getDeclaredConstructors()[0];
       // private Constructor<ComentariuProfesor> constructor2= (Constructor<ComentariuProfesor>) ComentariuProfesor.class.getDeclaredConstructors()[0];
        private ComentariuProfesor comProf;
       // private ComentariuProfesor invalidCom;
    */
    private ComentariuProfesor comProf;
    private ComentariuProfesor invalidCom;
    private ComentariuProfesor comInsert;
    private ComentariuProfesor comDelete;
    private ArrayList<ComentariuProfesor> comentariiExercitiu;


    @Before
    public void setUp() throws Exception {
        /*constructor.setAccessible(true);
        //constructor2.setAccessible(true);
        comProf = constructor.newInstance(9,3,1,1,101,"Nota 10!");
        //invalidCom = constructor2.newInstance(9,3,1,-1,300,"Rau");*/
        comProf = new ComentariuProfesor(6,1,1,101,"Nota 10!");
        comentariiExercitiu = new ArrayList<>();
    }

    @Test(expected = Exception.class)
    public void expExceptions() throws SQLException, ComentariuProfesorException {
        invalidCom = new ComentariuProfesor(-1,-1,102,-1,"Rau");

    }

    @Test
    public void insert() throws SQLException, ComentariuProfesorException {
        comInsert = ComentariuProfesor.getById(4);
        comInsert.insert();
    }

    @Test//(expected = ComentariuProfesorException.class)
    public void getById() throws SQLException, ComentariuProfesorException {
/*
        invalidCom = Mockito.mock(ComentariuProfesor.class);
        doThrow(new ComentariuProfesorException("Randul de start al comentariului nu poate fi mai mic decat 1!"))
                .when(invalidCom).insert();

        invalidCom.insert();
        catchException(invalidCom).insert();
        assertThat(caughtException(),instanceOf(ComentariuProfesorException.class));*/

         ComentariuProfesor com2 = ComentariuProfesor.getById(1);

    }

    @Test
    public void getByIdTemaIncarcataAndNrExercitiu() throws SQLException {
        assertTrue("succes",comentariiExercitiu.isEmpty());
        comentariiExercitiu = comProf.getByIdTemaIncarcataAndNrExercitiu(comProf.getIdTemaIncarcata(),comProf.getNumarExercitiu());

        assertEquals(1,comentariiExercitiu.size());
    }

    @Test
    public void getId() {
        assertEquals(0,comProf.getId());
    }

    @Test
    public void getIdTemaIncarcata() {
        assertEquals(6,comProf.getIdTemaIncarcata());
        assertNotEquals(-1,comProf.getIdTemaIncarcata());
    }

    @Test
    public void delete() throws SQLException {

        comDelete.delete(39,1,4,9);
    }

    @Test
    public void getNumarExercitiu() {
        assertEquals(1,comProf.getNumarExercitiu());
        assertNotEquals(2,comProf.getNumarExercitiu());
    }

    @Test
    public void getStartRow() {
        assertEquals(1,comProf.getStartRow());
        assertNotEquals(0,comProf.getStartRow());
    }

    @Test
    public void getEndRow() {
        assertEquals(101,comProf.getEndRow());
        assertNotEquals(102,comProf.getEndRow());
    }

    @Test
    public void getComentariu() {
        assertEquals("Nota 10!",comProf.getComentariu());
        assertNotEquals("M-am razgandit, dezastru!",comProf.getComentariu());
    }
}