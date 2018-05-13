package ro.uaic.info.ip.proiect.b3.controllers.subject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.uaic.info.ip.proiect.b3.authentication.AuthenticationManager;
import ro.uaic.info.ip.proiect.b3.database.objects.Materie;
import ro.uaic.info.ip.proiect.b3.materie.MateriiService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class ListSubjectsController {

    /**
     * 1. Verificam daca userul e logat, in caz afirmativ -> 2, altfel, return "invalid"
     * 2. Cream o lista cu toate materiile ( un json cu toate obiectele de tip materie ce contin (nume, semestru, an)
     * 3. Returnam valid, in cazul in care totul a decurs cu succes, altfel, returnam un mesaj specific de eroare
     */

    @RequestMapping(value = "/materii", method = RequestMethod.GET)
    public @ResponseBody List<Materie> listeazaMaterii (HttpServletResponse response , @CookieValue(value = "user", defaultValue = "-1") String loginToken) {
        if (!AuthenticationManager.isUserLoggedIn(loginToken)) {
            try {
                List<Materie> materii = MateriiService.getAll();
                return materii;
            } catch(Exception e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return null;
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
    }

}
