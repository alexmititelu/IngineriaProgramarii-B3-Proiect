package ro.uaic.info.ip.proiect.b3.backgroundjobs;

import ro.uaic.info.ip.proiect.b3.database.Database;

import java.sql.SQLException;

public class ConturiConectateJob implements Runnable {
    public void run() {
        while (true) {
            try {
                Database.getInstance().updateOperation("DELETE FROM conturi_conectate WHERE creation_time <= now() - INTERVAL 12 HOUR");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(1000 * 1800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
