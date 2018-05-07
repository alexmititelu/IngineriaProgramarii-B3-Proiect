package ro.uaic.info.ip.proiect.b3.controllers.testing;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Aceasta clasa este un exemplu pentru operatul cu cookieuri in Spring Boot.
 */

@Controller
public class CookieTestingController {
    @RequestMapping(value = "/cookie-testing")
    public String cookieTesting(@CookieValue(value = "hitCounter", defaultValue = "0") Long hitCounter, HttpServletResponse response, Model model) {
        hitCounter++;

        Cookie cookie = new Cookie("hitCounter", hitCounter.toString());
        cookie.setMaxAge(-1);
        response.addCookie(cookie);

        model.addAttribute("hitCounter", hitCounter.toString());
        return "./testing/cookie_testing";
    }
}
