package ro.uaic.info.ip.proiect.b3.model;

import junit.framework.JUnit4TestAdapter;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestForClass {

    String mesaj = "Dan";
    ClassForTest classForTest = new ClassForTest(mesaj);
    public static junit.framework.Test suite(){
        return new JUnit4TestAdapter(TestForClass.class);
    }
    @Test
    public void getMesaj() {
        assertEquals(mesaj,classForTest.getMesaj());

    }

    @Test
    public void setMesaj() {
        String nume = "Dragos";
        classForTest.setMesaj("Dragos");
        assertEquals(nume,classForTest.getMesaj());
    }

    @Test
    public void assignY()
    {
        /**
         * Acesta este un positive testing
         */
        classForTest.setY(0);
        classForTest.assignY(3);
        Integer number = 3;
        assertEquals(classForTest.getY(),number);
        /**
         * Acesta este un negative testing
         */
        classForTest.setY(0);
        classForTest.assignY(2);
        number = 0;
        assertEquals(classForTest.getY(),number);

    }

}
