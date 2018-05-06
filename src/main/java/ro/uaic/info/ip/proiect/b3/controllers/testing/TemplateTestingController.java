package ro.uaic.info.ip.proiect.b3.controllers.testing;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Aceasta clasa este un exemplu pentru lucrul cu templateuri in Spring Boot.
 */

@Controller
public class TemplateTestingController {
    @GetMapping("/template-testing")
    public String templateTesting(Model model) {
        if (System.currentTimeMillis() % 2 == 0)  {
            model.addAttribute("testing", "I'm a test variable");
            return "./testing/even";
        }
        else {
            model.addAttribute("testing", "I'm the other test variable");
            return "./testing/odd";
        }
    }
}

