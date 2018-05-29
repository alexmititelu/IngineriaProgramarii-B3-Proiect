package ro.uaic.info.ip.proiect.b3.controllers.subject.homework;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.uaic.info.ip.proiect.b3.configurations.ServerErrorMessages;
import ro.uaic.info.ip.proiect.b3.database.objects.cont.Cont;
import ro.uaic.info.ip.proiect.b3.database.objects.materie.Materie;
import ro.uaic.info.ip.proiect.b3.database.objects.notitatema.NotitaTema;
import ro.uaic.info.ip.proiect.b3.database.objects.student.Student;
import ro.uaic.info.ip.proiect.b3.database.objects.tema.Tema;
import ro.uaic.info.ip.proiect.b3.permissions.PermissionManager;

import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class NotiteTemaController {
    private final static Logger logger = Logger.getLogger(NotiteTemaController.class);

    @RequestMapping(value = "/materii/{numeMaterie}/{numeTema}/adaugaNotita", method = RequestMethod.POST)
    public @ResponseBody String adaugaNotita(
            @PathVariable("numeMaterie") String numeMaterie,
            @PathVariable("numeTema") String numeTema,
            @RequestParam("continut") String continut,
            @CookieValue(value = "user", defaultValue = "-1") String loginToken) {
        try {
            if (PermissionManager.isLoggedUserStudent(loginToken)) {
                Cont cont = Cont.getByLoginToken(loginToken);
                if (cont == null) {
                    logger.error("Cont get by login token a returnat null dupa isLoggedUserStudent a returnat true!");
                    return ServerErrorMessages.INTERNAL_ERROR_MESSAGE;
                }

                Student student = Student.getByEmail(cont.getEmail());
                if (student == null) {
                    logger.error("Student get by email a returnat null dupa isLoggedUserStudent a returnat true si cont get by login token not null!");
                    return ServerErrorMessages.INTERNAL_ERROR_MESSAGE;
                }

                Materie materie = Materie.getByTitlu(numeMaterie);
                if (materie == null) {
                    return "Numele materiei este invalid!";
                }

                Tema tema = Tema.getByMaterieIdAndNumeTema(materie.getId(), numeTema);
                if (tema == null) {
                    return "Numele temei din cadrul materiei alese nu exista!";
                }

                NotitaTema notitaTema = new NotitaTema(tema.getId(), continut, false, student.getNume() + " " + student.getPrenume());
                notitaTema.insert();

                return "valid";
            } else if (PermissionManager.isUserAllowedToModifySubject(numeMaterie, loginToken)) {
                Materie materie = Materie.getByTitlu(numeMaterie);
                if (materie == null) {
                    return "Numele materiei este invalid!";
                }

                Tema tema = Tema.getByMaterieIdAndNumeTema(materie.getId(), numeTema);
                if (tema == null) {
                    return "Numele temei din cadrul materiei alese nu exista!";
                }

                NotitaTema notitaTema = new NotitaTema(tema.getId(), continut, true, "Profesor");
                notitaTema.insert();

                return "valid";
            } else {
                return ServerErrorMessages.UNAUTHORIZED_ACCESS_MESSAGE;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return ServerErrorMessages.INTERNAL_ERROR_MESSAGE;
        }
    }

    @RequestMapping(value = "/materii/{numeMaterie}/{numeTema}/getNotite", method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<NotitaTema> getNotite(
            @PathVariable("numeMaterie") String numeMaterie,
            @PathVariable("numeTema") String numeTema,
            @CookieValue(value = "user", defaultValue = "-1") String loginToken) {
        try {
            if (PermissionManager.isLoggedUserStudent(loginToken)) {
                return NotitaTema.getForStudent(numeMaterie, numeTema, loginToken);
            } else if (PermissionManager.isUserAllowedToModifySubject(numeMaterie, loginToken)) {
                return NotitaTema.getForProfesor(numeMaterie, numeTema);
            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
