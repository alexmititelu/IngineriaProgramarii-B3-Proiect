package ro.uaic.info.ip.proiect.b3.controllers.subject.homework;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;

import java.util.Collection;
import java.util.Map;

import static org.junit.Assert.*;

public class ComparaControllerTest {

    private ComparaController comparaController;
    private String eroare;
    private Model model;
    private String logTokenProf = "OFCNsb0EPavOggQ6pCFmhldNyTTnf2A8CGAgUXFUt8pn5zksto04eyXbKgSeiXil";

    @Before
    public void setUp() throws Exception {
        comparaController = new ComparaController();
        eroare = "error";
        model = new Model() {
            @Override
            public Model addAttribute(String s, Object o) {
                return null;
            }

            @Override
            public Model addAttribute(Object o) {
                return null;
            }

            @Override
            public Model addAllAttributes(Collection<?> collection) {
                return null;
            }

            @Override
            public Model addAllAttributes(Map<String, ?> map) {
                return null;
            }

            @Override
            public Model mergeAttributes(Map<String, ?> map) {
                return null;
            }

            @Override
            public boolean containsAttribute(String s) {
                return false;
            }

            @Override
            public Map<String, Object> asMap() {
                return null;
            }
        };
    }

    @Test
    public void getComparaPage() {

        assertEquals(eroare,comparaController
                .getComparaPage(logTokenProf,"nume-materie-inexistent",
                        "testTema1","vladpetcu","teodorproca",1,model));

        assertEquals(eroare,comparaController
                .getComparaPage(logTokenProf, "testTeme",
                        "nume-inexistent","vladpetcu","teodorproca",1,model));

        assertEquals(eroare,comparaController
                .getComparaPage(logTokenProf,"testTeme",
                        "testTema1","vladpetcu","teodorproca",-1,model));

        assertEquals(eroare,comparaController
                .getComparaPage(logTokenProf,"testTeme",
                        "testTema1","no;user","teodorproca",1,model));

        assertEquals(eroare,comparaController
                .getComparaPage(logTokenProf,"testTeme",
                        "testTema1","vladpetcu","no-user",1,model));

        assertEquals("compara",comparaController
                .getComparaPage(logTokenProf, "testTeme",
                        "testTema1","vladpetcu","teodorproca",1,model));

    }
}