package ro.uaic.info.ip.proiect.b3.controllers.subject;

import org.springframework.web.bind.annotation.*;
import ro.uaic.info.ip.proiect.b3.authentication.AuthenticationManager;
import ro.uaic.info.ip.proiect.b3.database.Database;

import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateSubjectController {

    /**
     * Acest controller este asociat actiunii unui profesor de a crea o materie noua pentru aplicatie.
     *
     * 1. Verificam daca cel ce face requestul este logat si, respectiv, daca este profesor.
     *    In caz afirmativ, se trece la pasul 2. Altfel, se returneaza "Trebuie sa fiti autentificat in cont mai intai."
     * 2. Se face verificarea datelor introduse, si anume:
     *      - numeMaterie -> caractere alfa numerice si acest nume de materie sa nu existe deja in baza de date
     *      - an -> ia valori intregi doar din intervalul [1,3]
     *      - semestru -> ia valori intregi doar din intervalul [1,2]
     *    In caz pozitiv se trece la pasul 3, altfel, este aruncata o exceptie si prinsa ulterior, fiind returnat mesajul exceptiei.
     * 3. Se verifica daca materia cu numele respectiv exista deja in baza de date, in caz afirmativ, se arunca o exceptie, iar in cazul in care
     *    materia nu exista in baza de date aceasta va fi inserata.
     * 4. Daca totul a decurs cu succes se returneaza "valid", altfel, se returneaza mesajul de eroare prins anterior.
     *
     * @param numeMaterie reprezinta titlu materiei, de tip alfa-numeric
     * @param an repprezinta anul acesteia, {1,2,3}
     * @param semestru reprezinta semestrul in care se studiaza {1,2}
     * @param response reprezinta raspunsul trimis inapoi catre cel care a efectuat requestul
     * @return
     */

    @RequestMapping(value = "/createMaterie", method = RequestMethod.POST)
    public @ResponseBody String creeazaMaterie(@RequestParam("numeMaterie") String numeMaterie,
                                               @RequestParam("an") int an,
                                               @RequestParam("semestru") int semestru,
                                               @CookieValue(value = "user", defaultValue = "-1") String loginToken,
                                               HttpServletResponse response) {
        Connection connection = null;
        ResultSet nrMaterii = null;
        //1
        if (AuthenticationManager.isUserLoggedIn(loginToken) && AuthenticationManager.isLoggedUserProfesor(loginToken)) {
            try {

                // 2
                if( ((numeMaterie != null) && numeMaterie.matches("[A-Za-z0-9_]+")) == false) {
                    throw new Exception("numeMaterie invalid");
                }
                if(an <1 || an >3){
                    throw new Exception("an invalid");
                }
                if(semestru!=1 || semestru!=2){
                    throw new Exception("semestru invalid");
                }
                    // 3
                    connection = Database.getInstance().getConnection();

                    nrMaterii = Database.getInstance().selectQuery(connection,"SELECT * FROM materii where titlu = ?", numeMaterie);
                    if( !nrMaterii.next() )
                    {
                        Database.getInstance().updateOperation("INSERT INTO materii VALUES(?)",numeMaterie);
                    }
                    else
                    {
                        throw new SQLException("Materia cautata exista deja in baza de date");
                    }

                return "valid";
            } catch(SQLException e) {
                // faceti cu exceptii specifice ce pot aparea
                //4
                return e.getMessage();
            } catch (Exception e) {
                return e.getMessage();
            }


        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "Utilizatorul nu este logat sau nu are permisiunile necesare!";
        }
    }
}
