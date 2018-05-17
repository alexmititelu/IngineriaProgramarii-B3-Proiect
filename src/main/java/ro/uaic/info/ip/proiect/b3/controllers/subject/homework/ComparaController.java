package ro.uaic.info.ip.proiect.b3.controllers.subject.homework;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ComparaController {
    @RequestMapping(value = "/materii/{numeMaterie}/{numeTema}/compara", method = RequestMethod.GET)
    public String getComparaPage(
            @CookieValue(value = "user", defaultValue = "-1") String loginToken,
            @PathVariable("numeMaterie") String numeMaterie,
            @PathVariable("numeTema") String numeTema,
            @RequestParam("username1") String username1,
            @RequestParam("username2") String username2,
            @RequestParam("nrExercitiu") int nrExercitiu) {
        return "";
    }
}
