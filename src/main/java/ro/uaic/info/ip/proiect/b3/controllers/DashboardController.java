package ro.uaic.info.ip.proiect.b3.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

/**
 * Aceasta clasa reprezinta un controller pentru pagina de dashboard a unui utilizator.
 */

@Controller
public class DashboardController {
    /**
     * Aceasta metoda este apelata de oriunde se doreste returnarea unui view ce reprezinta dashboardul unui utilizator.
     *
     * @param model acest parametru este folosit pentru a modela view-ul returnat catre utilizator
     *              prin intermediul modelului putem seta variabile in pagina trimisa catre utilizator
     * @param user numele utilizatorului pentru care trebuie contruit dashboardul
     * @return numele view-ului ce reprezinta dashboardul
     */
    public String dashboard(Model model, String user) {
        model.addAttribute("username", user);
        return "dashboard";
    }
}
