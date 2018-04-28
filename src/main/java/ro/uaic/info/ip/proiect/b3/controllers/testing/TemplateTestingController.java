package ro.uaic.info.ip.proiect.b3.controllers.testing;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TemplateTestingController {
    @GetMapping("/template_testing")
    public String templateTesting() {
        if (System.currentTimeMillis() % 2 == 0) return "./testing/even";
        else return "./testing/odd";
    }
}

