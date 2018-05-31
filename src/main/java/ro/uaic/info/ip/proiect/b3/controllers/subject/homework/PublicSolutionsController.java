package ro.uaic.info.ip.proiect.b3.controllers.subject.homework;

import com.mysql.fabric.Server;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.uaic.info.ip.proiect.b3.clientinfo.SolutiePublicaInfo;
import ro.uaic.info.ip.proiect.b3.configurations.ServerErrorMessages;
import ro.uaic.info.ip.proiect.b3.database.Database;
import ro.uaic.info.ip.proiect.b3.database.objects.cont.Cont;
import ro.uaic.info.ip.proiect.b3.database.objects.materie.Materie;
import ro.uaic.info.ip.proiect.b3.database.objects.solutiepublica.SolutiePublica;
import ro.uaic.info.ip.proiect.b3.database.objects.student.Student;
import ro.uaic.info.ip.proiect.b3.database.objects.tema.Tema;
import ro.uaic.info.ip.proiect.b3.database.objects.temaincarcata.TemaIncarcata;
import ro.uaic.info.ip.proiect.b3.permissions.PermissionManager;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class PublicSolutionsController {
    private final static Logger logger = Logger.getLogger(PublicSolutionsController.class);

    @RequestMapping(value = "/materii/{numeMaterie}/{numeTema}/post-public-solution", method = RequestMethod.POST)
    public @ResponseBody
    String postPublicSolution(
            @CookieValue(value = "user", defaultValue = "-1") String loginToken,
            @PathVariable("numeMaterie") String numeMaterie,
            @PathVariable("numeTema") String numeTema,
            @RequestParam("nrExercitiu") int nrExercitiu,
            @RequestParam("username") String username) {
        try {
            if (PermissionManager.isUserAllowedToModifySubject(numeMaterie, loginToken)) {
                Materie materie = Materie.getByTitlu(numeMaterie);
                if (materie == null) return "Aceasta materie nu exista!";

                Tema tema = Tema.getByMaterieIdAndNumeTema(materie.getId(), numeTema);
                if (tema == null) return "Tema cu acest nume din cadrul materiei nu exista!";

                if (tema.getDeadline().after(new Date(new java.util.Date().getTime()))) return "Nu se pot face solutii publice inainte de deadline!";

                Cont cont = Cont.getByUsername(username);
                if (cont == null) return "Contul cu acest nume de utilizator nu exista!";

                TemaIncarcata temaIncarcata = TemaIncarcata.get(cont.getId(), tema.getId(), nrExercitiu);
                if (temaIncarcata == null) return "Acest utilizator nu a incarcat exercitiul pentru aceasta tema!";

                if (SolutiePublica.get(tema.getId(), nrExercitiu, temaIncarcata.getId()) != null)
                    return "Aceasta tema este deja publica!";

                SolutiePublica solutiePublica = new SolutiePublica(tema.getId(), nrExercitiu, temaIncarcata.getId());
                solutiePublica.insert();

                return "valid";
            } else {
                return ServerErrorMessages.UNAUTHORIZED_ACCESS_MESSAGE;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return ServerErrorMessages.INTERNAL_ERROR_MESSAGE;
        }
    }

    @RequestMapping(value = "/materii/{numeMaterie}/{numeTema}/public-solutions", method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<ArrayList<SolutiePublicaInfo>> getPublicSolutions(
            @CookieValue(value = "user", defaultValue = "-1") String loginToken,
            @PathVariable("numeMaterie") String numeMaterie,
            @PathVariable("numeTema") String numeTema) {
        try {
            if (PermissionManager.isUserLoggedIn(loginToken)) {
                ArrayList<ArrayList<SolutiePublicaInfo>> exercitii = new ArrayList<>();

                Materie materie = Materie.getByTitlu(numeMaterie);
                if (materie == null) return null;

                Tema tema = Tema.getByMaterieIdAndNumeTema(materie.getId(), numeTema);
                if (tema == null) return null;

                for (int i = 1; i <= tema.getNrExercitii(); ++i) {
                    ArrayList<SolutiePublicaInfo> infoSolutiiPublice = new ArrayList<>();
                    ArrayList<SolutiePublica> solutiiPublice = SolutiePublica.getAllForHomeworkAndExercise(tema.getId(), i);

                    for (SolutiePublica solutiePublica : solutiiPublice) {
                        TemaIncarcata temaIncarcata = TemaIncarcata.getById(solutiePublica.getIdTemaIncarcata());
                        Cont cont = Cont.getById(temaIncarcata.getIdCont());
                        Student student = Student.getByEmail(cont.getEmail());

                        infoSolutiiPublice.add(new SolutiePublicaInfo(student.getNume(), student.getPrenume(), cont.getUsername()));
                    }

                    exercitii.add(infoSolutiiPublice);
                }

                return exercitii;
            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @RequestMapping(value = "/materii/{numeMaterie}/{numeTema}/delete-public-solution", method = RequestMethod.POST)
    public @ResponseBody
    String deletePublicSolution(
            @CookieValue(value = "user", defaultValue = "-1") String loginToken,
            @PathVariable("numeMaterie") String numeMaterie,
            @PathVariable("numeTema") String numeTema,
            @RequestParam("nrExercitiu") int nrExercitiu,
            @RequestParam("username") String username) {
        try {
            if (PermissionManager.isUserAllowedToModifySubject(numeMaterie, loginToken)) {
                Materie materie = Materie.getByTitlu(numeMaterie);
                if (materie == null) return "Aceasta materie nu exista!";

                Tema tema = Tema.getByMaterieIdAndNumeTema(materie.getId(), numeTema);
                if (tema == null) return "Tema cu acest nume din cadrul materiei nu exista!" ;

                Cont cont = Cont.getByUsername(username);
                if (cont == null) return "Contul cu acest username nu exista!";

                TemaIncarcata temaIncarcata = TemaIncarcata.get(cont.getId(), tema.getId(), nrExercitiu);
                if (temaIncarcata == null) return "Nu exista aceasta tema incarcata!";

                SolutiePublica solutiePublica = SolutiePublica.get(tema.getId(), nrExercitiu, temaIncarcata.getId());
                if (solutiePublica == null) return "Aceasta solutie nu este publica!";

                solutiePublica.delete();
                return "valid";
            } else {
                return ServerErrorMessages.UNAUTHORIZED_ACCESS_MESSAGE;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return ServerErrorMessages.INTERNAL_ERROR_MESSAGE;
        }
    }
}
