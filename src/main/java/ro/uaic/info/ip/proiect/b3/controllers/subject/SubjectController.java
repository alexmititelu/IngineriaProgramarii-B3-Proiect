package ro.uaic.info.ip.proiect.b3.controllers.subject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.uaic.info.ip.proiect.b3.authentication.AuthenticationManager;
import ro.uaic.info.ip.proiect.b3.controllers.subject.Objects.Tema;
import ro.uaic.info.ip.proiect.b3.database.Database;
import ro.uaic.info.ip.proiect.b3.materii.TemeService;

import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SubjectController {

    @RequestMapping(value = "/materii/{numeMaterie}", method = RequestMethod.GET)
    public @ResponseBody String getMaterie(@CookieValue(value = "user", defaultValue = "-1") String loginToken,
                                              Model model,
                                              @PathVariable String numeMaterie,
                                              HttpServletResponse response) {

        if (AuthenticationManager.isUserLoggedIn(loginToken)) {
            Connection connection = null;
            ResultSet selectResult = null;

            try {
                connection = Database.getInstance().getConnection();

                selectResult = Database.getInstance().selectQuery(connection, "SELECT an,semestru FROM materii where titlu = ?", numeMaterie);
                Integer an = selectResult.getInt(selectResult.getInt(1));
                Integer semestru = selectResult.getInt(selectResult.getInt(2));

                model.addAttribute("materieAn",an);
                model.addAttribute("materieSemestru",semestru);
                model.addAttribute("materieNume",numeMaterie);

                return "valid";
            } catch (SQLException e) {
                return ("SQL Exception thrown: " + e);
            } catch (Exception e) {
                return ("Exception thrown: " + e);
            }

        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "Utilizatorul nu este logat sau nu are permisiunile necesare!";
        }
    }

    @RequestMapping(value = "/materii/{numeMaterie}/teme", method = RequestMethod.GET)
    public @ResponseBody List<Tema> getTeme(@CookieValue(value = "user", defaultValue = "-1") String loginToken,
                      Model model,
                      @PathVariable String numeMaterie,
                      HttpServletResponse response) {

        if (AuthenticationManager.isUserLoggedIn(loginToken)) {

            List<Tema> listaTeme = null;

            try {

                listaTeme = TemeService.getAll(numeMaterie);

                return listaTeme;

            } catch (SQLException e) {
                return null;
            }

        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
    }


}
