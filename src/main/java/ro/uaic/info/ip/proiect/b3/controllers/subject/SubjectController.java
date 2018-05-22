package ro.uaic.info.ip.proiect.b3.controllers.subject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.uaic.info.ip.proiect.b3.configurations.Permissions;
import ro.uaic.info.ip.proiect.b3.database.objects.cont.Cont;
import ro.uaic.info.ip.proiect.b3.database.objects.didactic.Didactic;
import ro.uaic.info.ip.proiect.b3.database.objects.didactic.exceptions.DidacticException;
import ro.uaic.info.ip.proiect.b3.database.objects.materie.Materie;
import ro.uaic.info.ip.proiect.b3.database.objects.materie.exceptions.MaterieException;
import ro.uaic.info.ip.proiect.b3.database.objects.profesor.Profesor;
import ro.uaic.info.ip.proiect.b3.permissions.PermissionManager;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

import static ro.uaic.info.ip.proiect.b3.configurations.ServerErrorMessages.*;

@Controller
public class SubjectController {
    private final static Logger logger = Logger.getLogger(SubjectController.class);

    /**
     * 1. Verificam daca userul e logat, in caz afirmativ -> 2, altfel, return
     * "invalid" 2. Cream o lista cu toate materiile ( un json cu toate obiectele de
     * tip materie ce contin (nume, semestru, an) 3. Returnam valid, in cazul in
     * care totul a decurs cu succes, altfel, returnam un mesaj specific de eroare
     */

    @RequestMapping(value = "/materii", method = RequestMethod.GET)
    public String getMaterii(@CookieValue(value = "user", defaultValue = "-1") String loginToken, Model model) {
        try {
            if (PermissionManager.isUserLoggedIn(loginToken)) {
                Cont cont = Cont.getByLoginToken(loginToken);
                if (cont.getPermission() > Permissions.STUDENT) {
                    return "./profesor/materii";
                } else {
                    return "./student/materii";
                }
            }

            model.addAttribute("errorMessage", UNAUTHORIZED_ACCESS_MESSAGE);
            return "error";
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);

            model.addAttribute("errorMessage", INTERNAL_ERROR_MESSAGE);
            return "error";
        }
    }

    @RequestMapping(value = "/materii_json", method = RequestMethod.GET)
    public @ResponseBody
    List<Materie> listeazaMaterii(@CookieValue(value = "user", defaultValue = "-1") String loginToken) {
        try {
            if (PermissionManager.isUserLoggedIn(loginToken)) {
                Cont cont = Cont.getByLoginToken(loginToken);

                if (cont.getPermission() > Permissions.STUDENT) {
                    Profesor profesor = Profesor.getByEmail(cont.getEmail());
                    if (profesor != null) {
                        return Materie.getAllByOwnership(profesor.getId());
                    } else {
                        return null;
                    }
                } else {
                    return Materie.getAll();
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @RequestMapping(value = "/materii/{numeMaterie}", method = RequestMethod.GET)
    public String getMaterie(
            @CookieValue(value = "user", defaultValue = "-1") String loginToken,
            Model model,
            @PathVariable String numeMaterie,
            HttpServletResponse response) {

        try {
            if (PermissionManager.isUserLoggedIn(loginToken)) {
                Materie materie = Materie.getByTitlu(numeMaterie);

                if (materie != null) {
                    model.addAttribute("materieAn", materie.getAn());
                    model.addAttribute("materieSemestru", materie.getSemestru());
                    model.addAttribute("materieNume", materie.getTitlu());
                    model.addAttribute("materieDescriere", materie.getDescriere());

                    if (PermissionManager.isLoggedUserProfesor(loginToken)) {
                        return "./profesor/materie";
                    } else {
                        return "./student/materie";
                    }
                } else {
                    model.addAttribute("errorMessage", PAGE_NOT_FOUND_MESSAGE);
                    return "error";
                }

            } else {
                model.addAttribute("errorMessage", UNAUTHORIZED_ACCESS_MESSAGE);
                return "error";
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);

            model.addAttribute("errorMessage", INTERNAL_ERROR_MESSAGE);
            return "error";
        }
    }

    /**
     * Acest controller este asociat actiunii unui profesor de a crea o materii noua pentru aplicatie.
     * <p>
     * 1. Verificam daca cel ce face requestul este logat si, respectiv, daca este profesor.
     * In caz afirmativ, se trece la pasul 2. Altfel, se returneaza "Trebuie sa fiti autentificat in cont mai intai."
     * 2. Se face verificarea datelor introduse, si anume:
     * - numeMaterie -> caractere alfa numerice si acest nume de materii sa nu existe deja in baza de date
     * - an -> ia valori intregi doar din intervalul [1,3]
     * - semestru -> ia valori intregi doar din intervalul [1,2]
     * In caz pozitiv se trece la pasul 3, altfel, este aruncata o exceptie si prinsa ulterior, fiind returnat mesajul exceptiei.
     * 3. Se verifica daca materia cu numele respectiv exista deja in baza de date, in caz afirmativ, se arunca o exceptie, iar in cazul in care
     * materia nu exista in baza de date aceasta va fi inserata.
     * 4. Daca totul a decurs cu succes se returneaza "valid", altfel, se returneaza mesajul de eroare prins anterior.
     *
     * @param numeMaterie reprezinta titlu materiei, de tip alfa-numeric
     * @param an          repprezinta anul acesteia, {1,2,3}
     * @param semestru    reprezinta semestrul in care se studiaza {1,2}
     * @param response    reprezinta raspunsul trimis inapoi catre cel care a efectuat requestul
     * @return
     */

    @RequestMapping(value = "/createMaterie", method = RequestMethod.POST)
    public @ResponseBody
    String creeazaMaterie(@RequestParam("numeMaterie") String numeMaterie,
                          @RequestParam("an") int an,
                          @RequestParam("semestru") int semestru,
                          @RequestParam("descriere") String descriere,
                          @CookieValue(value = "user", defaultValue = "-1") String loginToken,
                          HttpServletResponse response) {
        try {
            if (PermissionManager.isUserLoggedIn(loginToken) && PermissionManager.isLoggedUserProfesor(loginToken)) {
                Materie materie = new Materie(numeMaterie, an, semestru, descriere);
                materie.insert();

                Cont cont = Cont.getByLoginToken(loginToken);
                Profesor profesor = Profesor.getByEmail(cont.getEmail());
                materie = Materie.getByTitlu(numeMaterie);

                Didactic didactic = new Didactic(materie.getId(), profesor.getId());
                didactic.insert();

                return "valid";
            } else {
                return "Utilizatorul nu este logat sau nu are permisiunile necesare!";
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return INTERNAL_ERROR_MESSAGE;
        } catch (MaterieException | DidacticException e) {
            return e.getMessage();
        }
    }

}
