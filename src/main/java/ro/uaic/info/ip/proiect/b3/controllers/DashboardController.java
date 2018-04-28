package ro.uaic.info.ip.proiect.b3.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class DashboardController {
    public String dashboard(Model model, String userLoggedIn) {
        model.addAttribute("username", userLoggedIn);
        return "dashboard";
    }
}
