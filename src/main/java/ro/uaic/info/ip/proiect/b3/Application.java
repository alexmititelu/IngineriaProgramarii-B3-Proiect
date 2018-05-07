package ro.uaic.info.ip.proiect.b3;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import ro.uaic.info.ip.proiect.b3.backgroundjobs.ConturiConectateJob;
import ro.uaic.info.ip.proiect.b3.backgroundjobs.RegisterLinksJob;
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
    CommandLineRunner initBackgroundJobs()  {
        return (args) -> {
            Thread registerLinksJob = new Thread(new RegisterLinksJob());
            registerLinksJob.setDaemon(true);
            registerLinksJob.start();

            Thread conturiConectateJob = new Thread(new ConturiConectateJob());
            conturiConectateJob.setDaemon(true);
            conturiConectateJob.start();
        };
    }
}

