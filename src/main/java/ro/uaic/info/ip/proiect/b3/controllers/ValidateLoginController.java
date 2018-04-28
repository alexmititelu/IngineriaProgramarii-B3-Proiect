package ro.uaic.info.ip.proiect.b3.controllers;

import com.google.common.hash.Hashing;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.uaic.info.ip.proiect.b3.authentication.AuthenticationManager;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

@Controller
public class ValidateLoginController {
    @RequestMapping(value="/validateLogin", method=RequestMethod.POST)
    @ResponseBody
    public String validateLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletResponse response) {
        final String hashedPassword = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
        if (AuthenticationManager.isLoginDataValid(username, password)) {
            /*
                - generate a login token and add it to the conturi_conectate table together with the username
                - set a cookie user=token that lasts until user closes the browser
                - return a ok response for the client
             */
        } else {
            /*
                - return a wrong username / password response for the client
             */
        }

        return ""; // Remove this when method is fully implemented
    }
}
