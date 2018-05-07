package ro.uaic.info.ip.proiect.b3.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ro.uaic.info.ip.proiect.b3.authentication.AuthenticationManager;

/**
 * Aceasta clasa reprezinta un controller pentru metoda GET a paginii de home.
 */

@Controller
public class WelcomeController {
    /**
     * In cazul in care utilizatorul este logat:
     * 1. Se apeleaza metoda dashboard in DashboardController pentru a crea si returna dashboardul utilizatorului logat.
     * <p>
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
        if (AuthenticationManager.isUserLoggedIn(loginToken)) {
            return (new DashboardController()).dashboard(model, AuthenticationManager.getUsernameLoggedIn(loginToken));
        } else {
            return "login";
        }
    }
}
