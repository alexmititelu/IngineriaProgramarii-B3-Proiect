package ro.uaic.info.ip.proiect.b3.clientinfo;

import org.junit.Before;
import org.junit.Test;
import ro.uaic.info.ip.proiect.b3.database.objects.temaexercitiuextensie.TemaExercitiuExtensie;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class StudentNenotatTest {

    private StudentNenotat studentNenotat;
    private StudentNenotat studentNenotat2;
    private ArrayList<StudentNenotat> studentiNenotati;
    private TemaExercitiuExtensie exercitiu;
    private ArrayList<StudentNenotat> studentiNenotati2;
    private TemaExercitiuExtensie exercitiu2;


    @Before
    public void setUp() throws Exception {
        studentNenotat = new StudentNenotat("vladpetcu","Petcu","Vlad-Andrei");
        studentNenotat2 = new StudentNenotat("","","");
        studentiNenotati = new ArrayList<>();
        studentiNenotati2 = new ArrayList<>();
        exercitiu = TemaExercitiuExtensie.get(132,2);
        exercitiu2 = TemaExercitiuExtensie.get(123,2);
    }

    @Test
    public void getAllNenotati() throws SQLException {
        assertTrue("success",studentiNenotati.isEmpty());
        studentiNenotati = studentNenotat.getAllNenotati(exercitiu);
        assertEquals(0,studentiNenotati.size());

        assertTrue("success",studentiNenotati2.isEmpty());
        studentiNenotati2 = studentNenotat.getAllNenotati(exercitiu2);
        assertEquals(1,studentiNenotati2.size());
    }

    @Test
    public void getUsername() {
        assertEquals("vladpetcu",studentNenotat.getUsername());
        assertNotEquals(null,studentNenotat.getUsername());
    }

    @Test
    public void setUsername() {
        studentNenotat2.setUsername("test");
        assertEquals("test",studentNenotat2.getUsername());
        assertNotEquals("",studentNenotat2.getUsername());
    }

    @Test
    public void getNume() {
        assertEquals("Petcu",studentNenotat.getNume());
        assertNotEquals(null,studentNenotat.getNume());
    }

    @Test
    public void setNume() {
        studentNenotat2.setNume("Test");
        assertEquals("Test",studentNenotat2.getNume());
        assertNotEquals("",studentNenotat2.getNume());
    }

    @Test
    public void getPrenume() {
        assertEquals("Vlad-Andrei",studentNenotat.getPrenume());
        assertNotEquals(null,studentNenotat.getPrenume());
    }

    @Test
    public void setPrenume() {
        studentNenotat2.setPrenume("Testulet");
        assertEquals("Testulet",studentNenotat2.getPrenume());
        assertNotEquals("",studentNenotat2.getPrenume());
    }
}