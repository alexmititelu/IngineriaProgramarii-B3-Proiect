package ro.uaic.info.ip.proiect.b3.controllers.subject.homework;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.uaic.info.ip.proiect.b3.clientinfo.InfoExercitiuProfesor;
import ro.uaic.info.ip.proiect.b3.clientinfo.StudentNenotat;
import ro.uaic.info.ip.proiect.b3.clientinfo.StudentNotat;
import ro.uaic.info.ip.proiect.b3.clientinfo.TemaPlagiata;
import ro.uaic.info.ip.proiect.b3.database.objects.materie.Materie;
import ro.uaic.info.ip.proiect.b3.database.objects.tema.Tema;
import ro.uaic.info.ip.proiect.b3.database.objects.temaexercitiuextensie.TemaExercitiuExtensie;
import ro.uaic.info.ip.proiect.b3.permissions.PermissionManager;

import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class ProfesorHomeworkInfoController {
    private Logger logger = Logger.getLogger(ProfesorHomeworkInfoController.class);

    @RequestMapping(value = "/materii/{numeMaterie}/{numeTema}/profesor_info", method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<InfoExercitiuProfesor> getProfesorInfo(
            @CookieValue(value = "user", defaultValue = "-1") String loginToken,
            @PathVariable("numeMaterie") String numeMaterie,
            @PathVariable("numeTema") String numeTema) {

        try {
            if (!PermissionManager.isUserAllowedToModifySubject(numeMaterie, loginToken)) {
                return null;
            }

            Materie materie = Materie.getByTitlu(numeMaterie);
            Tema tema = null;

            if (materie != null) {
                tema = Tema.getByMaterieIdAndNumeTema(materie.getId(), numeTema);
            }

            if (tema == null) {
                return null;
            }

            ArrayList<InfoExercitiuProfesor> infoExercitiiProfesor = new ArrayList<>();
            ArrayList<TemaExercitiuExtensie> exercitii = TemaExercitiuExtensie.getAllExercises(tema.getId());

            for (TemaExercitiuExtensie exercitiu : exercitii) {
                InfoExercitiuProfesor infoExercitiuProfesor = new InfoExercitiuProfesor();

                infoExercitiuProfesor.setNume("Exercitiul " + exercitiu.getNrExercitiu());
                infoExercitiuProfesor.setEnunt(exercitiu.getEnunt());
                infoExercitiuProfesor.setExtensie(exercitiu.getExtensieAcceptata());
                infoExercitiuProfesor.setStudentiNenotati(StudentNenotat.getAllNenotati(exercitiu));
                infoExercitiuProfesor.setStudentiNotati(StudentNotat.getAllNotati(exercitiu));
                infoExercitiuProfesor.setTemePlagiate(TemaPlagiata.getAllForExercise(exercitiu));

                infoExercitiiProfesor.add(infoExercitiuProfesor);
            }

            return infoExercitiiProfesor;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
