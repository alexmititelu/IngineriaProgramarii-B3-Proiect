package ro.uaic.info.ip.proiect.b3.controllers.testing;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UploadTestingController {
    @GetMapping("/upload-testing")
    public String templateTesting(Model model) {
        return "./testing/upload-testing";
    }
}
