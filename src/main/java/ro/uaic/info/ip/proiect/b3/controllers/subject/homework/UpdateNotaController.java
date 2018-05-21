package ro.uaic.info.ip.proiect.b3.controllers.subject.homework;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.uaic.info.ip.proiect.b3.configurations.ServerErrorMessages;
import ro.uaic.info.ip.proiect.b3.database.Database;
import ro.uaic.info.ip.proiect.b3.database.objects.cont.Cont;
import ro.uaic.info.ip.proiect.b3.database.objects.materie.Materie;
import ro.uaic.info.ip.proiect.b3.database.objects.tema.Tema;
import ro.uaic.info.ip.proiect.b3.permissions.PermissionManager;
import java.sql.SQLException;

@Controller
public class UpdateNotaController {
    private final static Logger logger = Logger.getLogger(UpdateNotaController.class);

    @RequestMapping(value = "/materii/{numeMaterie}/{numeTema}/update_nota", method = RequestMethod.POST)
    public @ResponseBody String updateNota(
            @CookieValue(value = "user", defaultValue = "-1") String loginToken,
            @PathVariable("numeMaterie") String numeMaterie,
            @PathVariable("numeTema") String numeTema,
            @RequestParam("username") String usernameToGrade,
            @RequestParam("nrExercitiu") int nrExercitiu,
            @RequestParam("nota") int nota) {
        try {
            if (PermissionManager.isUserAllowedToModifySubject(numeMaterie, loginToken)) {
                Materie materie = Materie.getByTitlu(numeMaterie);
                if (materie == null) return "Materia cu acest nume nu exista!";

                Tema tema = Tema.getByMaterieIdAndNumeTema(materie.getId(), numeTema);
                if (tema == null) return "Tema cu acest nume din cadrul materiei nu exista!";

                Cont cont = Cont.getByUsername(usernameToGrade);
                if (cont == null) return "Numele de utilizator pentru care se incearca editarea notei nu exista!";

                if (nota < 1 || nota > 10) return "Nota poate lua valori doar intre 1 si 10!";

                int updatedRows = Database.getInstance().updateOperation("update teme_incarcate set nota = ? where id_cont = ? and nr_exercitiu = ? and id_tema = ?",
                        Integer.toString(nota), Long.toString(cont.getId()), Integer.toString(nrExercitiu), Long.toString(tema.getId()));

                if (updatedRows == 0) return "Numarul exercitiului nu este valid!";

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

