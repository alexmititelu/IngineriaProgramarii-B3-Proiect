package ro.uaic.info.ip.proiect.b3.controllers.subject.homework;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.uaic.info.ip.proiect.b3.configurations.ServerErrorMessages;
import ro.uaic.info.ip.proiect.b3.database.objects.cont.Cont;
import ro.uaic.info.ip.proiect.b3.database.objects.materie.Materie;
import ro.uaic.info.ip.proiect.b3.database.objects.solutiepublica.SolutiePublica;
import ro.uaic.info.ip.proiect.b3.database.objects.tema.Tema;
import ro.uaic.info.ip.proiect.b3.database.objects.temaincarcata.TemaIncarcata;
import ro.uaic.info.ip.proiect.b3.permissions.PermissionManager;

import java.sql.SQLException;

@Controller
public class PublicSolutionsController {
    private final static Logger logger = Logger.getLogger(PublicSolutionsController.class);

    @RequestMapping(value = "/materii/{numeMaterie}/{numeTema}/post-public-solution", method = RequestMethod.POST)
    public @ResponseBody String postPublicSolution(
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

                Cont cont = Cont.getByUsername(username);
                if (cont == null) return "Contul cu acest nume de utilizator nu exista!";

                TemaIncarcata temaIncarcata = TemaIncarcata.get(cont.getId(), tema.getId(), nrExercitiu);
                if (temaIncarcata == null) return "Acest utilizator nu a incarcat exercitiul pentru aceasta tema!";

                if (SolutiePublica.get(tema.getId(), nrExercitiu, temaIncarcata.getIdCont()) != null) return "Aceasta tema este deja publica!";

                SolutiePublica solutiePublica = new SolutiePublica(tema.getId(), nrExercitiu, temaIncarcata.getIdCont());
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
}
