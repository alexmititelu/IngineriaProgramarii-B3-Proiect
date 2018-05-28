package ro.uaic.info.ip.proiect.b3.controllers.subject.homework;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UpdateNotaControllerTest {

    private UpdateNotaController updateController;
    private String logTokenProf = "vPKke639Bmj5TBIpBzrwgWNBHeUs5d4EoEVEjbyPVfXTePuvK0HaERdJsDaE7pKf";


    @Before
    public void setUp() {
        updateController = new UpdateNotaController();

    }

    @Test
    public void updateNota() {
        assertEquals("Tema cu acest nume din cadrul materiei nu exista!",updateController.updateNota(logTokenProf,"test44",
                ";test","vladpetcu",1,9));

        assertEquals("Numele de utilizator pentru care se incearca editarea notei nu exista!",
                updateController.updateNota(logTokenProf,"test44", "test","v;",1,9));

        assertEquals("Nota poate lua valori doar intre 1 si 10!",updateController.updateNota(logTokenProf,"test44",
                "test","vladpetcu",1,-1));

        assertEquals("Numarul exercitiului nu este valid!",updateController.updateNota(logTokenProf,"test44",
                "test","vladpetcu",0,9));

        assertEquals("valid",updateController.updateNota(logTokenProf,"test44",
                "test","vladpetcu",1,9));

        assertEquals("You do not have the permissions to view this page at the moment!",
                updateController.updateNota("fadadas","test44",
                "test","vladpetcu",1,9));
    }
}