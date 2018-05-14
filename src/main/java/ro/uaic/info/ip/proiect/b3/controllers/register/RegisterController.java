package ro.uaic.info.ip.proiect.b3.controllers.register;

import com.google.common.hash.Hashing;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.uaic.info.ip.proiect.b3.authentication.AuthenticationManager;
import ro.uaic.info.ip.proiect.b3.authentication.RegistrationValidator;
import ro.uaic.info.ip.proiect.b3.database.Database;
import ro.uaic.info.ip.proiect.b3.database.objects.Cont;
import ro.uaic.info.ip.proiect.b3.database.objects.RegisterLink;
import ro.uaic.info.ip.proiect.b3.database.objects.Student;
import ro.uaic.info.ip.proiect.b3.email.EmailService;
import ro.uaic.info.ip.proiect.b3.generators.TokenGenerator;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

@Controller
public class RegisterController {
    /**
     * Metoda registerPageStepTwo returneaza numele paginii ce trebuie trimise catre utilizator pentru pathul /register/{register-token} unde {register-token} reprezinta
     * un string generat la cererea unui utilizator ce doreste sa se inregistreze.
     *
     * In cazul in care utilizatorul care face cererea este deja autentificat acesta va fi redirectionat catre pagina de dashboard.
     *
     * In cazul in care utilizatorul nu este autentificat:
     * 1. Se valideaza existenta tokenului in baza de date (tabelul register_links)
     * 2. Se trimite pagina ce corespunde pasului al doilea de inregistrare. Pagina din pasul al doilea de inregistrare reprezinta un view ce
     * contine variabila 'email'. Aceasta variabila trebuie setata egala cu emailul utilizatorului care detine tokenul. Emailul utilizatorului
     * se regaseste in baza de date in acelasi tabel, register_links.
     * In cazul in care validarea de la pasul 1 esueaza raspunsul primit va fi 400 Bad Request iar pagina returnata va fi "error".
     *
     * @param loginToken    reprezinta valoare cookieului 'user' sau -1 in cazul in care cookieul cu acest nume nu exista
     * @param model         acest parametru este folosit pentru a modela view-ul returnat catre utilizator
     *                      prin intermediul modelului putem seta variabile in pagina trimisa catre utilizator
     *                      vezi @DashboardController si resursa dashboard.html din fisierul resources/templates pentru exemplu
     * @param registerToken reprezinta un string cu valoarea {register-token} din path
     * @return numele paginii returnate
     */
    @RequestMapping(value = "/register/{registerToken}", method = RequestMethod.GET)
    public String registerPageStepTwo(
            @CookieValue(value = "user", defaultValue = "-1") String loginToken,
            Model model,
            @PathVariable String registerToken,
            HttpServletResponse response) {
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

    /**
     * Metoda returneaza body-ul raspunsului HTTP pentru o cerere de inregistrare pentru primul pas.
     *
     * In cazul in care numarul matricol introdus se regaseste in tabela studenti iar emailul asociat acestui numar matricol nu se regaseste in tabela conturi se vor face urmatorii pasi:
     * 1. Generarea unui token random de lungime 64 ce contine doar caractere alfanumerice unic. (Sa nu existe deja in tabela register_links)
     * 2. Introducerea tokenului si emailului asociat numarului matricol in tabela register_links.
     * 3. Trimiterea unui mail catre utilizator ce contine un mesaj de bun venit si linkul corespunzator pasului doi din procesul de autentificare. (www.host-name.ro/register/{generated-token})
     * 4. Se returneaza un mesaj pozitiv.
     *
     * In cazul in care numarul matricol introdus nu se regaseste in tabela studenti sau emailul asociat acestui numar matricol se regaseste in tabela conturi se vor face urmatorii pasi:
     * 1. Setarea raspunsului drept 400 Bad Request cu ajutorul parametrului response.  -- response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
     * 2. Trimiterea unui raspuns negativ.
     *
     * @param nrMatricol numarul matricol al utilizatorului care a realizat cererea
     * @param response   un obiect java reprezentand raspunsul http; cu ajutorul acestuia se pot seta headere, se poate seta codul de status etc.
     * @return mesaj in functie de rezultatul validarii
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public @ResponseBody
    String registerController(
            @RequestParam("nr_matricol") String nrMatricol,
            HttpServletResponse response) {
        Student student = Student.getByNrMatricol(nrMatricol);

        if (student != null) {
            Cont cont = Cont.getByEmail(student.getEmail());

            if (cont == null) {
                String token = TokenGenerator.getToken(64, "register_links");
                RegisterLink.add(token, student.getEmail());
                EmailService.sendRegistrationMail(student.getEmail(), token);
                return "valid";

            } else {
                // response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return "Exista deja un cont creat pentru acest numar matricol.";
            }
        } else {
            // response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Numarul matricol nu este valid.";
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerPageStepOne() {
        return "register-step-one";
    }

    /**
     * Metoda returneaza body-ul raspunsului HTTP pentru o cerere de inregistrare pentru al doilea pas.
     * 1. Se valideaza tokenul de inregistrare. (Existenta sa in tabela register_links)
     * 2. Se valideaza emailul primit. (Existenta sa in tabela studenti si inexsistenta in tabela conturi)
     * 3. Se valideaza username-ul. (Acesta trebuie sa contina doar caractere alfanumerice si caracterul '.' (punct) maxim o singura data si sa aiba o lungime de
     * cel putin 6 caractere si cel mult 30)
     * 4. Se valideaza parola. (Aceasta trebuie sa contina doar caractere alfanumerice si sa aiba o lungime de minim 8)
     * 5. Se hashuieste parola folosing sha256. -- final String hashedPassword = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString()
     * 6. Se insereaza datele in tabela conturi. (username-ul, emailul si parola hashuita)
     * 7. In cazul in care inserarea s-a realizat cu succes se va sterget tokenul de inregistrare din baza de date.
     * 8. Se returneaza un mesaj pozitiv
     * In cazul in care una dintre validarile de la pasii 1-4 esueaza:
     * 1. Setarea raspunsului drept 400 Bad Request cu ajutorul parametrului response.  -- response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
     * 2. Trimiterea unui raspuns negativ.
     *
     * @param email         emailul trimis de catre utilizator
     * @param username      usernameul trimis de catre utilizator
     * @param password      parola trimisa de catre utilizator
     * @param registerToken tokenul de inregistrare
     * @param response      un obiect java reprezentand raspunsul http; cu ajutorul acestuia se pot seta headere, se poate seta codul de status etc.
     * @return mesaj in functie de rezultatul validarii
     */
    @RequestMapping(value = "/register/{registerToken}", method = RequestMethod.POST)
    public @ResponseBody
    String register(
            @RequestParam("email") String email,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @PathVariable String registerToken,
            HttpServletResponse response) {

        if (!AuthenticationManager.isRegisterTokenValid(registerToken)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Invalid token!";
        }

        RegistrationValidator registrationValidator = new RegistrationValidator();
        if (!registrationValidator.isEmailValid(email)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Email deja folosit!";
        }

        if (!registrationValidator.isUsernameValid(username)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Numele de utilizator este invalid!";
        }

        if (!registrationValidator.isPasswordRespectingConstraints(password)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Parola este invalida";
        }

        final String hashedPassword = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();

        try {
            Database.getInstance().updateOperation("INSERT INTO conturi (username, email, password) VALUES (?, ?, ?)", username, email, hashedPassword);
            Database.getInstance().updateOperation("DELETE FROM register_links WHERE TOKEN = ?", registerToken);
        } catch (SQLException e) {
            System.err.println("Ooops ... " + e.getMessage());

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "Eroare interna!";
        }

        return "valid";
    }
}
