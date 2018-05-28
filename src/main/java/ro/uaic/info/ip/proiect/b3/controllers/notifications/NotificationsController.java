package ro.uaic.info.ip.proiect.b3.controllers.notifications;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.uaic.info.ip.proiect.b3.database.objects.cont.Cont;
import ro.uaic.info.ip.proiect.b3.database.objects.notificare.Notificare;
import ro.uaic.info.ip.proiect.b3.permissions.PermissionManager;

import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class NotificationsController {
    private static final Logger logger = Logger.getLogger(NotificationsController.class);

    @RequestMapping(value = "/notifications", method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<Notificare> getNotifications(
            @CookieValue(value = "user", defaultValue = "-1") String loginToken) {
        try {
            if (PermissionManager.isLoggedUserStudent(loginToken)) {
                Cont cont = Cont.getByLoginToken(loginToken);

                if (cont == null) {
                    logger.error("Get cont by login token returned null after PermissionsManager.isLoggedUserStudent returned true!");
                    return null;
                }

                return Notificare.getNotificationsForUser(cont.getId());
            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
