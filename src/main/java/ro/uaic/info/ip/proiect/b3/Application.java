package ro.uaic.info.ip.proiect.b3;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import ro.uaic.info.ip.proiect.b3.controllers.register.ValidateStepOneController;
import ro.uaic.info.ip.proiect.b3.storage.StorageProperties;
import ro.uaic.info.ip.proiect.b3.storage.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner initStorageServvice(StorageService storageService) {
        return (args) -> {
            storageService.init();
        };
    }

    @Bean
    CommandLineRunner initBackgroundJobs() {
        return (args) -> {
            new ValidateStepOneController().sendEmailForRegistration("mititelu.alex@yahoo.com","test");

            // Start daemon thread pentru tabela conturi_conectate
            // Start daemon thread pentru tabela register_links
        };
    }
}

