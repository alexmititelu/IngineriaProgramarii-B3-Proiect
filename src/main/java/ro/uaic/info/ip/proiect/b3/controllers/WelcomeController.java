package ro.uaic.info.ip.proiect.b3.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ro.uaic.info.ip.proiect.b3.permissions.PermissionManager;

import java.sql.SQLException;

import static ro.uaic.info.ip.proiect.b3.configurations.ServerErrorMessages.INTERNAL_ERROR_MESSAGE;

/**
 * Aceasta clasa reprezinta un controller pentru metoda GET a paginii de home.
 */

@Controller
public class WelcomeController {
    private final static Logger logger = Logger.getLogger(WelcomeController.class);

    /**
     * In cazul in care utilizatorul este logat:
     * 1. Se apeleaza metoda dashboard din DashboardController pentru a crea si returna dashboardul utilizatorului logat.
     * In cazul in care utilizatorul nu este logat:
     * 1. Se returneaza numele paginii de login.
     *
     * @param loginToken tokenul de login al utilizatorului
     * @param model      acest parametru este folosit pentru a modela view-ul returnat catre utilizator
     *                   prin intermediul modelului putem seta variabile in pagina trimisa catre utilizator
     * @return numele paginii de login
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcome(@CookieValue(value = "user", defaultValue = "-1") String loginToken, Model model) {
        try {
            if (PermissionManager.isUserLoggedIn(loginToken)) {
                return (new DashboardController()).dashboard(model, PermissionManager.getUsernameLoggedIn(loginToken));
            } else {
                return "home-no-logged";
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);

            model.addAttribute("errorMessage", INTERNAL_ERROR_MESSAGE);
            return "error";
        }
    }
}
