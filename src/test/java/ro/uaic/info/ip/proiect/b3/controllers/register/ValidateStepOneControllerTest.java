package ro.uaic.info.ip.proiect.b3.controllers.register;

import org.junit.Test;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;

import static org.junit.Assert.*;

public class ValidateStepOneControllerTest {

    //toti parametri de la nrMatricol de la functia registerController() sunt pusi intuitivi deoarece tabelele din baza de date nu sunt completate
    // urmeaza mai apoi schimbarea parametricol cu altii reali dupa ce se vor popula tabelele

    @Test
    public void registerController() {
        ValidateStepOneController validateStepOneController = new ValidateStepOneController();
        HttpServletResponse httpServletResponse = new HttpServletResponse() {
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

        assertEquals("Numarul matricol nu este valid.", validateStepOneController.registerController("testInvalidNrMatricol", httpServletResponse));
        assertEquals("A fost trimis un email de confirmare pe adresa asociata acestui numar matricol.", validateStepOneController.registerController("inexistentValidNrMatricol", httpServletResponse)); //nr matricol inexistent in tabelul din baza de date dar valid
        assertEquals("Exista deja un cont creat pentru acest numar matricol.", validateStepOneController.registerController("existentValidNrMatricol", httpServletResponse));                            //nr matricol existent in tabelul din baza de date

        assertNotEquals("Numarul matricol nu este valid.", validateStepOneController.registerController("existentValidNrMatricol", httpServletResponse));
        assertNotEquals("Numarul matricol nu este valid.", validateStepOneController.registerController("inexistentValidNrMatricol", httpServletResponse));

        assertNotEquals("A fost trimis un email de confirmare pe adresa asociata acestui numar matricol.", validateStepOneController.registerController("testInvalidNrMatricol", httpServletResponse));
        assertNotEquals("A fost trimis un email de confirmare pe adresa asociata acestui numar matricol.", validateStepOneController.registerController("existentValidNrMatricol", httpServletResponse));

        assertNotEquals("Exista deja un cont creat pentru acest numar matricol.", validateStepOneController.registerController("inexistentValidNrMatricol", httpServletResponse));
        assertNotEquals("Exista deja un cont creat pentru acest numar matricol.", validateStepOneController.registerController("testInvalidNrMatricol", httpServletResponse));




    }
}