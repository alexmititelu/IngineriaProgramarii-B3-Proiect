package ro.uaic.info.ip.proiect.b3.authentication;


import org.junit.Test;
import static org.junit.Assert.*;

public class AuthenticationManagerTest {

    @Test
    public void multiplicationOfZeroIntegersShouldReturnZero() {
        AuthenticationManager authenticationManager = new AuthenticationManager(); // MyClass is tested

    }

    @Test
    public void testIsUserLoggedIn() {

        AuthenticationManager authenticationManager = new AuthenticationManager();

        String fakeId = "fake";

        assertEquals(false, authenticationManager.isUserLoggedIn(fakeId));
    }

    @Test
    public void testGetUsernameLoggedIn() {

        AuthenticationManager authenticationManager = new AuthenticationManager();
        String fakeId = "fake";

        assertNotEquals("random", authenticationManager.getUsernameLoggedIn(fakeId));

    }

    @Test
    public void testIsLoggedUserProfesor() {

        AuthenticationManager authenticationManager = new AuthenticationManager();
        String fakeId = "fake";

        assertNotEquals("random", authenticationManager.isLoggedUserProfesor(fakeId));
    }


    @Test
    public void testIsLoginDataValid() {

        AuthenticationManager authenticationManager = new AuthenticationManager();
        String invalidPass = "invalid_pass";
        String invalidUser = "invalid_user";

        assertEquals(false, authenticationManager.isLoginDataValid(invalidUser, invalidPass));

    }

    @Test
    public void testIsRegisterTokenValid() {
        AuthenticationManager authenticationManager = new AuthenticationManager();
        String invalidToken = "invalid";

        assertEquals(false, authenticationManager.isRegisterTokenValid(invalidToken));
    }

    @Test
    public void testGetEmailForRegisterToken() {
        AuthenticationManager authenticationManager = new AuthenticationManager();
        String invalidRegisterToken = "invalid";
        String invalidEmail = "invalid@invalid.com";

        assertNotEquals(invalidRegisterToken, authenticationManager.getEmailForRegisterToken(invalidRegisterToken));
    }
}