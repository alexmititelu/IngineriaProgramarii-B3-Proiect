package ro.uaic.info.ip.proiect.b3.controllers.subject.homework;

import org.junit.Before;
import org.junit.Test;
import ro.uaic.info.ip.proiect.b3.clientinfo.InfoExercitiuProfesor;
import ro.uaic.info.ip.proiect.b3.database.objects.materie.Materie;
import ro.uaic.info.ip.proiect.b3.database.objects.tema.Tema;
import ro.uaic.info.ip.proiect.b3.database.objects.temaexercitiuextensie.TemaExercitiuExtensie;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ProfesorHomeworkInfoControllerTest {

    private ProfesorHomeworkInfoController controller;
    private String logTokenProf = "vPKke639Bmj5TBIpBzrwgWNBHeUs5d4EoEVEjbyPVfXTePuvK0HaERdJsDaE7pKf";
    //proful cu id = 18 e Codrin....trebuie tokenul lui Codrin
    private String invalidToken = ";;asdsssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";
    private ArrayList<InfoExercitiuProfesor> infoExercitiiProfesor;
    private ArrayList<TemaExercitiuExtensie> exercitii;

    @Before
    public void setUp() throws Exception {
        controller = new ProfesorHomeworkInfoController();
        infoExercitiiProfesor = new ArrayList<>();
        Materie materie = Materie.getByTitlu("test44");
        Tema tema;
        tema = Tema.getByMaterieIdAndNumeTema(materie.getId(), "test");
        exercitii = TemaExercitiuExtensie.getAllExercises(tema.getId());

    }

    @Test
    public void getProfesorInfo() {
        assertEquals(null, controller.getProfesorInfo(invalidToken,"testX","ssd"));
        assertEquals(null, controller.getProfesorInfo(logTokenProf,";;;;test","ssd"));
        assertEquals(null, controller.getProfesorInfo(logTokenProf,"testX",";;;tema44"));

        assertNotEquals(null, controller.getProfesorInfo(logTokenProf,"test44","test"));

    }

}