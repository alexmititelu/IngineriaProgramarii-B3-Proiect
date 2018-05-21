package ro.uaic.info.ip.proiect.b3.controllers.subject.homework;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ro.uaic.info.ip.proiect.b3.database.objects.materie.Materie;
import ro.uaic.info.ip.proiect.b3.database.objects.tema.Tema;
import ro.uaic.info.ip.proiect.b3.permissions.PermissionManager;

import java.sql.SQLException;

import static ro.uaic.info.ip.proiect.b3.configurations.ServerErrorMessages.INTERNAL_ERROR_MESSAGE;
import static ro.uaic.info.ip.proiect.b3.configurations.ServerErrorMessages.UNAUTHORIZED_ACCESS_MESSAGE;

@Controller
public class HomeworkPageController {
    private final static Logger logger = Logger.getLogger(HomeworkPageController.class);

    @RequestMapping(value = "/materii/{numeMaterie}/{numeTema}", method = RequestMethod.GET)
    public String getHomeworkPage(
            @CookieValue(value = "user", defaultValue = "-1") String loginToken,
            @PathVariable("numeMaterie") String numeMaterie,
            @PathVariable("numeTema") String numeTema,
            Model model) {
        try {
            if (PermissionManager.isUserLoggedIn(loginToken)) {
                Materie materie = Materie.getByTitlu(numeMaterie);
                if (materie == null) {
                    model.addAttribute("errorMessage", "Materia cu acest nume nu exista!");
                    return "error";
                }

                Tema tema = Tema.getByMaterieIdAndNumeTema(materie.getId(), numeTema);
                if (tema == null) {
                    model.addAttribute("errorMessage", "Tema cu acest nume din cadrul materiei nu exista!");
                    return "error";
                }

                model.addAttribute("numeTema", tema.getNumeTema());
                model.addAttribute("enuntTema", tema.getEnunt());
                model.addAttribute("deadlineTema", tema.getDeadline());

                if (PermissionManager.isLoggedUserProfesor(loginToken)) {
                    return "./profesor/tema";
                } else {
                    return "./student/tema";
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
}
