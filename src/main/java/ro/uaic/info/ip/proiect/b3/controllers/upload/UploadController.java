package ro.uaic.info.ip.proiect.b3.controllers.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.uaic.info.ip.proiect.b3.authentication.AuthenticationManager;
import ro.uaic.info.ip.proiect.b3.storage.StorageException;
import ro.uaic.info.ip.proiect.b3.storage.StorageService;

import javax.servlet.http.HttpServletResponse;

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
    public @ResponseBody String upload(@RequestParam("file") MultipartFile file, HttpServletResponse response, @CookieValue(value = "user", defaultValue = "-1") String loginToken) {
        if (AuthenticationManager.isUserLoggedIn(loginToken)) {
            String username = AuthenticationManager.getUsernameLoggedIn(loginToken);

            try {
                storageService.store(username, file);
            } catch (StorageException e) {
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
