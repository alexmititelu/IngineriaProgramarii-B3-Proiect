package ro.uaic.info.ip.proiect.b3.controllers.subject.homework;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContinutFisierController {
    @RequestMapping(value = "/materii/{numeMaterie}/{numeTema}/continut_fisier", method = RequestMethod.POST)
    public String[] getContinutFisier(
            @CookieValue(value = "user", defaultValue = "-1") String loginToken,
            @RequestParam("username") String usernameToGetHomeworkFor,
            @RequestParam("nrExercitiu") int nrExercitiu) {
        return new String[1];
    }
}
