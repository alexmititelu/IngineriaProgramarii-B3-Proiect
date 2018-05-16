package ro.uaic.info.ip.proiect.b3.controllers.subject.homework;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.uaic.info.ip.proiect.b3.authentication.AuthenticationManager;
import ro.uaic.info.ip.proiect.b3.database.objects.materie.Materie;
import ro.uaic.info.ip.proiect.b3.database.objects.tema.Tema;
import ro.uaic.info.ip.proiect.b3.database.objects.tema.exceptions.TemaException;

import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static ro.uaic.info.ip.proiect.b3.configurations.ServerErrorMessages.INTERNAL_ERROR_MESSAGE;

@Controller
public class HomeworkController {
    private final static Logger logger = Logger.getLogger(HomeworkController.class);


    @RequestMapping(value = "/materii/{numeMaterie}/teme_json", method = RequestMethod.GET)
    public @ResponseBody
    List<Tema> getTeme(
            @CookieValue(value = "user", defaultValue = "-1") String loginToken,
            Model model,
            @PathVariable String numeMaterie,
            HttpServletResponse response) {
        try {
            if (AuthenticationManager.isUserLoggedIn(loginToken)) {
                Materie materie = Materie.getByTitlu(numeMaterie);

                if (materie != null) {
                    return Tema.getAllByIdMaterie(materie.getId());
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);

            model.addAttribute("errorMessage", INTERNAL_ERROR_MESSAGE);
            return null;
        }
    }

    /**
     * Acest controller este asociat actiunii unui profesor de a crea o tema in cadrul unei materii.
     * <p>
     * 1. Verificam daca cel ce face requestul este logat si, respectiv, daca este profesor.
     * In caz afirmativ, se trece la pasul 2. Altfel, se returneaza "Trebuie sa fiti autentificat in cont mai intai."
     * 2. Se face verificarea datelor introduse, si anume:
     * - numeMaterie -> caractere alfa numerice si acest nume de materii sa existe deja in baza de date
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
     * @param extensiiFisiere
     * @param response
     * @return
     */
    @RequestMapping(value = "/materii/{numeMaterie}/createTema", method = RequestMethod.POST)
    public @ResponseBody
    String creeazaTema(@PathVariable("numeMaterie") String numeMaterie,
                       @RequestParam("numeTema") String numeTema,
                       @RequestParam("deadline") String deadline,
                       @RequestParam("enunt") String enunt,
                       @RequestParam("nrExercitii") int nrExercitii,
                       @RequestParam("extensiifisiere") String[] extensiiFisiere,
                       @RequestParam("cerinteExercitii") String[] cerinte,
                       @CookieValue(value = "user", defaultValue = "-1") String loginToken,
                       HttpServletResponse response) {
        try {
            if (AuthenticationManager.isUserLoggedIn(loginToken) && !AuthenticationManager.isLoggedUserProfesor(loginToken)) {
                Materie materie = Materie.getByTitlu(numeMaterie);

                if (materie != null) {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    Tema tema = new Tema(materie.getId(), new Date(formatter.parse(deadline).getTime()), enunt, nrExercitii, numeTema, extensiiFisiere);
                    tema.insert();

                    return "valid";
                } else {
                    return "Materia cu acest titlu nu exista!";
                }
            } else {
                return "Utilizatorul nu este logat sau nu are permisiunile necesare!";
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return INTERNAL_ERROR_MESSAGE;
        } catch (ParseException e) {
            return "Formatul date este incorect! Formatul acceptat este dd/mm/yyyy!";
        } catch (TemaException e) {
            return e.getMessage();
        }
    }
}
