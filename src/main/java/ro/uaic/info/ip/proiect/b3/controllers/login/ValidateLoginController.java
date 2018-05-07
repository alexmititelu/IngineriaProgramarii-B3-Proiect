package ro.uaic.info.ip.proiect.b3.controllers.login;

import com.google.common.hash.Hashing;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.uaic.info.ip.proiect.b3.authentication.AuthenticationManager;
import ro.uaic.info.ip.proiect.b3.database.Database;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Random;

/**
 * Aceasta clasa reprezinta un controller pentru metoda POST a paginii de logare.
 */

@Controller
public class ValidateLoginController {
    /**
     * Metoda returneaza body-ul raspunsului HTTP pentru o cerere de login.
     *
     * In cazul in care datele de logare sunt valide:
     * 1. Stergerea din tabela conturi_conectate a oricarui token corespunzator userului ce a facut cererea.
     * 2. Generarea unui token alfanumeric random de lungime 64 unic. (Sa nu existe deja in tabela conturi_conectate)
     * 3. Adaugarea acestui token in tabela conturi_conectate impreuna cu usernameul care a incercat autentificarea si un timestamp curent.
     * 4. Returnarea catre client a unui raspuns de forma "token={generated-token}".
     *
     * In cazul in care datele de logare nu sunt valide:
     * 1. Setarea raspunsului drept 401Unauthorized Error cu ajutorul parametrului response.  -- response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
     * 2. Trimiterea unui raspuns negativ.
     *
     * @param username username-ul trimis de catre utilizator
     * @param password parola in plain text trimisa de catre utilizator (parola este criptata pe retea din cauza folosirii protocolului https si decriptata de catre server)
     * @param response un obiect java reprezentand raspunsul http; cu ajutorul acestuia se pot seta headere, se poate seta codul de status etc.
     * @return un mesaj in functie de rezultatul validarii
     */
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public @ResponseBody String login(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletResponse response) {

        final String hashedPassword = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
        final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Connection con = null;

        if (AuthenticationManager.isLoginDataValid(username, hashedPassword))
        {
            try {
                con = Database.getInstance().getConnection();
                String query = "DELETE FROM conturi_conectate WHERE username LIKE ?";
                PreparedStatement preparedStatement = con.prepareStatement(query);

                preparedStatement.setString(1, username);
                preparedStatement.executeQuery();

                Random r = new Random();
                for (int i = 0; i < 64; i++)
                {
                    sb.append(alphabet.charAt(r.nextInt(alphabet.length()))); //adaugam un caracter random din stringul alphabet
                }

                query = "INSERT INTO conturi_conectate VALUES(?,?,CURRENT_TIMESTAMP )";
                preparedStatement = con.prepareStatement(query);
                preparedStatement.setString(1, sb.toString());
                preparedStatement.setString(2, username);

            } catch (SQLException e) {
                System.err.println("CAN'T GET DATABASE CONNECTION IN VALIDATING LOGIN CONTROLLER");
                e.printStackTrace();
            }
            finally {
                try {
                    if (con != null) {
                        con.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


            return "token=" + sb.toString();
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "error=username/password-are-not-valid";
        }
    }
}
