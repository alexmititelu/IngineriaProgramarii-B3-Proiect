package ro.uaic.info.ip.proiect.b3.controllers.subject.homework;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.uaic.info.ip.proiect.b3.configurations.ServerErrorMessages;
import ro.uaic.info.ip.proiect.b3.database.Database;
import ro.uaic.info.ip.proiect.b3.permissions.PermissionManager;

import java.sql.SQLException;


@Controller
public class UpdateNotaController {

    private final static Logger logger = Logger.getLogger(UpdateNotaController.class);

    @RequestMapping(value = "/materii/{numeMaterie}/{numeTema}/update_nota", method = RequestMethod.POST)
    public String updateNota(
            @CookieValue(value = "user", defaultValue = "-1") String loginToken,
            @PathVariable("numeMaterie") String numeMaterie,
            @PathVariable("numeMaterie") String nume_tema,
            @RequestParam("username") String usernameToGrade,
            @RequestParam("nrExercitiu") String nrExercitiu,
            @RequestParam("nota") String nota) {

        try {
            if (PermissionManager.isLoggedUserProfesor(loginToken) &&
                    PermissionManager.isUserAllowedToModifySubject(numeMaterie, loginToken) &&
                    PermissionManager.isRegisterTokenValid(loginToken)) {

                Database.getInstance().updateOperation("update teme_incarcate set nota = ? where id_cont = " +
                                "(select id from conturi where username = ?) and nr_exercitiu = ? and id_tema = " +
                                "(select id where nume_tema = ?)"
                        , nota, usernameToGrade, nrExercitiu, nume_tema);

                return "Valid";
            } else {
                return ServerErrorMessages.UNAUTHORIZED_ACCESS_MESSAGE;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return ServerErrorMessages.INTERNAL_ERROR_MESSAGE;
        }
    }
}

