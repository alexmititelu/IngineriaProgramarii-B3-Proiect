package ro.uaic.info.ip.proiect.b3.database.objects.contconectat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.Bean;
import ro.uaic.info.ip.proiect.b3.database.objects.contconectat.exceptions.ContConectatException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.*;

public class ContConectatTest {

    private Constructor<ContConectat> constructor;
    private ContConectat cont;
    private ContConectat invCont;
    private Date date;

    @Before
    public void setUp() throws SQLException, ContConectatException, IllegalAccessException, InvocationTargetException, InstantiationException {
        cont = new ContConectat("vladpetcu");

    }

    /*@Test(expected = Exception.class)
    public void expExceptions() throws SQLException, ContConectatException {
        invCont = new ContConectat(";");
    }*/

    @Test
    public void insert() {
    }

    @Test
    public void getById() throws SQLException {
    }

    @Test
    public void getByUsername() {
    }

    @Test
    public void getByToken() {
    }

    @Test
    public void getId()  {
        assertEquals(0,cont.getId());
        assertNotEquals(null,cont.getId());
    }

    @Test
    public void getUsername() {
        assertEquals("vladpetcu",cont.getUsername());
        assertNotEquals(null,cont.getUsername());
    }

    @Test
    public void getToken() {
        //assertEquals("AFWYHKUkBqPvvlm3FxwMAt4KA0duSKZusV6Cn0oYNWSVokXnqMruo8TR8uzbCEAu",cont.getToken());
        assertNotEquals("sjagdas",cont.getToken());

    }

    @Test
    public void getCreationTime() throws ParseException {
       /* Date date2 = null;
        assertEquals(date2,cont.getCreationTime());*/

    }
}