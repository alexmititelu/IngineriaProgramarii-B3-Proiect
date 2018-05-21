package ro.uaic.info.ip.proiect.b3.controllers.subject.homework;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.uaic.info.ip.proiect.b3.configurations.ServerErrorMessages;
import ro.uaic.info.ip.proiect.b3.database.Database;
import ro.uaic.info.ip.proiect.b3.database.objects.cont.Cont;
import ro.uaic.info.ip.proiect.b3.database.objects.materie.Materie;
import ro.uaic.info.ip.proiect.b3.database.objects.tema.Tema;
import ro.uaic.info.ip.proiect.b3.database.objects.temaincarcata.TemaIncarcata;
import ro.uaic.info.ip.proiect.b3.generators.FileNameGenerator;
import ro.uaic.info.ip.proiect.b3.permissions.PermissionManager;
import ro.uaic.info.ip.proiect.b3.plagiarism.PlagiarismDetector;
import ro.uaic.info.ip.proiect.b3.storage.StorageProperties;
import ro.uaic.info.ip.proiect.b3.storage.filesystemstorage.FileSystemStorageService;

import java.sql.SQLException;
import java.util.Date;


@Controller
public class UploadHomeworkController {
    Logger logger = Logger.getLogger(UploadHomeworkController.class);

    @RequestMapping(value = "/materii/{numeMaterie}/{numeTema}/upload", method = RequestMethod.POST)
    public String uploadHomework(
            @CookieValue(value = "user", defaultValue = "-1") String loginToken,
            @PathVariable("numeMaterie") String numeMaterie,
            @PathVariable("numeTema") String numeTema,
            @RequestParam("file") MultipartFile file,
            @RequestParam("nrExercitiu") int nrExercitiu) {


        try {
            if (PermissionManager.isLoggedUserStudent(loginToken)) {

                Materie materie = Materie.getByTitlu(numeMaterie);

                if (materie == null) {
                    return "Materie invalida";
                }

                Tema tema = Tema.getByMaterieIdAndNumeTema(materie.getId(), numeTema);

                if(tema == null) {
                    return "Tema invalida";
                }

                TemaIncarcata temaIncarcata = TemaIncarcata.get(Cont.getByLoginToken(loginToken).getId(), tema.getId(), nrExercitiu);


                if (temaIncarcata != null) {
                    return "Solutie deja uploadata";
                }


                Date deadline = tema.getDeadline();

                if (new Date().after(deadline)) {
                    return "Deadline depasit";
                }

                /* verificam extensia.. */

                String fileName = FileNameGenerator.getNewFileName();

                new FileSystemStorageService(new StorageProperties()).store(fileName, file);

                Database.getInstance().updateOperation("INSERT into teme_incarcate (id_cont,id_tema,nr_exercitiu,nume_fisier_tema) "
                                + "VALUES (select id from conturi where username = ?),(select id from teme where nume_tema=? and id_materie in (select id_materie from materii where titlu = ?)),?,?)"
                        , Cont.getByLoginToken(loginToken).getUsername(), numeTema, numeMaterie, Integer.toString(nrExercitiu), fileName);

                return "valid";
            } else return ServerErrorMessages.UNAUTHORIZED_ACCESS_MESSAGE;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return ServerErrorMessages.INTERNAL_ERROR_MESSAGE;
        }
    }
}

