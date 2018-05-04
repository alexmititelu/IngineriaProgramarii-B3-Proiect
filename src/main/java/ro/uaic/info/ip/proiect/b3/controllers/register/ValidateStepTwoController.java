package ro.uaic.info.ip.proiect.b3.controllers.register;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * Aceasta clasa reprezinta un controller pentru metoda POST a paginii de inregistrare din al doilea pas. (Vezi utilis/autentificare/ pentru detalii despre sistemul de autentificare)
 */
@Controller
public class ValidateStepTwoController {
    /**
     * Metoda returneaza body-ul raspunsului HTTP pentru o cerere de inregistrare pentru al doilea pas.
     *
     * 1. Se valideaza tokenul de inregistrare. (Existenta sa in tabela register_links)
     * 2. Se valideaza emailul primit. (Existenta sa in tabela studenti)
     * 3. Se valideaza username-ul. (Acesta trebuie sa contina doar caractere alfanumerice si caracterul '.' (punct) maxim o singura data si sa aiba o lungime de
     *    cel putin 6 caractere si cel mult 30)
     * 4. Se valideaza parola. (Aceasta trebuie sa contina doar caractere alfanumerice si sa aiba o lungime de minim 8)
     * 5. Se hashuieste parola folosing sha256. -- final String hashedPassword = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString()
     * 6. Se insereaza datele in tabela conturi. (username-ul, emailul si parola hashuita)
     * 7. In cazul in care inserarea s-a realizat cu succes se va sterget tokenul de inregistrare din baza de date.
     * 8. Se returneaza un mesaj pozitiv
     * In cazul in care una dintre validarile de la pasii 1-4 esueaza:
     * 1. Setarea raspunsului drept 400 Bad Request cu ajutorul parametrului response.  -- response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
     * 2. Trimiterea unui raspuns negativ.
     * @param email emailul trimis de catre utilizator
     * @param username usernameul trimis de catre utilizator
     * @param password parola trimisa de catre utilizator
     * @param registerToken tokenul de inregistrare
     * @param response un obiect java reprezentand raspunsul http; cu ajutorul acestuia se pot seta headere, se poate seta codul de status etc.
     * @return mesaj in functie de rezultatul validarii
     */
    @RequestMapping(value = "/register/{registerToken}", method = RequestMethod.POST)
    public @ResponseBody String register(
            @RequestParam("email") String email,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @PathVariable String registerToken,
            HttpServletResponse response) {
        return "";
    }
}