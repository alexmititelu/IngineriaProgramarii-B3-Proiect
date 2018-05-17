package ro.uaic.info.ip.proiect.b3.controllers.subject.homework;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.uaic.info.ip.proiect.b3.clientinfo.HotZone;

import java.util.ArrayList;

@Controller
public class HomeworkHotZonesController {
    @RequestMapping(value = "/materii/{numeMaterie}/{numeTema}/hot_zones", method = RequestMethod.POST)
    public ArrayList<HotZone> getHotZones(
            @CookieValue(value = "user", defaultValue = "-1") String loginToken,
            @PathVariable("numeMaterie") String numeMaterie,
            @PathVariable("numeTema") String numeTema,
            @RequestParam("username1") String username1,
            @RequestParam("username2") String username2,
            @RequestParam("nrExercitiu") String nrExercitiu) {
        return new ArrayList();
    }
}
