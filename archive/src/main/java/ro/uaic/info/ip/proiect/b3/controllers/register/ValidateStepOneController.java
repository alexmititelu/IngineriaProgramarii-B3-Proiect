package ro.uaic.info.ip.proiect.b3.controllers.register;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.uaic.info.ip.proiect.b3.database.objects.Cont;
import ro.uaic.info.ip.proiect.b3.database.objects.RegisterLink;
import ro.uaic.info.ip.proiect.b3.database.objects.Student;
import ro.uaic.info.ip.proiect.b3.generators.TokenGenerator;

import javax.servlet.http.HttpServletResponse;

/**
 * Aceasta clasa reprezinta un controller pentru metoda POST a paginii de inregistrare din primul pas. (Vezi utilis/autentificare/ pentru detalii despre sistemul de autentificare)
 */
@Controller
public class ValidateStepOneController {
    /**
     * Metoda returneaza body-ul raspunsului HTTP pentru o cerere de inregistrare pentru primul pas.
     * .
     * In cazul in care numarul matricol introdus se regaseste in tabela studenti iar emailul asociat acestui numar matricol nu se regaseste in tabela conturi se vor face urmatorii pasi:
     * 1. Generarea unui token random de lungime 64 ce contine doar caractere alfanumerice unic. (Sa nu existe deja in tabela register_links)
     * 2. Introducerea tokenului si emailului asociat numarului matricol in tabela register_links.
     * 3. Trimiterea unui mail catre utilizator ce contine un mesaj de bun venit si linkul corespunzator pasului doi din procesul de autentificare. (www.host-name.ro/register/{generated-token})
     * 4. Se returneaza un mesaj pozitiv.
     * <p>
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
    String registerController(@RequestParam("nr_matricol") String nrMatricol, HttpServletResponse response) {
        Student student = Student.get(nrMatricol);

        if (student != null) {
            Cont cont = Cont.get(student.getEmail());

            if (cont == null) {
                String token = TokenGenerator.getToken(64, "register_links");
                RegisterLink.add(token, student.getEmail());
                // RegistrationMail.send(token,email);
                return "A fost trimis un email de confirmare pe adresa asociata acestui numar matricol.";

            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return "Exista deja un cont creat pentru acest numar matricol.";
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Numarul matricol nu este valid.";
        }
    }
}
