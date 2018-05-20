package ro.uaic.info.ip.proiect.b3.controllers.subject.homework;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.uaic.info.ip.proiect.b3.database.objects.cont.Cont;
import ro.uaic.info.ip.proiect.b3.database.objects.contconectat.ContConectat;
import ro.uaic.info.ip.proiect.b3.database.objects.didactic.Didactic;
import ro.uaic.info.ip.proiect.b3.database.objects.didactic.exceptions.DidacticException;
import ro.uaic.info.ip.proiect.b3.database.objects.materie.Materie;

import javax.validation.constraints.Null;
import java.io.*;
import java.sql.SQLException;

@Controller
public class ContinutFisierController {
    @RequestMapping(value = "/materii/{numeMaterie}/{numeTema}/continut_fisier", method = RequestMethod.POST)
    public String[] getContinutFisier(
            @CookieValue(value = "user", defaultValue = "-1") String loginToken,
            @RequestParam("username") String usernameToGetHomeworkFor,
            @RequestParam("nrExercitiu") int nrExercitiu,
            @PathVariable("numeMaterie") String numeMaterie,
            @PathVariable("numeTema") String numeTema) throws SQLException, DidacticException {

        String[] result = new String[1024];
        int lineNo = 0;

        try {
            File file = new File("Teme/Tema" + nrExercitiu + "-" + numeMaterie + "/" + numeTema + "/continut_fisier");//Introduceti aici pathul bun
            Cont cont = Cont.getByLoginToken(loginToken);

            if (cont.getUsername().compareTo(usernameToGetHomeworkFor) != 0 && cont.getPermission() == 1) {
                return null;
            }

            if (cont.getPermission() == 2) {
                if (Didactic.getByIdMaterieAndIdProfesor(Materie.getByTitlu(numeMaterie).getId(), cont.getId()) == null) {
                    return null;
                }
            }
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;
            while ((line = br.readLine()) != null) {
                result[lineNo] = line;
                lineNo++;
            }
        }
            catch (NullPointerException e){
            return null;
        }
         catch (IOException e) {
            return null;
        }

        return result;
    }
}
