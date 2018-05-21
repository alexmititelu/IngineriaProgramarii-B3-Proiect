package ro.uaic.info.ip.proiect.b3.controllers.login;

import com.google.common.hash.Hashing;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import ro.uaic.info.ip.proiect.b3.database.objects.cont.Cont;
import ro.uaic.info.ip.proiect.b3.database.objects.contconectat.exceptions.ContConectatException;
import ro.uaic.info.ip.proiect.b3.permissions.PermissionManager;
import java.io.IOException;
import java.io.PrintWriter;
import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static java.lang.invoke.MethodHandles.catchException;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.*;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Locale;

public class LoginControllerTest {

    private LoginController controller;
    private HttpServletResponse raspuns;
    private PermissionManager perm;
    private String hashedPassword;

    @Rule
    public ExpectedException none = ExpectedException.none();

    @Before
    public void setUp() {
        controller = new LoginController();
        raspuns = new HttpServletResponse() {
            @Override
            public void addCookie(Cookie cookie) {

            }

            @Override
            public boolean containsHeader(String s) {
                return false;
            }

            @Override
            public String encodeURL(String s) {
                return null;
            }

            @Override
            public String encodeRedirectURL(String s) {
                return null;
            }

            @Override
            public String encodeUrl(String s) {
                return null;
            }

            @Override
            public String encodeRedirectUrl(String s) {
                return null;
            }

            @Override
            public void sendError(int i, String s) throws IOException {

            }

            @Override
            public void sendError(int i) throws IOException {

            }

            @Override
            public void sendRedirect(String s) throws IOException {

            }

            @Override
            public void setDateHeader(String s, long l) {

            }

            @Override
            public void addDateHeader(String s, long l) {

            }

            @Override
            public void setHeader(String s, String s1) {

            }

            @Override
            public void addHeader(String s, String s1) {

            }

            @Override
            public void setIntHeader(String s, int i) {

            }

            @Override
            public void addIntHeader(String s, int i) {

            }

            @Override
            public void setStatus(int i) {

            }

            @Override
            public void setStatus(int i, String s) {

            }

            @Override
            public int getStatus() {
                return 0;
            }

            @Override
            public String getHeader(String s) {
                return null;
            }

            @Override
            public Collection<String> getHeaders(String s) {
                return null;
            }

            @Override
            public Collection<String> getHeaderNames() {
                return null;
            }

            @Override
            public String getCharacterEncoding() {
                return null;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public ServletOutputStream getOutputStream() throws IOException {
                return null;
            }

            @Override
            public PrintWriter getWriter() throws IOException {
                return null;
            }

            @Override
            public void setCharacterEncoding(String s) {

            }

            @Override
            public void setContentLength(int i) {

            }

            @Override
            public void setContentLengthLong(long l) {

            }

            @Override
            public void setContentType(String s) {

            }

            @Override
            public void setBufferSize(int i) {

            }

            @Override
            public int getBufferSize() {
                return 0;
            }

            @Override
            public void flushBuffer() throws IOException {

            }

            @Override
            public void resetBuffer() {

            }

            @Override
            public boolean isCommitted() {
                return false;
            }

            @Override
            public void reset() {

            }

            @Override
            public void setLocale(Locale locale) {

            }

            @Override
            public Locale getLocale() {
                return null;
            }
        };
        perm = new PermissionManager();
        hashedPassword = Hashing.sha256().hashString("proiectip123", StandardCharsets.UTF_8).toString();
    }

    @Test
    public void login() throws Exception {

        assertNotEquals("valid", controller.login("jteeest", "adfsdf", raspuns));
        assertNotEquals("Numele de utilizator sau parola sunt invalide!",
                controller.login("vladpetcu", "proiectip123", raspuns));
        assertNotEquals(null, controller.login("vladpetcu", "proiectip123", raspuns));

        assertEquals("valid", controller.login("vladpetcu", "proiectip123", raspuns));
        assertEquals("Numele de utilizator sau parola sunt invalide!", controller.login("vladpetcu", "0000000", raspuns));
        assertEquals("Numele de utilizator sau parola sunt invalide!", controller.login("vladpetcu0", "proiectip123", raspuns));

        assertTrue("success",perm.isLoginDataValid("vladpetcu",hashedPassword) == true);
        assertFalse("fail",perm.isLoginDataValid(";",hashedPassword) == true);

        //assertEquals("Numele de utilizator este invalid!",invalidCont = new ContConectat("'''"));

    }

    @Test
    public void testForCatch() throws SQLException{

        Constructor<Cont> constructor= (Constructor<Cont>) Cont.class.getDeclaredConstructors()[0];
        constructor.setAccessible(true);

        LoginController invalidController = Mockito.mock(LoginController.class);
        none.expect(Exception.class);

        PermissionManager invalidPerm = Mockito.mock(PermissionManager.class);
        Mockito.when(invalidPerm.isLoginDataValid(";",";"))
                .thenReturn(true);

        Cont blaCont = Mockito.mock(Cont.class);
        Mockito.when(blaCont.getByUsername(";")).thenReturn(null);

        invalidController.login(";",";",raspuns);

        catchException(invalidController).login(";",";",raspuns);
        assertThat(caughtException(),instanceOf(ContConectatException.class));


    }

}