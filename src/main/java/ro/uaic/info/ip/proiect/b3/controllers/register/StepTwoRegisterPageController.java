package ro.uaic.info.ip.proiect.b3.controllers.register;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ro.uaic.info.ip.proiect.b3.authentication.AuthenticationManager;
import ro.uaic.info.ip.proiect.b3.controllers.DashboardController;

import javax.servlet.http.HttpServletResponse;

/**
 * Aceasta clasa reprezinta un controller pentru metoda GET a paginii de inregistrare din al doilea pas. (Vezi utilis/autentificare/ pentru detalii despre sistemul de autentificare)
 */
@Controller
public class StepTwoRegisterPageController {
    /**
     * Metoda registerPageStepTwo returneaza numele paginii ce trebuie trimise catre utilizator pentru pathul /register/{register-token} unde {register-token} reprezinta
     * un string generat la cererea unui utilizator ce doreste sa se inregistreze.
     *
     * In cazul in care utilizatorul care face cererea este deja autentificat acesta va fi redirectionat catre pagina de dashboard.
     *
     * In cazul in care utilizatorul nu este autentificat:
     * 1. Se valideaza existenta tokenului in baza de date (tabelul register_links)
     * 2. Se trimite pagina ce corespunde pasului al doilea de inregistrare. Pagina din pasul al doilea de inregistrare reprezinta un view ce
     *    contine variabila 'email'. Aceasta variabila trebuie setata egala cu emailul utilizatorului care detine tokenul. Emailul utilizatorului
     *    se regaseste in baza de date in acelasi tabel, register_links.
     * In cazul in care validarea de la pasul 1 esueaza raspunsul primit va fi 400 Bad Request iar pagina returnata va fi "error".
     *
     * @param loginToken reprezinta valoare cookieului 'user' sau -1 in cazul in care cookieul cu acest nume nu exista
     * @param model acest parametru este folosit pentru a modela view-ul returnat catre utilizator
     *              prin intermediul modelului putem seta variabile in pagina trimisa catre utilizator
     *              vezi @DashboardController si resursa dashboard.html din fisierul resources/templates pentru exemplu
     * @param registerToken reprezinta un string cu valoarea {register-token} din path
     * @return numele paginii returnate
     */
    @RequestMapping(value="/register/{registerToken}", method=RequestMethod.GET)
    public String registerPageStepTwo(@CookieValue(value = "user", defaultValue = "-1") String loginToken, Model model, @PathVariable String registerToken, HttpServletResponse response) {
        if (AuthenticationManager.isUserLoggedIn(loginToken)) {
            return "redirect:/";
        } else {
            if (AuthenticationManager.isRegisterTokenValid(registerToken)) {
                model.addAttribute("email", AuthenticationManager.getEmailForRegisterToken(registerToken));
                return "register-step-two";
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return "error";
            }
        }
    }
}
