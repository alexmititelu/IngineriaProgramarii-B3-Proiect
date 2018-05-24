package ro.uaic.info.ip.proiect.b3.controllers.register;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;
import ro.uaic.info.ip.proiect.b3.database.objects.cont.Cont;
import ro.uaic.info.ip.proiect.b3.database.objects.student.Student;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import static com.googlecode.catchexception.apis.BDDCatchException.caughtException;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;

public class RegisterControllerTest {
    private RegisterController controller;
    private RegisterController invalidController;
    private HttpServletResponse raspuns;
    private Model model;
/*  Trebuie sa existe in baza de date:
    vladpetcu - logat pe aplicatie + registerToken in bd
    jtest1 - sa aiba cont creeat + registerToken in bd
    jtest2 - doar registerToken in bd
    validMatricol - doar in tabela studenti */
    private String validMatricol = "321sl350";
    private String regTokenPetcu = "pHKq0opN2NKRTbqUrtBfmCKTygDhtycRbT8vJjwenJRV7h1QeRdXnMZzMp2uYFEd";//exista cont
    private String regTokenTest1 = "KIbiQanepugQTANvBf6GFxpBRjCPcJQfW9atJwEfSwSEZ64awjH7NrLBQpKM8m4w";//nu exista cont
    private String regTokenTest2 = "asdfbsadfjsabjdfjdjddCKTygDhtycRbT8vJjwenJRV7h1QeRdXnMZzMp2uYFEd";
    private String matricolPetcu = "31090104SL161265";
    private String logRegisterPetcu = "cnJ1GP1f92l1S8dvlOzDk9hwULhkMXERsMFDvJt71PZDRvc0zdymOMgNhoU1Drh0";//luat din conturi_conectate dupa logare

    @Before
    public void setUp(){
        controller = new RegisterController();
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
    public void registerPageStepTwo() throws SQLException {

        assertEquals("redirect:/",controller.registerPageStepTwo(logRegisterPetcu, regTokenPetcu,model,raspuns));
        assertNotEquals("redirect:/",controller.registerPageStepTwo(";","",model,raspuns));


        assertEquals("register-step-two",controller.registerPageStepTwo
                ("badlogintoken",regTokenTest1,model,raspuns));
        assertNotEquals("register-step-two",controller.registerPageStepTwo(logRegisterPetcu,
                regTokenPetcu,model,raspuns));

        assertEquals("error",controller.registerPageStepTwo(";",";",model,raspuns));
        assertNotEquals("error",controller.registerPageStepTwo(logRegisterPetcu, regTokenPetcu,model,raspuns));

    }

    @Test(expected = Exception.class)
    public void testForCatch() {
        invalidController = Mockito.mock(RegisterController.class);
        doThrow(new SQLException())
                .when(invalidController)
                        .registerPageStepTwo(";;",";;",model,raspuns);
        invalidController.registerPageStepTwo(";;",";;",model,raspuns);
        assertThat(caughtException(),instanceOf(SQLException.class));
        assertEquals("error",invalidController.registerPageStepTwo(";;",";;",model,raspuns));

    }

    @Test
    public void registerController() throws IllegalAccessException, InvocationTargetException, InstantiationException {

        assertEquals("Numarul matricol nu este valid!",controller.registerController("",raspuns));
        assertEquals("Numarul matricol nu este valid!",controller.registerController("000jt000",raspuns));

        assertEquals("valid",controller.registerController(validMatricol,raspuns));
        assertNotEquals(null,controller.registerController(validMatricol,raspuns));
        assertNotEquals("Exista deja un cont creat pentru acest numar matricol.",
                controller.registerController(matricolPetcu,raspuns));

    }

    @Test
    public void register() {

        assertEquals("Tokenul nu este valid!",controller
                .register("petcuvlad92@yahoo.com", "vladpetcu", "proiectip123",
                        "adsasd;;;;adasd",raspuns));

        assertEquals("Exista deja un cont asociat acestui email!",controller.register("petcuvlad92@yahoo.com",
                "vladpetcu", "proiectip123", regTokenPetcu,raspuns));

        assertNotEquals("valid",controller.register("junit.test1@gmail.com",
                "junittest1", "junittest123", regTokenTest1,raspuns));

        assertEquals("valid",controller.register("junit.test2@gamil.com","junittest2","junittest123",
                regTokenTest2,raspuns));

    }
}
