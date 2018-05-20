package ro.uaic.info.ip.proiect.b3.controllers.subject.homework;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ro.uaic.info.ip.proiect.b3.permissions.PermissionManager;

import java.sql.SQLException;


@Controller
public class HomeworkPageController {
    @RequestMapping(value = "/materii/{numeMaterie}/{numeTema}", method = RequestMethod.GET)
    public String getHomeworkPage(@CookieValue(value = "user", defaultValue = "-1") String loginToken) throws SQLException {
        if (PermissionManager.isUserLoggedIn(loginToken)) {
            if (PermissionManager.isLoggedUserProfesor(loginToken)) {
                return "./profesor/tema";
            } else {
                return "./student/tema";
            }
        }
        
        return "error";
    }

}
