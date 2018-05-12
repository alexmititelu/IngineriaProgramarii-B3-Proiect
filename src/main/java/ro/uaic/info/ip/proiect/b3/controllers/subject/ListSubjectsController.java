package ro.uaic.info.ip.proiect.b3.controllers.subject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

public class ListSubjectsController {

    /**
     * 1. Verificam daca userul e logat, in caz afirmativ -> 2, altfel, return "invalid"
     * 2. Cream o lista cu toate materiile ( un json cu toate obiectele de tip materie ce contin (nume, semestru, an)
     * 3. Returnam valid, in cazul in care totul a decurs cu succes, altfel, returnam un mesaj specific de eroare
     */

    @RequestMapping(value = "/materii", method = RequestMethod.POST)
    public @ResponseBody String listeazaMaterii (HttpServletResponse response) {


        if ( true ) {
            // 1.
            // 2.
            try {
                //
                // adaugati in response lista de materii disponibile
                return "valid";
            } catch(Exception e) {
                // faceti cu exceptii specifice ce pot aparea
                // 3
                return "mesaj de eroare";
            }

        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "invalid";
        }
    }
}
