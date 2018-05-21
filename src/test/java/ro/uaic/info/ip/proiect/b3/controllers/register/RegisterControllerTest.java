/*
package ro.uaic.info.ip.proiect.b3.controllers.register;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;
import ro.uaic.info.ip.proiect.b3.database.objects.cont.Cont;

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

import static org.junit.Assert.*;

public class RegisterControllerTest {
    private RegisterController controller;
    private HttpServletResponse raspuns;
    private Model model;
    private String validMatricol;


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
        validMatricol = "321sl345";
    }

    */
/* private Student validStudent = Student.getByNrMatricol(validMatricol);
    private Student invalidStudent = Student.getByNrMatricol("000jt000");*//*


    @Test
    public void registerPageStepTwo() throws SQLException {

        // register_links -> user: junittest2 pw:junittest123 (trebuie sa fie conectat)

        String loginTokenLaCerere = "kDTeFBN3uB5hqAqS7cVftNRbaQiwuGyhwwiUS6M7kQS0SWd8n4gMOlTlzdibkuy8";
        String registerToken2 = "pHKq0opN2NKRTbqUrtBfmCKTygDhtycRbT8vJjwenJRV7h1QeRdXnMZzMp2uYFEd";
        assertEquals("redirect:/",controller.registerPageStepTwo(loginTokenLaCerere, registerToken2,model,raspuns));

        assertEquals("error",controller.registerPageStepTwo(";",";",model,raspuns));

        assertNotEquals("error", controller.registerPageStepTwo
                ("IUQSHondm5VD4NdrmyaFqKAUVXSiyzdR7GjGwgwUy2gCHwWozt3lajI3gKqWuqYA", registerToken2,model,raspuns));

        assertNotEquals("register-step-two", controller.registerPageStepTwo(loginTokenLaCerere,registerToken2,model,raspuns));

        assertEquals("register-step-two",controller.registerPageStepTwo
                ("fail",registerToken2,model,raspuns));


        assertEquals("error",controller.registerPageStepTwo("","-1",model,raspuns));
        assertNotEquals("redirect:/",controller.registerPageStepTwo("","-1",model,raspuns));
        assertNotEquals("register-step-two",controller.registerPageStepTwo("","-1",model,raspuns));
    }

    @Test
    public void registerController() throws IllegalAccessException, InvocationTargetException, InstantiationException {

        assertEquals("Numarul matricol nu este valid!",controller.registerController("",raspuns));
        assertEquals("Numarul matricol nu este valid!",controller.registerController("000jt000",raspuns));

        assertEquals("valid",controller.registerController(validMatricol,raspuns));
        assertNotEquals(null,controller.registerController(validMatricol,raspuns));
        assertNotEquals("Exista deja un cont creat pentru acest numar matricol.",
                controller.registerController("31090104SL161265",raspuns));

    }

    @Test
    public void register() {

        assertEquals("Tokenul nu este valid!",controller
                .register("petcuvlad92@yahoo.com", "vladpetcu", "proiectip123",
                        "adsasd;;;;adasd",raspuns));
        assertEquals("Exista deja un cont asociat acestui email!",controller.register("junit.test2@gmail.com",
                "junittest2", "junittest123",
                "pHKq0opN2NKRTbqUrtBfmCKTygDhtycRbT8vJjwenJRV7h1QeRdXnMZzMp2uYFEd",raspuns));
        assertEquals("Exista deja un cont asociat acestui email!",controller.register("petcuvlad92@yahoo.com",
                "vladpetcu", "proiectip123",
                "pHKq0opN2NKRTbqUrtBfmCKTygDhtycRbT8vJjwenJRV7h1QeRdXnMZzMp2uYFEd",raspuns));
        assertNotEquals("valid",controller.register("junit.test1@gmail.com",
                "junittest1", "junittest123",
                "KIbiQanepugQTANvBf6GFxpBRjCPcJQfW9atJwEfSwSEZ64awjH7NrLBQpKM8m4w",raspuns));

        //Merge doar la prima rulare, registerToken e sters dupa -> matricol nou, cerere noua, token nou
        */
/*String regTok = "pHKq0opN2NKRTbqUrtBfmCKTygDhtycRbT8vJjwenJRV7h1QeRdXnMZzMp2uYFEd";
        assertEquals("valid",controller.register("asd@asd.com","asdasdd","aasdsd123",
                regTok,raspuns));*//*



    }
}*/
