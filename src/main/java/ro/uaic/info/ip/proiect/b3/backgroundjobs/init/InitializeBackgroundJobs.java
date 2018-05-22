package ro.uaic.info.ip.proiect.b3.backgroundjobs.init;

import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ro.uaic.info.ip.proiect.b3.backgroundjobs.ConturiConectateJob;
import ro.uaic.info.ip.proiect.b3.backgroundjobs.RegisterLinksJob;

@Component
public class InitializeBackgroundJobs implements CommandLineRunner {
    private static final Logger logger = Logger.getLogger(InitializeBackgroundJobs.class);

    @Override
    public void run(String... args) {
        Thread registerLinksJob = new Thread(new RegisterLinksJob());
        registerLinksJob.setDaemon(true);
        registerLinksJob.start();
        logger.info("Started register links background job successfully");

        Thread conturiConectateJob = new Thread(new ConturiConectateJob());
        conturiConectateJob.setDaemon(true);
        conturiConectateJob.start();
        logger.info("Started conturi conectate background job successfully");

        /*
        Thread checkPlagiarismJob = new Thread(new CheckPlagiarismJob());
        checkPlagiarismJob.setDaemon(true);
        checkPlagiarismJob.start();
        logger.info("Started check plagiarism background job successfully");
        */
    }
}
