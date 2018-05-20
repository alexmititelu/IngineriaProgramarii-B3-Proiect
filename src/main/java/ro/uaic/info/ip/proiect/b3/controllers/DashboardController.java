package ro.uaic.info.ip.proiect.b3.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ro.uaic.info.ip.proiect.b3.database.objects.cont.Cont;

import java.sql.SQLException;

import static ro.uaic.info.ip.proiect.b3.configurations.ServerErrorMessages.INTERNAL_ERROR_MESSAGE;
import static ro.uaic.info.ip.proiect.b3.configurations.ServerErrorMessages.PAGE_NOT_FOUND_MESSAGE;

/**
 * Aceasta clasa reprezinta un controller pentru pagina de dashboard a unui utilizator.
 */

@Controller
public class DashboardController {
    private final static Logger logger = Logger.getLogger(WelcomeController.class);

    /**
     * Aceasta metoda este apelata de oriunde se doreste returnarea unui view ce reprezinta dashboardul unui utilizator.
     *
     * @param model acest parametru este folosit pentru a modela view-ul returnat catre utilizator
     *              prin intermediul modelului putem seta variabile in pagina trimisa catre utilizator
     * @param username numele utilizatorului pentru care trebuie contruit dashboardul
     * @return numele view-ului ce reprezinta dashboardul
     */
    public String dashboard(Model model, String username) {
        try {
            Cont cont = Cont.getByUsername(username);
            if (cont != null && cont.getPermission() < 2) {
                model.addAttribute("username", username);
                return "./student/dashboard";
            } else if (cont != null && cont.getPermission() >= 2) {
                model.addAttribute("username", username);
                return "./profesor/dashboard";
            } else {
                model.addAttribute("errorMessage", PAGE_NOT_FOUND_MESSAGE);
                return "model";
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);

            model.addAttribute("errorMessage", INTERNAL_ERROR_MESSAGE);
            return "error";
        }
    }
}
