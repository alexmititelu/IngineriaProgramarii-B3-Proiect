package ro.uaic.info.ip.proiect.b3.controllers.login;

import com.google.common.hash.Hashing;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.uaic.info.ip.proiect.b3.authentication.AuthenticationManager;
import ro.uaic.info.ip.proiect.b3.database.Database;
import ro.uaic.info.ip.proiect.b3.database.objects.contconectat.ContConectat;
import ro.uaic.info.ip.proiect.b3.database.objects.contconectat.exceptions.ContConectatException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import static ro.uaic.info.ip.proiect.b3.configurations.ServerErrorMessages.INTERNAL_ERROR_MESSAGE;

/**
 * Aceasta clasa reprezinta un controller pentru metoda POST a paginii de logare.
 */

@Controller
public class LoginController {
    private final static Logger logger = Logger.getLogger(LoginController.class);

    /**
     * Metoda returneaza body-ul raspunsului HTTP pentru o cerere de login.
     * In cazul in care datele de logare sunt valide:
     * 1. Stergerea din tabela conturi_conectate a oricarui token corespunzator userului ce a facut cererea.
     * 2. Generarea unui token alfanumeric random de lungime 64 unic. (Sa nu existe deja in tabela conturi_conectate)
     * 3. Adaugarea acestui token in tabela conturi_conectate impreuna cu usernameul care a incercat autentificarea si un timestamp curent.
     * 4. Returnarea catre client a unui raspuns de forma "token={generated-token}".
     * In cazul in care datele de logare nu sunt valide:
     * 1. Setarea raspunsului drept 401Unauthorized Error cu ajutorul parametrului response.  -- response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
     * 2. Trimiterea unui raspuns negativ.
     *
     * @param username username-ul trimis de catre utilizator
     * @param password parola in plain text trimisa de catre utilizator (parola este criptata pe retea din cauza folosirii protocolului https si decriptata de catre server)
     * @param response un obiect java reprezentand raspunsul http; cu ajutorul acestuia se pot seta headere, se poate seta codul de status etc.
     * @return un mesaj in functie de rezultatul validarii
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody
    String login(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletResponse response) {
        final String hashedPassword = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();

        try {
            if (AuthenticationManager.isLoginDataValid(username, hashedPassword)) {
                Database.getInstance().updateOperation("DELETE FROM conturi_conectate WHERE username LIKE ?", username);

                ContConectat contConectat = new ContConectat(username);
                contConectat.insert();

                Cookie cookie = new Cookie("user", contConectat.getToken());
                cookie.setMaxAge(60 * 60 * 12);
                response.addCookie(cookie);

                return "valid";
            } else {
                return "Numele de utilizator sau parola sunt invalide!";
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return INTERNAL_ERROR_MESSAGE;
        } catch (ContConectatException e) {
            return e.getMessage();
        }
    }
}
