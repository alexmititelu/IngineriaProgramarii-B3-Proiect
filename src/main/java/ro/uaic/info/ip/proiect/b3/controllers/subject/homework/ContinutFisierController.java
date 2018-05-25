package ro.uaic.info.ip.proiect.b3.controllers.subject.homework;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.uaic.info.ip.proiect.b3.controllers.subject.homework.objects.LinieContinutFisier;
import ro.uaic.info.ip.proiect.b3.database.objects.comentariuprofesor.ComentariuProfesor;
import ro.uaic.info.ip.proiect.b3.database.objects.cont.Cont;
import ro.uaic.info.ip.proiect.b3.database.objects.materie.Materie;
import ro.uaic.info.ip.proiect.b3.database.objects.tema.Tema;
import ro.uaic.info.ip.proiect.b3.database.objects.temaincarcata.TemaIncarcata;
import ro.uaic.info.ip.proiect.b3.permissions.PermissionManager;
import ro.uaic.info.ip.proiect.b3.storage.StorageProperties;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class ContinutFisierController {
    private final static Logger logger = Logger.getLogger(ContinutFisierController.class);

    @RequestMapping(value = "/materii/{numeMaterie}/{numeTema}/continut_fisier", method = RequestMethod.POST)
    public @ResponseBody
    ArrayList<LinieContinutFisier> getContinutFisier(
            @CookieValue(value = "user", defaultValue = "-1") String loginToken,
            @PathVariable("numeMaterie") String numeMaterie,
            @PathVariable("numeTema") String numeTema,
            @RequestParam("nrExercitiu") int nrExercitiu,
            @RequestParam("username") String username) {
        try {
            Cont cont = null;

            if (PermissionManager.isUserAllowedToModifySubject(numeMaterie, loginToken)) {
                cont = Cont.getByUsername(username);
            } else if (PermissionManager.isLoggedUserStudent(loginToken)) {
                cont = Cont.getByLoginToken(loginToken);
            }

            if (cont == null) {
                return null;
            }

            Materie materie = Materie.getByTitlu(numeMaterie);
            if (materie == null) return null;

            Tema tema = Tema.getByMaterieIdAndNumeTema(materie.getId(), numeTema);
            if (tema == null) return null;

            TemaIncarcata temaIncarcata = TemaIncarcata.get(cont.getId(), tema.getId(), nrExercitiu);


            ArrayList<ComentariuProfesor> comentarii = ComentariuProfesor.getByIdTemaIncarcataAndNrExercitiu(temaIncarcata.getId(), nrExercitiu);


            ArrayList<LinieContinutFisier> liniiContinutFisier = new ArrayList<>();


            if (temaIncarcata != null) {
                File fisierTema = new File(new StorageProperties().getLocation() + temaIncarcata.getNumeFisierTema());

                try (BufferedReader br = new BufferedReader(new FileReader(fisierTema))) {
                    String line;
                    int indexLinie = 1;
                    while ((line = br.readLine()) != null) {
                        LinieContinutFisier linieContinutFisier = new LinieContinutFisier();
                        linieContinutFisier.setLineValue(line);

                        for (ComentariuProfesor comentariu : comentarii) {
                            if (comentariu.getStartRow() == indexLinie) {
                                linieContinutFisier.setComment(comentariu.getComentariu());
                                linieContinutFisier.setCommentedLines(comentariu.getEndRow() - indexLinie + 1);
                                comentarii.remove(comentariu);
                                break;
                            }
                        }
                        indexLinie++;
                    }
                }
            }


            return liniiContinutFisier;
        } catch (FileNotFoundException e) {
            return null;
        } catch (SQLException | IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
