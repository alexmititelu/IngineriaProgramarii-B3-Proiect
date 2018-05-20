/*package ro.uaic.info.ip.proiect.b3.controllers.subject.homework;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.uaic.info.ip.proiect.b3.database.Database;
import ro.uaic.info.ip.proiect.b3.permissions.PermissionManager;
import ro.uaic.info.ip.proiect.b3.plagiarism.PlagiarismDetector;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

@Controller
public class UploadHomeworkController {
    @RequestMapping(value = "/materii/{numeMaterie}/{numeTema}/upload", method = RequestMethod.POST)
    public String uploadHomework(
            @CookieValue(value = "user", defaultValue = "-1") String loginToken,
            @PathVariable("numeMaterie") String numeMaterie,
            @PathVariable("numeTema") String numeTema,
            @RequestParam ("user") String userName,
            @RequestParam("file") MultipartFile file,
            @RequestParam("nrExercitiu") int nrExercitiu) throws SQLException, IOException {
        if (PermissionManager.isLoggedUserStudent(loginToken) && PermissionManager.isUserLoggedIn(loginToken)) {
            Connection connection = null;
            connection = Database.getInstance().getConnection();
            ResultSet AlreadyIn=null;
            AlreadyIn=Database.getInstance().selectQuery(connection,"SELECT * from teme_incarcate where "
                    + "id_cont in (select id from conturi where username = ?)" + " and "
                    + "id_tema in (select id from teme where nume_tema= ?)" + " and "
                    + "nr_exercitiu =?",userName,numeTema,Integer.toString(nrExercitiu));
            if (!AlreadyIn.next()) return "Tema deja uploadata";
            byte[] data=file.getBytes();

            String ext = "dat";
            byte[] array = new byte[7]; // length is bounded by 7
            new Random().nextBytes(array);
            String generatedString = new String(array, Charset.forName("UTF-8"));
            String fileName = String.format("%s.%s",generatedString, ext);

            OutputStream out = new FileOutputStream(new File("\\materii\\{numeMaterie}\\{numeTema}\\upload\\"+fileName));
            out.write(data);
            out.close();
            Database.getInstance().updateOperation("INSERT into teme_incarcate (id_cont,id_tema,nr_exercitiu,nume_fisier_tema) "
                            + "VALUES (select id from conturi where username = ?),(select id from teme where nume_tema=? and id_materie in (select id_materie from materii where titlu = ?)),?,?)"
                    ,userName,numeTema,numeMaterie,Integer.toString(nrExercitiu),fileName);
            new PlagiarismDetector().update(numeMaterie, numeTema);
            return "valid";
        }
        else return "Upload nereusit";
    }
}
*/
