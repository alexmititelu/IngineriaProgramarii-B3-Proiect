package ro.uaic.info.ip.proiect.b3.controllers.subject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.uaic.info.ip.proiect.b3.authentication.AuthenticationManager;
import ro.uaic.info.ip.proiect.b3.database.Database;

import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
public class SubjectController {
    @RequestMapping(value = "/materii/{numeMaterie}", method = RequestMethod.GET)
    String getMaterie(@CookieValue(value = "user", defaultValue = "-1") String loginToken,
                      Model model,
                      @PathVariable String numeMaterie,
                      HttpServletResponse response) {

        if (AuthenticationManager.isUserLoggedIn(loginToken) && AuthenticationManager.isLoggedUserProfesor(loginToken)) {
            Connection connection = null;
            ResultSet selectResult = null;

            try {
                connection = Database.getInstance().getConnection();

                selectResult = Database.getInstance().selectQuery(connection, "SELECT an,semestru FROM materii where titlu = ?", numeMaterie);
                Integer an = selectResult.getInt(1);
                Integer semestru = selectResult.getInt(2);

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
}
