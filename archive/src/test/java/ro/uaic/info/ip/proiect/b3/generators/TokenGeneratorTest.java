package ro.uaic.info.ip.proiect.b3.generators;

import org.junit.Test;

import static org.junit.Assert.*;

public class TokenGeneratorTest {
    @Test
    public void getToken() {
        assertEquals(0, TokenGenerator.getToken(-1, "conturi_conectate").length());
        assertEquals(7, TokenGenerator.getToken(7, "conturi_conectate").length());
        assertEquals(30, TokenGenerator.getToken(30, "conturi_conectate").length());
        assertEquals(90, TokenGenerator.getToken(90, "conturi_conectate").length());
        assertEquals(336, TokenGenerator.getToken(336, "conturi_conectate").length());
        assertEquals(895, TokenGenerator.getToken(895, "conturi_conectate").length());
        assertEquals(1000, TokenGenerator.getToken(1000, "conturi_conectate").length());
        assertEquals(10000, TokenGenerator.getToken(10000, "conturi_conectate").length());


        assertNotEquals(5000, TokenGenerator.getToken(100, "conturi_conectate").length());

        assertNotEquals(200, TokenGenerator.getToken(100, "conturi_conectate").length());

        assertEquals(10, TokenGenerator.getToken(10, "studenti").length());  //tabele ce nu au coloana token
        assertEquals(10, TokenGenerator.getToken(10, "profesori").length());  //tabele ce nu au coloana token
        assertEquals(10, TokenGenerator.getToken(10, "conturi").length());    //tabele ce nu au coloana token
        assertEquals(10, TokenGenerator.getToken(10, "testInexistentTable").length());


    }
}