/*
package ro.uaic.info.ip.proiect.b3.database.objects.contconectat;

import org.junit.Before;
import org.junit.Test;
import ro.uaic.info.ip.proiect.b3.database.objects.contconectat.exceptions.ContConectatException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.Date;
import static org.junit.Assert.*;

public class ContConectatTest {

    //Constructor<ContConectat> constructor;
    private ContConectat cont;
    private ContConectat contByUser;
    private ContConectat invCont;
    private SimpleDateFormat sdf;
    private Date data;
    private Date data2;
    private String logTokenPetcu = "Fbd5vNNs6EAiiKktrZ0I3hVPM2k1gtljmAM9BdDBI5CF88o8OSYMIgGCBnH9Mnhc";

    @Before
    public void setUp() throws SQLException, ContConectatException, ParseException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {

        //constructor = (Constructor<ContConectat>) ContConectat.class.getDeclaredConstructors()[0];
        //constructor.setAccessible(true);
        //cont = constructor.newInstance(605,"vladpetcu",logTokenPetcu,data);
        cont = new ContConectat("vladpetcu");
        contByUser = ContConectat.getByUsername("vladpetcu");
    }

    */
/*@Test(expected = Exception.class)
    public void expExceptions() throws SQLException, ContConectatException {
        invCont = new ContConectat(";");
    }*//*


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
        assertEquals(605,contByUser.getId());
        assertNotEquals(null,cont.getId());
    }

    @Test
    public void getUsername() {
        assertEquals("vladpetcu",contByUser.getUsername());
        assertNotEquals(null,cont.getUsername());
    }

    @Test
    public void getToken() {
        assertEquals(logTokenPetcu, contByUser.getToken());
        assertNotEquals("sjagdas",cont.getToken());

    }

    @Test
    public void getCreationTime() throws ParseException {
        sdf = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        data = sdf.parse("2018-05-24 13:59:28");
        System.out.println(data);

        data2 = (Date) cont.getCreationTime();
        System.out.println(data2);


        Date data3 = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        data3 = df.parse("2018-05-24");
        System.out.println(data3);
        assertEquals(data,contByUser.getCreationTime());

    }
}*/
