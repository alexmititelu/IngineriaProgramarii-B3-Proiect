package ro.uaic.info.ip.proiect.b3.controllers.subject.homework;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.uaic.info.ip.proiect.b3.database.objects.comentariuprofesor.ComentariuProfesor;
import ro.uaic.info.ip.proiect.b3.database.objects.comentariuprofesor.exceptions.ComentariuProfesorException;
import ro.uaic.info.ip.proiect.b3.database.objects.cont.Cont;
import ro.uaic.info.ip.proiect.b3.database.objects.materie.Materie;
import ro.uaic.info.ip.proiect.b3.database.objects.notificare.Notificare;
import ro.uaic.info.ip.proiect.b3.database.objects.notificare.exceptions.NotificareException;
import ro.uaic.info.ip.proiect.b3.database.objects.tema.Tema;
import ro.uaic.info.ip.proiect.b3.database.objects.temaincarcata.TemaIncarcata;
import ro.uaic.info.ip.proiect.b3.permissions.PermissionManager;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

import static ro.uaic.info.ip.proiect.b3.configurations.ServerErrorMessages.INTERNAL_ERROR_MESSAGE;

@Controller
public class ComentariuController {
    private final static Logger logger = Logger.getLogger(ComentariuController.class);

    @RequestMapping(value = "/materii/{numeMaterie}/{numeTema}/adaugaComentariu", method = RequestMethod.POST)
    public @ResponseBody
    String adaugaComentariu(@PathVariable("numeMaterie") String numeMaterie,
                            @PathVariable("numeTema") String numeTema,
                            @RequestParam("nrExercitiu") int nrExercitiu,
                            @RequestParam("username") String username,
                            @RequestParam("startRow") int startRow,
                            @RequestParam("endRow") int endRow,
                            @RequestParam("comentariu") String comentariu,
                            @CookieValue(value = "user", defaultValue = "-1") String loginToken) {
        try {
            if (PermissionManager.isUserAllowedToModifySubject(numeMaterie, loginToken)) {

                Materie materie = Materie.getByTitlu(numeMaterie);

                if (materie == null) {
                    return "Materia specificata nu exista.";
                }

                Cont student = Cont.getByUsername(username);

                if (student == null) {
                    return "Invalid student username";
                }

                Tema tema = Tema.getByMaterieIdAndNumeTema(materie.getId(), numeTema);

                if (tema == null) {
                    return "Tema specificata nu exista";
                }

                TemaIncarcata temaIncarcata = TemaIncarcata.get(student.getId(), tema.getId(), nrExercitiu);

                if (temaIncarcata == null) {
                    return "Tema incarcata nu exista";
                }

                ComentariuProfesor comentariuProfesor = new ComentariuProfesor(temaIncarcata.getId(), nrExercitiu, startRow, endRow, comentariu);
                comentariuProfesor.insert();

                try {
                    Notificare notificare = new Notificare(
                            String.format("[%s] A fost adaugat un comentariu la exercitiul %d din cadrul temei \"%s\"!", materie.getTitlu(), nrExercitiu, tema.getNumeTema()),
                            temaIncarcata.getIdCont(),
                            0);
                    notificare.insert();
                } catch (SQLException | NotificareException e) {
                    logger.error(e.getMessage(), e);
                }

                return "valid";
            } else {
                return "Utilizatorul nu este logat sau nu are permisiunile necesare!";
            }
        } catch (SQLException | ComentariuProfesorException e) {
            logger.error(e.getMessage(), e);
            return INTERNAL_ERROR_MESSAGE;
        }

    }


    @RequestMapping(value = "/materii/{numeMaterie}/{numeTema}/stergeComentariu", method = RequestMethod.POST)
    public @ResponseBody
    String stergeComentariu(@PathVariable("numeMaterie") String numeMaterie,
                            @PathVariable("numeTema") String numeTema,
                            @RequestParam("nrExercitiu") int nrExercitiu,
                            @RequestParam("username") String username,
                            @RequestParam("startRow") int startRow,
                            @RequestParam("endRow") int endRow,
                            @CookieValue(value = "user", defaultValue = "-1") String loginToken,
                            HttpServletResponse response) {
        try {
            if (PermissionManager.isUserAllowedToModifySubject(numeMaterie, loginToken)) {

                Materie materie = Materie.getByTitlu(numeMaterie);

                if (materie == null) {
                    return "Materia specificata nu exista.";
                }

                Cont student = Cont.getByUsername(username);

                if (student == null) {
                    return "Invalid student username";
                }

                Tema tema = Tema.getByMaterieIdAndNumeTema(materie.getId(), numeTema);

                if (tema == null) {
                    return "Tema specificata nu exista";
                }

                TemaIncarcata temaIncarcata = TemaIncarcata.get(student.getId(), tema.getId(), nrExercitiu);

                if (temaIncarcata == null) {
                    return "Tema incarcata nu exista";
                }


                ComentariuProfesor.delete(temaIncarcata.getId(), nrExercitiu, startRow, endRow);

                try {
                    Notificare notificare = new Notificare(
                            String.format("[%s] A fost sters un comentariu la exercitiul %d din cadrul temei \"%s\"!", materie.getTitlu(), nrExercitiu, tema.getNumeTema()),
                            temaIncarcata.getIdCont(),
                            0);
                    notificare.insert();
                } catch (SQLException | NotificareException e) {
                    logger.error(e.getMessage(), e);
                }

                return "valid";
            } else {
                return "Utilizatorul nu este logat sau nu are permisiunile necesare!";
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return INTERNAL_ERROR_MESSAGE;
        }

    }

}
