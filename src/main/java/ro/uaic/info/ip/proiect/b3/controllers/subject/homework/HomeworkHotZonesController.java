package ro.uaic.info.ip.proiect.b3.controllers.subject.homework;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.uaic.info.ip.proiect.b3.clientinfo.HotZone;
import ro.uaic.info.ip.proiect.b3.database.Database;
import ro.uaic.info.ip.proiect.b3.database.objects.materie.Materie;
import ro.uaic.info.ip.proiect.b3.database.objects.tema.Tema;
import ro.uaic.info.ip.proiect.b3.permissions.PermissionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class HomeworkHotZonesController {
    private final static Logger logger = Logger.getLogger(HomeworkController.class);

    @RequestMapping(value = "/materii/{numeMaterie}/{numeTema}/hot_zones", method = RequestMethod.POST)
    public @ResponseBody
    ArrayList<HotZone> getHotZones(
            @CookieValue(value = "user", defaultValue = "-1") String loginToken,
            @PathVariable("numeMaterie") String numeMaterie,
            @PathVariable("numeTema") String numeTema,
            @RequestParam("username1") String username1,
            @RequestParam("username2") String username2,
            @RequestParam("nrExercitiu") String nrExercitiu) {

        Connection connection = null;
        ResultSet hotZoneResultSet;
        ArrayList<HotZone> hotZones = new ArrayList<>();
        HotZone hotZone = new HotZone();

        try {
            if (PermissionManager.isUserAllowedToModifySubject(numeMaterie, loginToken)) {
                connection = Database.getInstance().getConnection();

                Materie materie = Materie.getByTitlu(numeMaterie);
                if (materie == null) return null;

                Tema tema = Tema.getByMaterieIdAndNumeTema(materie.getId(), numeTema);

                if (tema != null) {
                    hotZoneResultSet = Database.getInstance().selectQuery(connection, "select h.start_row_user1, h.end_row_user1," +
                            " h.start_row_user2, h.end_row_user2," +
                            " h.procent_plagiat from hot_zone h join plagiat p on h.id_plagiat = p.id " +
                            "where p.id_tema = ? " +
                            "and ((p.username1 = ? and p.username2 = ?) OR (p.username2 = ? and p.username1 = ?)) " +
                            "and p.nr_exercitiu = ?", Long.toString(tema.getId()), username1, username2, username1, username2, nrExercitiu);

                    while (hotZoneResultSet.next()) {
                        hotZone.setStudent1(new int[]{hotZoneResultSet.getInt(1), hotZoneResultSet.getInt(2)});
                        hotZone.setStudent2(new int[]{hotZoneResultSet.getInt(3), hotZoneResultSet.getInt(4)});
                        hotZone.setProcent(hotZoneResultSet.getInt(5));
                        hotZones.add(hotZone);
                    }
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }

        return hotZones;
    }
}
