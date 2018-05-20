package ro.uaic.info.ip.proiect.b3.controllers.subject.homework;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.uaic.info.ip.proiect.b3.plagiarism.PlagiarismDetector;

@Controller
public class UploadHomeworkController {
    @RequestMapping(value = "/materii/{numeMaterie}/{numeTema}/upload", method = RequestMethod.POST)
    public String uploadHomework(
            @CookieValue(value = "user", defaultValue = "-1") String loginToken,
            @PathVariable("numeMaterie") String numeMaterie,
            @PathVariable("numeTema") String numeTema,
            @RequestParam("file") MultipartFile file,
            @RequestParam("nrExercitiu") int nrExercitiu) {
        new PlagiarismDetector().update(numeMaterie, numeTema);
        return "valid";
    }
}
