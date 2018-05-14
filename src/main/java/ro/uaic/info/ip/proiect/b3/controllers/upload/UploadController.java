package ro.uaic.info.ip.proiect.b3.controllers.upload;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.uaic.info.ip.proiect.b3.authentication.AuthenticationManager;
import ro.uaic.info.ip.proiect.b3.database.Database;
import ro.uaic.info.ip.proiect.b3.storage.FileFormatException;
import ro.uaic.info.ip.proiect.b3.storage.StorageException;
import ro.uaic.info.ip.proiect.b3.storage.StorageService;

import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Aceasta clasa reprezinta un controller pentru metoda POST a paginii de upload a unui fisier.
 */
@Controller
public class UploadController {
    private final StorageService storageService;

    @Autowired
    public UploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody String upload(@RequestParam("file") MultipartFile file,
                                       @RequestParam("titluMaterie") String titluMaterie,
                                       @RequestParam("numeTema") String numeTema,
                                       @RequestParam("exercitiu") String exercitiu,
                                       HttpServletResponse response, @CookieValue(value = "user", defaultValue = "-1") String loginToken) {
        if (AuthenticationManager.isUserLoggedIn(loginToken)) {
            String username = AuthenticationManager.getUsernameLoggedIn(loginToken);

            try {
                Connection dbConnection = Database.getInstance().getConnection();
                ResultSet resultSet = Database.getInstance().selectQuery(dbConnection,
                        "SELECT id,extensie_fisier FROM teme JOIN materii ON teme.id_materie = materii.id WHERE materii.titlu_materie = ? AND teme.nume_tema = ? AND ? BETWEEN 1 AND teme.nr_exercitii",titluMaterie,numeTema,exercitiu);

                String idTema = resultSet.getString(1);

                String expectedExtension = resultSet.getString(2);

                File convertedFile = new File(file.getOriginalFilename());
                file.transferTo(convertedFile);

                String uploadedFileExtension = new Tika().detect(convertedFile);

                if(!expectedExtension.equals(uploadedFileExtension)) {
                    throw new FileFormatException("The expected file format and your file format doesn't match.");
                }

                storageService.store(username, file);

                String uploadedFilename = StringUtils.cleanPath(file.getOriginalFilename());

                Database.getInstance().updateOperation("INSERT INTO teme_incarcate (username_student,id_tema,nume_fisier) VALUES (?,?,?)",username,idTema,uploadedFilename);


            } catch (StorageException | SQLException | FileFormatException | IOException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return "error=" + e.getMessage();
            }

            return "success";
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "error=unauthorized";
        }
    }
}
