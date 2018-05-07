package ro.uaic.info.ip.proiect.b3.backgroundjobs;

import ro.uaic.info.ip.proiect.b3.database.Database;

import java.sql.SQLException;


public class RegisterLinksJob implements Runnable {
    public void run() {
        while (true) {
            try {
                Database.getInstance().updateOperation("DELETE FROM register_links WHERE creation_time <= now() - INTERVAL 1 HOUR");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(1000 * 60 * 30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
