package ro.uaic.info.ip.proiect.b3.controllers.subject.homework;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.uaic.info.ip.proiect.b3.database.objects.cont.Cont;
import ro.uaic.info.ip.proiect.b3.database.objects.student.Student;
import ro.uaic.info.ip.proiect.b3.permissions.PermissionManager;

import java.sql.SQLException;

import static ro.uaic.info.ip.proiect.b3.configurations.ServerErrorMessages.INTERNAL_ERROR_MESSAGE;
import static ro.uaic.info.ip.proiect.b3.configurations.ServerErrorMessages.UNAUTHORIZED_ACCESS_MESSAGE;

@Controller
public class ComparaController {
    private final static Logger logger = Logger.getLogger(ComparaController.class);

    @RequestMapping(value = "/materii/{numeMaterie}/{numeTema}/compara", method = RequestMethod.GET)
    public String getComparaPage(
            @CookieValue(value = "user", defaultValue = "-1") String loginToken,
            @PathVariable("numeMaterie") String numeMaterie,
            @PathVariable("numeTema") String numeTema,
            @RequestParam("username1") String username1,
            @RequestParam("username2") String username2,
            @RequestParam("nrExercitiu") int nrExercitiu,
            Model model) {
        try {
            if (PermissionManager.isUserAllowedToModifySubject(numeMaterie, loginToken)) {
                Cont cont1 = Cont.getByUsername(username1);
                Student student1 = Student.getByEmail(cont1.getEmail());

                Cont cont2 = Cont.getByUsername(username2);
                Student student2 = Student.getByEmail(cont2.getEmail());

                model.addAttribute("nume1", student1.getNume());
                model.addAttribute("prenume1", student1.getPrenume());

                model.addAttribute("nume2", student2.getNume());
                model.addAttribute("prenume2", student2.getPrenume());

                return "compara";
            } else {
                model.addAttribute("errorMessage", UNAUTHORIZED_ACCESS_MESSAGE);
                return "error";
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);

            model.addAttribute("errorMessage", INTERNAL_ERROR_MESSAGE);
            return "error";
        }
    }
}
