package ro.uaic.info.ip.proiect.b3.clientinfo;

import org.junit.Before;
import org.junit.Test;
import ro.uaic.info.ip.proiect.b3.database.objects.cont.Cont;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class InfoExercitiuStudentTest {

    private InfoExercitiuStudent infoExStudent;
    private InfoExercitiuStudent infoExStudent2;
    private InfoExercitiuStudent infoExStudExistent;
    private ArrayList<InfoExercitiuStudent> infoList;

    @Before
    public void setUp() throws Exception {
        infoExStudent = new InfoExercitiuStudent("Enunt","txt",false,"8");
        infoExStudent2 = new InfoExercitiuStudent("","",false,"");
        infoExStudExistent = new InfoExercitiuStudent("test","cpp",true,"10");
        infoList = new ArrayList<>();
    }

    @Test
    public void getInfoExercitiiStudent() throws SQLException {
        assertTrue("success",infoList.isEmpty());
        infoList = infoExStudExistent.getInfoExercitiiStudent(
                "WEEfR5PzpSUvVNld4IHOWlTZ9AFT7wd1ihPOaQwXxMizcnTeOk0RqswKWhokhERO","test44","test");
        //contul lui Codrin
        assertEquals(3,infoList.size());

    }

    @Test
    public void getEnunt() {
        assertEquals("Enunt",infoExStudent.getEnunt());
        assertNotEquals(null,infoExStudent.getEnunt());
    }

    @Test
    public void setEnunt() {
        infoExStudent2.setEnunt("Alt enunt");
        assertEquals("Alt enunt",infoExStudent2.getEnunt());
        assertNotEquals(null,infoExStudent2.getEnunt());
    }

    @Test
    public void getExtensie() {
        assertEquals("txt",infoExStudent.getExtensie());
        assertNotEquals(null,infoExStudent.getExtensie());
    }

    @Test
    public void setExtensie() {
        infoExStudent2.setExtensie("cpp");
        assertEquals("cpp",infoExStudent2.getExtensie());
        assertNotEquals(null,infoExStudent.getExtensie());
    }

    @Test
    public void isUploaded() {
        assertEquals(false,infoExStudent.isUploaded());
        assertNotEquals(true,infoExStudent.isUploaded());
    }

    @Test
    public void setUploaded() {
        infoExStudent2.setUploaded(true);
        assertEquals(true,infoExStudent2.isUploaded());
        assertNotEquals(false,infoExStudent2.isUploaded());
        infoExStudent2.setUploaded(false);
    }

    @Test
    public void getNota() {
        assertEquals("8",infoExStudent.getNota());
        assertNotEquals(null,infoExStudent.getNota());
    }

    @Test
    public void setNota() {
        infoExStudent2.setNota("10");
        assertEquals("10",infoExStudent2.getNota());
        assertNotEquals(null, infoExStudent2.getNota());

    }
}