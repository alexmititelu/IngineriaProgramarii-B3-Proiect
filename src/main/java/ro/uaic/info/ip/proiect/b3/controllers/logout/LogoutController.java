package ro.uaic.info.ip.proiect.b3.controllers.logout;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.uaic.info.ip.proiect.b3.configurations.ServerErrorMessages;
import ro.uaic.info.ip.proiect.b3.database.objects.contconectat.ContConectat;
import ro.uaic.info.ip.proiect.b3.permissions.PermissionManager;

import java.sql.SQLException;


@Controller
public class LogoutController {
    private Logger logger = Logger.getLogger(LogoutController.class);

    @RequestMapping(value = "/sign-out", method = RequestMethod.POST)
    public @ResponseBody
    String logout(@CookieValue(value = "user", defaultValue = "-1") String loginToken) {
        try {
            if (PermissionManager.isUserLoggedIn(loginToken)) {
                ContConectat.delete(loginToken);
                return "valid";
            } else {
                return "Incercare de delogare cand utilizatorul nu era logat!";
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return ServerErrorMessages.INTERNAL_ERROR_MESSAGE;
        }
    }
}
