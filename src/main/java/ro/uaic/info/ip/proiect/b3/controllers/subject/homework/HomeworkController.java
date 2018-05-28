package ro.uaic.info.ip.proiect.b3.controllers.subject.homework;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.uaic.info.ip.proiect.b3.database.objects.materie.Materie;
import ro.uaic.info.ip.proiect.b3.database.objects.notificare.Notificare;
import ro.uaic.info.ip.proiect.b3.database.objects.notificare.exceptions.NotificareException;
import ro.uaic.info.ip.proiect.b3.database.objects.tema.Tema;
import ro.uaic.info.ip.proiect.b3.database.objects.tema.exceptions.TemaException;
import ro.uaic.info.ip.proiect.b3.permissions.PermissionManager;

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
            if (PermissionManager.isUserLoggedIn(loginToken)) {
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

    @RequestMapping(value = "/materii/{numeMaterie}/createTema", method = RequestMethod.POST)
    public @ResponseBody
    String creeazaTema(@PathVariable("numeMaterie") String numeMaterie,
                       @RequestParam("numeTema") String numeTema,
                       @RequestParam("deadline") String deadline,
                       @RequestParam("enunt") String enunt,
                       @RequestParam("nrExercitii") int nrExercitii,
                       @RequestParam("extensiiFisiere[]") String[] extensiiFisiere,
                       @RequestParam("cerinteExercitii[]") String[] cerinte,
                       @CookieValue(value = "user", defaultValue = "-1") String loginToken,
                       HttpServletResponse response) {
        try {
            if (PermissionManager.isUserLoggedIn(loginToken) && PermissionManager.isLoggedUserProfesor(loginToken) && PermissionManager.isUserAllowedToModifySubject(numeMaterie, loginToken)) {
                Materie materie = Materie.getByTitlu(numeMaterie);

                if (materie != null) {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    Tema tema = new Tema(materie.getId(), new Date(formatter.parse(deadline).getTime()), enunt, nrExercitii, numeTema, extensiiFisiere, cerinte);
                    tema.insert();

                    try {
                        Notificare notificare = new Notificare(
                                String.format("[%s] A fost adaugata o noua tema!", materie.getTitlu()),
                                0,
                                materie.getId());
                        notificare.insert();
                    } catch (SQLException | NotificareException e) {
                        logger.error(e.getMessage(), e);
                    }

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
