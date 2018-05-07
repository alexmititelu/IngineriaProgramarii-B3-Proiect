package ro.uaic.info.ip.proiect.b3.backgroundjobs;

import ro.uaic.info.ip.proiect.b3.database.Database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ConturiConectateJob implements Runnable {
    public void run() {
        Connection con = null;

        while (true) {
            try {
                con = (Connection) Database.getInstance().getConnection();
                Statement stmt = con.createStatement();
                stmt.executeUpdate("DELETE FROM conturi_conectate WHERE creation_time <= now() - INTERVAL 1 DAY");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            try {
                Thread.sleep(1000 * 1800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
