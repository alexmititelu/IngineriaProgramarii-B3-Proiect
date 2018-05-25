package ro.uaic.info.ip.proiect.b3.controllers.subject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.uaic.info.ip.proiect.b3.configurations.ServerErrorMessages;
import ro.uaic.info.ip.proiect.b3.database.Database;
import ro.uaic.info.ip.proiect.b3.database.objects.cont.Cont;
import ro.uaic.info.ip.proiect.b3.database.objects.inscriere.Inscriere;
import ro.uaic.info.ip.proiect.b3.database.objects.inscriere.exceptions.InscriereException;
import ro.uaic.info.ip.proiect.b3.database.objects.materie.Materie;
import ro.uaic.info.ip.proiect.b3.permissions.PermissionManager;

import java.sql.SQLException;

@Controller
public class SubscribeController {
    private final static Logger logger = Logger.getLogger(SubscribeController.class);

    @RequestMapping(value = "/materii/{numeMaterie}/subscribe", method = RequestMethod.POST)
    public @ResponseBody
    String subscribe(
            @CookieValue(value = "user", defaultValue = "-1") String loginToken,
            @PathVariable("numeMaterie") String numeMaterie) {
        try {
            if (PermissionManager.isLoggedUserStudent(loginToken)) {
                Cont cont = Cont.getByLoginToken(loginToken);
                if (cont == null) {
                    logger.error("Get cont by login token failed immediatly after PermissionManager.isLoggedUserStudent returned true!");
                    return ServerErrorMessages.INTERNAL_ERROR_MESSAGE;
                }

                Materie materie = Materie.getByTitlu(numeMaterie);
                if (materie == null) return "Materia cu acest nume nu exista!";

                Inscriere inscriere = Inscriere.get(cont.getId(), materie.getId());
                if (inscriere != null) return "Utilizatorul " + cont.getUsername() + " este deja inscris la aceasta materie!";

                inscriere = new Inscriere(cont.getId(), materie.getId());
                inscriere.insert();

                return "valid";
            } else {
                return ServerErrorMessages.UNAUTHORIZED_ACCESS_MESSAGE;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return ServerErrorMessages.INTERNAL_ERROR_MESSAGE;
        } catch (InscriereException e) {
            return e.getMessage();
        }
    }

    @RequestMapping(value = "/materii/{numeMaterie}/unsubscribe", method = RequestMethod.POST)
    public @ResponseBody
    String unsubscribe(
            @CookieValue(value = "user", defaultValue = "-1") String loginToken,
            @PathVariable("numeMaterie") String numeMaterie) {
        try {
            if (PermissionManager.isLoggedUserStudent(loginToken)) {
                Cont cont = Cont.getByLoginToken(loginToken);
                if (cont == null) {
                    logger.error("Get cont by login token failed immediatly after PermissionManager.isLoggedUserStudent returned true!");
                    return ServerErrorMessages.INTERNAL_ERROR_MESSAGE;
                }

                Materie materie = Materie.getByTitlu(numeMaterie);
                if (materie == null) return "Materia cu acest nume nu exista!";

                Inscriere inscriere = Inscriere.get(cont.getId(), materie.getId());
                if (inscriere == null) return "Utilizatorul " + cont.getUsername() + " nu este inscris la aceasta materie!";

                int updatedRows = Database.getInstance().updateOperation("DELETE FROM inscrieri WHERE id_cont = ? AND id_materie = ?",
                        Long.toString(cont.getId()), Long.toString(materie.getId()));

                if (updatedRows == 0) return ServerErrorMessages.INTERNAL_ERROR_MESSAGE;

                return "valid";
            } else {
                return ServerErrorMessages.UNAUTHORIZED_ACCESS_MESSAGE;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return ServerErrorMessages.INTERNAL_ERROR_MESSAGE;
        } catch (InscriereException e) {
            return e.getMessage();
        }
    }

    @RequestMapping(value = "/materii/{numeMaterie}/is-subscribed", method = RequestMethod.GET)
    public @ResponseBody
    String isSubscribed(
            @CookieValue(value = "user", defaultValue = "-1") String loginToken,
            @PathVariable("numeMaterie") String numeMaterie) {
        try {
            if (PermissionManager.isLoggedUserStudent(loginToken)) {
                Cont cont = Cont.getByLoginToken(loginToken);
                if (cont == null) {
                    logger.error("Get cont by login token failed immediatly after PermissionManager.isLoggedUserStudent returned true!");
                    return ServerErrorMessages.INTERNAL_ERROR_MESSAGE;
                }

                Materie materie = Materie.getByTitlu(numeMaterie);
                if (materie == null) return "Materia cu acest nume nu exista!";

                Inscriere inscriere = Inscriere.get(cont.getId(), materie.getId());
                return inscriere != null ? "YES" : "NO";
            } else {
                return ServerErrorMessages.UNAUTHORIZED_ACCESS_MESSAGE;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return ServerErrorMessages.INTERNAL_ERROR_MESSAGE;
        } catch (InscriereException e) {
            return e.getMessage();
        }
    }
}
