package ro.uaic.info.ip.proiect.b3.clientinfo;

import org.junit.Before;
import org.junit.Test;
import ro.uaic.info.ip.proiect.b3.database.objects.temaexercitiuextensie.TemaExercitiuExtensie;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TemaPlagiataTest {

    private TemaPlagiata temaPlagiata;
    private TemaPlagiata temaPlagiata2;
    private TemaExercitiuExtensie exercitiu;
    private ArrayList<TemaPlagiata> temePlagiate;


    @Before
    public void setUp() throws Exception {
        temaPlagiata = new TemaPlagiata("teodorproca","Proca",
                "Teodor","gabrielcodrin","Cojocaru","Gabriel-Codrin",45);
        exercitiu =TemaExercitiuExtensie.get(132,1);
        temaPlagiata2 = new TemaPlagiata("","","","","","",50);
        temePlagiate = new ArrayList<>();
    }

    @Test
    public void getAllForExercise() throws SQLException {
        assertTrue("success",temePlagiate.isEmpty());
        temePlagiate = temaPlagiata.getAllForExercise(exercitiu);
        assertEquals(1,temePlagiate.size());
    }

    @Test
    public void getUsername1() {
        assertEquals("teodorproca",temaPlagiata.getUsername1());
        assertNotEquals(null,temaPlagiata.getUsername1());
    }

    @Test
    public void setUsername1() {
        temaPlagiata2.setUsername1("vladpetcu");
        assertEquals("vladpetcu",temaPlagiata2.getUsername1());
    }

    @Test
    public void getNume1() {
        assertEquals("Proca",temaPlagiata.getNume1());
        assertNotEquals(null,temaPlagiata.getNume1());
    }

    @Test
    public void setNume1() {
        temaPlagiata2.setNume1("Petcu");
        assertEquals("Petcu",temaPlagiata2.getNume1());
    }

    @Test
    public void getPrenume1() {
        assertEquals("Teodor",temaPlagiata.getPrenume1());
        assertNotEquals(null,temaPlagiata.getPrenume1());
    }

    @Test
    public void setPrenume1() {
        temaPlagiata2.setPrenume1("Lucian");
        assertEquals("Lucian",temaPlagiata2.getPrenume1());
    }

    @Test
    public void getUsername2() {
        assertEquals("gabrielcodrin",temaPlagiata.getUsername2());
        assertNotEquals(null,temaPlagiata.getUsername2());
    }

    @Test
    public void setUsername2() {
        temaPlagiata2.setUsername2("cochiorlucian");
        assertEquals("cochiorlucian",temaPlagiata2.getUsername2());
    }

    @Test
    public void getNume2() {
        assertEquals("Cojocaru",temaPlagiata.getNume2());
        assertNotEquals(null,temaPlagiata.getNume2());
    }

    @Test
    public void setNume2() {
        temaPlagiata2.setNume2("Cochior");
        assertEquals("Cochior",temaPlagiata2.getNume2());
    }

    @Test
    public void getPrenume2() {
        assertEquals("Gabriel-Codrin",temaPlagiata.getPrenume2());
        assertNotEquals(null,temaPlagiata.getPrenume2());
    }

    @Test
    public void setPrenume2() {
        temaPlagiata2.setPrenume2("Lucian");
        assertEquals("Lucian",temaPlagiata2.getPrenume2());
    }

    @Test
    public void getProcentPlagiarism() {
        assertEquals(45,temaPlagiata.getProcentPlagiarism());
        assertEquals(50,temaPlagiata2.getProcentPlagiarism());
        assertNotEquals(-1,temaPlagiata.getProcentPlagiarism());
    }

    @Test
    public void setProcentPlagiarism() {
        temaPlagiata2.setProcentPlagiarism(60);
        assertEquals(60,temaPlagiata2.getProcentPlagiarism());
    }
}