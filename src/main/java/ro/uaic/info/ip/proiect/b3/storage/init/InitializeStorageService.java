package ro.uaic.info.ip.proiect.b3.storage.init;

import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import ro.uaic.info.ip.proiect.b3.storage.StorageProperties;
import ro.uaic.info.ip.proiect.b3.storage.StorageService;

@Component
@EnableConfigurationProperties(StorageProperties.class)
public class InitializeStorageService implements CommandLineRunner {
    private static final Logger logger = Logger.getLogger(InitializeStorageService.class);
    private StorageService storageService;

    public InitializeStorageService(StorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    public void run(String... args) {
        storageService.init();
        logger.info("Initialized file system storage successfully");
    }
}
