package ro.uaic.info.ip.proiect.b3.controllers.upload;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Aceasta clasa reprezinta un controller pentru metoda POST a paginii de upload a unui fisier.
 */
@Controller
public class UploadController {
    /**
     * Vezi http://www.mkyong.com/spring-boot/spring-boot-file-upload-example/
     *
     * @param file
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        return "";
    }
}
