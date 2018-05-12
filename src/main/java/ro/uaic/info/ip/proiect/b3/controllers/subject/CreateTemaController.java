package ro.uaic.info.ip.proiect.b3.controllers.subject;

import org.springframework.web.bind.annotation.*;
import ro.uaic.info.ip.proiect.b3.authentication.AuthenticationManager;

import javax.servlet.http.HttpServletResponse;

public class CreateTemaController {

    /**
     * Acest controller este asociat actiunii unui profesor de a crea o tema in cadrul unei materii.
     *
     * 1. Verificam daca cel ce face requestul este logat si, respectiv, daca este profesor.
     *    In caz afirmativ, se trece la pasul 2. Altfel, se returneaza "Trebuie sa fiti autentificat in cont mai intai."
     * 2. Se face verificarea datelor introduse, si anume:
     *      - numeMaterie -> caractere alfa numerice si acest nume de materie sa nu existe deja in baza de date
     *      - numeTema -> caractere alfa numerice si acest nume de tema as nu fie in baza de date
     *      - deadline -> format de zi valid
     *      - nrExercitii -> nr natural
     *      - format -> sql etc
     *    In caz pozitiv se trece la pasul 3, altfel, este aruncata o exceptie si prinsa ulterior, fiind returnat mesajul exceptiei.
     * 3. Se verifica daca materia cu numele respectiv exista deja in baza de date, in caz afirmativ, se arunca o exceptie, iar in cazul in care
     *    materia nu exista in baza de date aceasta va fi inserata.
     * 4. Daca totul a decurs cu succes se returneaza "valid", altfel, se returneaza mesajul de eroare prins anterior.
     *
     * @param numeMaterie
     * @param numeTema
     * @param deadline
     * @param enunt
     * @param nrExercitii
     * @param format
     * @param response
     * @return
     */

    @RequestMapping(value = "/materii/{numeMaterie}/createTema", method = RequestMethod.POST)
    public @ResponseBody
    String creeazaTema (@PathVariable String numeMaterie,
                        @RequestParam String numeTema,
                        @RequestParam String deadline,
                        @RequestParam String enunt,
                        @RequestParam int nrExercitii,
                        @RequestParam String format,
                        @CookieValue(value = "user", defaultValue = "-1") String loginToken,
                        HttpServletResponse response) {

        if (AuthenticationManager.isUserLoggedIn(loginToken) && AuthenticationManager.isLoggedUserProfesor(loginToken)) {

            try {
                //  1
                // 2
                // 3

                return "valid";
            } catch(Exception e) {
                // faceti cu exceptii specifice ce pot aparea
                //4
                return "mesaj de eroare";
            }

        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "invalid";
        }
    }
}
