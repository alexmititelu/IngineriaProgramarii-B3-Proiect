package ro.uaic.info.ip.proiect.b3.database.objects.tema;

import org.junit.Before;
import org.junit.Test;
import ro.uaic.info.ip.proiect.b3.database.objects.tema.exceptions.TemaException;


import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import static org.junit.Assert.*;

public class TemaTest {
    private Tema tema;
    public Tema tema2;
    public Tema tema3;
    private Date data;
    private String[] extensii;
    private String[] enunturi;
    private Calendar cal;

    private ArrayList<Tema> teme;
    private ArrayList<Tema> teme2;

    @Before
    public void setUp() throws Exception {

        cal = Calendar.getInstance();
        cal.set( cal.YEAR, 2018 );
        cal.set( cal.MONTH, cal.MAY );
        cal.set( cal.DATE, 31 );

        cal.set( cal.HOUR_OF_DAY, 0 );
        cal.set( cal.MINUTE, 0 );
        cal.set( cal.SECOND, 0 );
       // cal.set( cal.MILLISECOND, 0 );

        data = new java.sql.Date( cal.getTime().getTime() );
        extensii = new String[3];
        enunturi = new String[3];
        extensii[0] = "cpp"; extensii[1] = "cpp"; extensii[2] = "cpp";
        enunturi[0] = "asdasdasd"; enunturi[1] = "enunt-test"; enunturi[2] = "asdasdasd";

        tema = new Tema(44, data,"enunt tema 132",3,"jUnitTest",extensii,enunturi);

        teme = new ArrayList<>();
        teme2 = new ArrayList<>();
    }

    @Test(expected = TemaException.class)
    public void throwExp1() throws SQLException, TemaException {
        Tema invTema1 = new Tema(-1,data,"enunt tema 132",3,"jUnitTest",extensii,enunturi);
    }
    @Test(expected = TemaException.class)
    public void throwExp2() throws SQLException, TemaException {
        Tema invTema2 = new Tema(44,data,"enunt tema 132",-1,"jUnitTest",extensii,enunturi);
    }
    @Test(expected = TemaException.class)
    public void throwExp3() throws SQLException, TemaException {
        Tema invTema3 = new Tema(44,data,"enunt tema 132",3,";",extensii,enunturi);
    }

    @Test
    public void getAllSubjectsReadyToUpdatePlagiarism() throws SQLException {
        assertTrue("success1",teme2.isEmpty());
        teme2 = tema.getAllSubjectsReadyToUpdatePlagiarism();
        assertEquals(8, teme2.size());
    }

    @Test
    public void getById() throws SQLException {
        tema3 = tema.getById(134);
    }

    @Test
    public void getByMaterieIdAndNumeTema() throws SQLException {
        tema2 = tema.getByMaterieIdAndNumeTema(44,"Jtest");
    }

    @Test
    public void getAllByIdMaterie() throws SQLException {
        assertTrue("success",teme.isEmpty());
        teme = tema.getAllByIdMaterie(44);
        assertEquals(2,teme.size());

    }

    @Test
    public void insert() throws SQLException {
       // tema.insert();
    }

    @Test
    public void getId() {
        assertEquals(0,tema.getId());
    }

    @Test
    public void getIdMaterie() {
        assertEquals(44,tema.getIdMaterie());
    }

    @Test
    public void getDeadline() {
        assertEquals(data,tema.getDeadline());
    }

    @Test
    public void getEnunt() {
        assertEquals("enunt tema 132",tema.getEnunt());
    }

    @Test
    public void getNrExercitii() {
        assertEquals(3,tema.getNrExercitii());
        assertNotEquals(-1,tema.getNrExercitii());
    }

    @Test
    public void getNumeTema() {
        assertEquals("jUnitTest",tema.getNumeTema());
    }
}