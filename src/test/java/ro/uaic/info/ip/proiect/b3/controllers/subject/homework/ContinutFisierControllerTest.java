package ro.uaic.info.ip.proiect.b3.controllers.subject.homework;

import org.junit.Before;
import org.junit.Test;
import ro.uaic.info.ip.proiect.b3.clientinfo.LinieContinutFisier;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ContinutFisierControllerTest {

    private ContinutFisierController continutFisierController;
    private String logTokenProf = "jmA9KxVmkXLO5RO4VA1eLokwzclGNogYlxC1Irh6nap4tHbYeOwkZ50evOsXyhk5";
    private String logTokenStud = "cnJ1GP1f92l1S8dvlOzDk9hwULhkMXERsMFDvJt71PZDRvc0zdymOMgNhoU1Drh0";
    private String logTokenInvalid = ";bla";
    private ArrayList<LinieContinutFisier>  continut;

    @Before
    public void setUp() throws Exception {
        continutFisierController = new ContinutFisierController();
        continut = new ArrayList<>();
    }

    @Test
    public void getContinutFisier() {

        assertEquals(null,continutFisierController
                .getContinutFisier(logTokenInvalid,"testTeme","testTema1",1,"profesor"));

        assertEquals(null,continutFisierController
                .getContinutFisier(logTokenProf,";","testTema1",1,"profesor"));

        assertEquals(null,continutFisierController
                .getContinutFisier(logTokenProf,"testTeme",";",1,"profesor"));

        assertEquals(null,continutFisierController
                .getContinutFisier(logTokenStud,";testTeme","testTema1",1,"vladpetcu"));

        assertTrue("success",continut.isEmpty());

        continut = continutFisierController
                .getContinutFisier(logTokenStud,"test44","test",1,"vladpetcu");

        assertFalse("success",continut.isEmpty());
        assertEquals(273,continut.size());



    }
}