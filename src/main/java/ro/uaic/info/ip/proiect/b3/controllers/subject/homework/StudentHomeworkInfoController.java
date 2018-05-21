package ro.uaic.info.ip.proiect.b3.controllers.subject.homework;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.uaic.info.ip.proiect.b3.clientinfo.InfoExercitiuStudent;
import ro.uaic.info.ip.proiect.b3.permissions.PermissionManager;

import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class StudentHomeworkInfoController {
    private final static Logger logger = Logger.getLogger(StudentHomeworkInfoController.class);

    @RequestMapping(value = "/materii/{numeMaterie}/{numeTema}/student_info_json", method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<InfoExercitiuStudent> getStudentInfo(
            @CookieValue(value = "user", defaultValue = "-1") String loginToken,
            @PathVariable("numeMaterie") String numeMaterie,
            @PathVariable("numeTema") String numeTema) {
        try {
            if (!PermissionManager.isUserLoggedIn(loginToken)) {
                return null;
            }

            if (!PermissionManager.isLoggedUserStudent(loginToken)) {
                return null;
            }

            return InfoExercitiuStudent.getInfoExercitiiStudent(loginToken, numeMaterie, numeTema);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
