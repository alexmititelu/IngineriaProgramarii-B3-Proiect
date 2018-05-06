package ro.uaic.info.ip.proiect.b3.controllers.register;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ro.uaic.info.ip.proiect.b3.authentication.AuthenticationManager;

/**
 * Aceasta clasa reprezinta un controller pentru metoda GET a paginii de inregistrare din primul pas. (Vezi utilis/autentificare/ pentru detalii despre sistemul de autentificare)
 */
@Controller
public class StepOneRegisterPageController {
    /**
     * Metoda registerPageStepOne returneaza numele paginii ce trebuie trimise catre utilizator pentru pathul /register.
     *
     * In cazul in care utilizatorul care face cererea este deja autentificat acesta va fi redirectionat catre pagina de dashboard.
     *
     * In cazul in care utilizatorul nu este autentificat aceasta metoda va returna numele pagii de register.
     *
     * @param loginToken reprezinta valoare cookieului 'user' sau -1 in cazul in care cookieul cu acest nume nu exista
     * @param model aces parametru este folosit pentru a modela view-ul returnat catre utilizator
     *              prin intermediul modelului putem seta variabile in pagina trimisa catre utilizator
     *              vezi @DashboardController si resursa dashboard.html din fisierul resources/templates pentru exemplu
     * @return numele paginii returnate
     */
    @RequestMapping(value="/register", method=RequestMethod.GET)
    public String registerPageStepOne(@CookieValue(value = "user", defaultValue = "-1") String loginToken, Model model) {
        if (AuthenticationManager.isUserLoggedIn(loginToken)) {
            return "redirect:/";
        } else {
            return "register-step-one";
        }
    }
}
