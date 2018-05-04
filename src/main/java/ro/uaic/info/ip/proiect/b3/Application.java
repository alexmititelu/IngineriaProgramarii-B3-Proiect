package ro.uaic.info.ip.proiect.b3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        // Start daemon thread pentru tabela conturi_conectate
        // Start daemon thread pentru tabela register_links
        SpringApplication.run(Application.class, args);
    }
}

