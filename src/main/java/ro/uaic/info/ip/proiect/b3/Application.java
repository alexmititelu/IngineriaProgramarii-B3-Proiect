package ro.uaic.info.ip.proiect.b3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Application {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcome() {
        return "Greetings from B3!";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

