package ro.uaic.info.ip.proiect.b3.controllers.subject.homework;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ro.uaic.info.ip.proiect.b3.clientinfo.ExercitiuInfoProfesor;

import java.util.ArrayList;

@Controller
public class HomeworkProfesorInfoController {
    @RequestMapping(value = "/materii/{numeMaterie}/{numeTema}/profesor_info", method = RequestMethod.GET)
    public ArrayList<ExercitiuInfoProfesor> getProfesorInfo(
            @CookieValue(value = "user", defaultValue = "-1") String loginToken,
            @PathVariable("numeMaterie") String numeMaterie,
            @PathVariable("numeTema") String numeTema) {
        return new ArrayList<>();
    }
}
