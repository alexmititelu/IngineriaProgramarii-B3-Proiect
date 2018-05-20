package ro.uaic.info.ip.proiect.b3.controllers.subject.homework;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UpdateNotaController {
    @RequestMapping(value = "/materii/{numeMaterie}/{numeTema}/update_nota", method = RequestMethod.POST)
    public String updateNota(
            @CookieValue(value = "user", defaultValue = "-1") String loginToken,
            @RequestParam("username") String usernameToGrade,
            @RequestParam("nrExercitiu") int nrExercitiu,
            @RequestParam("nota") int nota) {
        return "valid";
    }
}
