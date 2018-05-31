package ro.uaic.info.ip.proiect.b3.backgroundjobs;

import it.zielke.moji.MossException;
import org.apache.log4j.Logger;
import ro.uaic.info.ip.proiect.b3.plagiarism.PlagiarismDetector;

import java.io.IOException;
import java.sql.SQLException;

public class CheckPlagiarismJob implements Runnable {
    private final static Logger logger = Logger.getLogger(CheckPlagiarismJob.class);

    public void run() {
        while (true) {
            try {
                PlagiarismDetector.update();
            } catch (SQLException | IOException | MossException e) {
                logger.error(e.getMessage(), e);
            }

            try {
                Thread.sleep(1000 * 60 * 5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
