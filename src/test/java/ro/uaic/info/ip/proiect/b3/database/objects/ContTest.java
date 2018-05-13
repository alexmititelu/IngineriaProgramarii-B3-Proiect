package ro.uaic.info.ip.proiect.b3.database.objects;

import org.junit.Test;

import java.lang.reflect.Constructor;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.instanceOf;

public class ContTest {

    @Test
    public void get() throws Exception {
        Constructor<Cont> constructor= (Constructor<Cont>) Cont.class.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        Cont validAcount = constructor.newInstance("Jtest", "test.Jtest@Jmail.com", "Jtest");

        Cont userCont = Cont.getByEmail("lucian.cochiorheghi@gmail.com");
        assertThat(userCont, instanceOf(Cont.class));  // inca nu exista date in tabelul Conturi


        assertEquals(null, Cont.getByEmail("testInesistentEmail@jtest.com"));
        assertEquals(null, Cont.getByEmail(""));
        assertEquals("Jtest", validAcount.getUsername());
        assertEquals("test.Jtest@Jmail.com", validAcount.getEmail());
        assertEquals("Jtest", validAcount.getPassword());

    }

    @Test
    public void getUsername() throws Exception{
        Constructor<Cont> constructor= (Constructor<Cont>) Cont.class.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        Cont cont = constructor.newInstance("Jtest", "test.Jtest@Jmail.com", "Jtest");

        assertEquals("Jtest", cont.getUsername());
        assertNotEquals("", cont.getUsername());


    }

    @Test
    public void getEmail ()throws Exception {
        Constructor<Cont> constructor= (Constructor<Cont>) Cont.class.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        Cont cont = constructor.newInstance("Jtest", "test.Jtest@Jmail.com", "Jtest");

        assertEquals("test.Jtest@Jmail.com", cont.getEmail());
        assertNotEquals("", cont.getEmail());
    }

    @Test
    public void getPassword()throws Exception {
        Constructor<Cont> constructor= (Constructor<Cont>) Cont.class.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        Cont cont = constructor.newInstance("Jtest", "test.Jtest@Jmail.com", "Jtest");

        assertEquals("Jtest", cont.getPassword());
        assertNotEquals("", cont.getPassword());
    }
}