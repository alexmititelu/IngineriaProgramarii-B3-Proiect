package ro.uaic.info.ip.proiect.b3.clientinfo;

import org.junit.Before;
import org.junit.Test;
import ro.uaic.info.ip.proiect.b3.database.objects.temaexercitiuextensie.TemaExercitiuExtensie;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.*;

public class StudentNotatTest {

    private SimpleDateFormat sdf;
    private Date data;
    private StudentNotat studentNotat;
    private ArrayList<StudentNotat> studentiNotati;
    private ArrayList<StudentNotat> studentiNotati2;
    private TemaExercitiuExtensie exercitiu;

    @Before
    public void setUp() throws Exception {
        sdf = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        data = sdf.parse("2018-05-24 06:13:10");
        studentNotat = new StudentNotat("vladpetcu","Petcu","Vlad-Andrei",data,8);
        studentiNotati = new ArrayList<>();
        exercitiu = TemaExercitiuExtensie.get(132,1);
    }

    @Test
    public void getAllNotati() throws SQLException {
        assertTrue("success",studentiNotati.isEmpty());
        studentiNotati = studentNotat.getAllNotati(exercitiu);
        assertEquals(2,studentiNotati.size());

    }

    @Test
    public void getNota() {
        assertEquals(8,studentNotat.getNota());
        assertNotEquals(-1,studentNotat.getNota());
    }

    @Test
    public void setNota() {
        studentNotat.setNota(10);
        assertEquals(10,studentNotat.getNota());
    }
}