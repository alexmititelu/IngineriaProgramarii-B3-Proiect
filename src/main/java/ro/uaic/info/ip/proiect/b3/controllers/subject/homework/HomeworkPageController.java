package ro.uaic.info.ip.proiect.b3.controllers.subject.homework;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeworkPageController {
    @RequestMapping(value = "/materii/{numeMaterie}/{numeTema}", method = RequestMethod.GET)
    public String getHomeworkPage(@CookieValue(value = "user", defaultValue = "-1") String loginToken, Model model) {
        return "";
    }
}
