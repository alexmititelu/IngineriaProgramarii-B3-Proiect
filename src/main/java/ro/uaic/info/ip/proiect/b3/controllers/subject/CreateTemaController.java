package ro.uaic.info.ip.proiect.b3.controllers.subject;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ro.uaic.info.ip.proiect.b3.authentication.AuthenticationManager;
import ro.uaic.info.ip.proiect.b3.database.Database;

import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateTemaController {

    /**
     * Acest controller este asociat actiunii unui profesor de a crea o tema in cadrul unei materii.
     * <p>
     * 1. Verificam daca cel ce face requestul este logat si, respectiv, daca este profesor.
     * In caz afirmativ, se trece la pasul 2. Altfel, se returneaza "Trebuie sa fiti autentificat in cont mai intai."
     * 2. Se face verificarea datelor introduse, si anume:
     * - numeMaterie -> caractere alfa numerice si acest nume de materie sa existe deja in baza de date
     * - numeTema -> caractere alfa numerice si acest nume de tema as nu fie in baza de date
     * - deadline -> format de zi valid
     * - nrExercitii -> nr natural
     * - extensieFisierAcceptat -> ce tip de extensie este acceptat pentru fisierele uploadate
     * In caz pozitiv se trece la pasul 3, altfel, este aruncata o exceptie si prinsa ulterior, fiind returnat mesajul exceptiei.
     * 3. Se verifica daca materia cu numele respectiv exista deja in baza de date, in caz afirmativ, se arunca o exceptie, iar in cazul in care
     * materia nu exista in baza de date aceasta va fi inserata.
     * 4. Daca totul a decurs cu succes se returneaza "valid", altfel, se returneaza mesajul de eroare prins anterior.
     *
     * @param numeMaterie
     * @param numeTema
     * @param deadline
     * @param enunt
     * @param nrExercitii
     * @param extensieFisierAcceptat
     * @param response
     * @return
     */

    @RequestMapping(value = "/materii/{numeMaterie}/createTema", method = RequestMethod.POST)
    public @ResponseBody
    String creeazaTema(@PathVariable("numeMaterie") String numeMaterie,
                       @RequestParam String numeTema,
                       @RequestParam("deadline") @DateTimeFormat(pattern = "dd/mm/yyyy") Date deadline,
                       @RequestParam String enunt,
                       @RequestParam int nrExercitii,
                       @RequestParam String extensieFisierAcceptat,
                       @CookieValue(value = "user", defaultValue = "-1") String loginToken,
                       HttpServletResponse response) {

        if (AuthenticationManager.isUserLoggedIn(loginToken) && AuthenticationManager.isLoggedUserProfesor(loginToken)) {
            Connection connection = null;
            ResultSet materie = null;
            try {
                //  1
                if (numeMaterie == null || numeTema == null || deadline == null || enunt == null || extensieFisierAcceptat == null)
                    throw new Exception("Parametru (null) invalid");
                if (!(numeMaterie.matches("[A-Za-z0-9_]+"))) {
                    throw new Exception("numeMaterie invalid");
                }
                connection = Database.getInstance().getConnection();
                materie = Database.getInstance().selectQuery(connection, "SELECT id FROM materii where titlu = ?", numeMaterie);
                BigInteger idMaterie;
                if (!materie.next()) {
                    throw new SQLException("Materia nu exista in baza de date!");
                } else {
                    idMaterie = (BigInteger) materie.getObject(1);
                    if (!numeTema.matches("[A-Za-z0-9_]+")) {
                        throw new Exception("numeTeme invalid!");
                    }
                    ResultSet checkNumeTema = Database.getInstance().selectQuery(connection, "SELECT * FROM teme where nume_tema = ?", numeTema);
                    if (!checkNumeTema.next()) {
                        if (!extensieFisierAcceptat.matches("[A-Za-z]+")) {
                            throw new Exception("Extensie invalida!");
                        }
                        Database.getInstance().updateOperation("INSERT INTO TEME VALUES(?,?,?,?,?,?)",idMaterie.toString(),deadline.toString(),enunt, Integer.toString(nrExercitii),extensieFisierAcceptat,numeTema);
                    } else {
                        throw new SQLException("Numele temei exista deja in baza de date");
                    }

                }


                return "valid";
            } catch (SQLException e) {
                return ("SQL Exception thrown: " + e);
            } catch (Exception e) {
                return ("Exception thrown: " + e);
            }

        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "Utilizatorul nu este logat sau nu are permisiunile necesare!";
        }
    }

}
