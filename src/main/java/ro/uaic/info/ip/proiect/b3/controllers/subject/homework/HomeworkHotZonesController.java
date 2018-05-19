package ro.uaic.info.ip.proiect.b3.controllers.subject.homework;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.uaic.info.ip.proiect.b3.clientinfo.HotZone;

import ro.uaic.info.ip.proiect.b3.permissions.PermissionManager;
import ro.uaic.info.ip.proiect.b3.database.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

@Controller
public class HomeworkHotZonesController {
    private final static Logger logger = Logger.getLogger(HomeworkController.class);


    @RequestMapping(value = "/materii/{numeMaterie}/{numeTema}/hot_zones", method = RequestMethod.POST)
    public ArrayList<HotZone> getHotZones(
            @CookieValue(value = "user", defaultValue = "-1") String loginToken,
            @PathVariable("numeMaterie") String numeMaterie,
            @PathVariable("numeTema") String numeTema,
            @RequestParam("username1") String username1,
            @RequestParam("username2") String username2,
            @RequestParam("nrExercitiu") String nrExercitiu) {

        Connection connection = null;
        ResultSet hotZone = null;
        ArrayList<HotZone> hotZones = new ArrayList();
        HotZone auxiliarHotZone = null;
            try {
                if (PermissionManager.isLoggedUserProfesor(loginToken) &&
                        PermissionManager.isUserAllowedToModifySubject(numeMaterie, loginToken) &&
                        PermissionManager.isRegisterTokenValid(loginToken)) {

                    connection = Database.getInstance().getConnection();
                    hotZone = Database.getInstance().selectQuery(connection, "select h.start_row_user1, h.end_row_user1," +
                                                                            " h.start_row_user2, h.end_row_user2," +
                                                                            " h.procent_plagiat from hot_zone h join plagiat p on h.id_plagiat = p.id " +
                                                                            "where p.id_tema = ? " +
                                                                            "and p.username1 = ? and p.username2 = ? " +
                                                                            "and p.nr_exercitiu = ? ",numeTema, username1, username2, nrExercitiu);
                    if( !hotZone.next() )
                    {
                        throw new SQLException("nu exista cei 2 studenti, care au copiat la respectiva tema, la respectivul exercitiu");
                    }
                    else
                    {
                        auxiliarHotZone.setStudent1(new int[]{ hotZone.getInt(1), hotZone.getInt(2) } );
                        auxiliarHotZone.setStudent2(new int[]{ hotZone.getInt(3), hotZone.getInt(4) } );
                        auxiliarHotZone.setProcent( hotZone.getInt(5) );
                        hotZones.add(auxiliarHotZone);
                    }

                    while ( hotZone.next() )
                    {
                        auxiliarHotZone.setStudent1(new int[]{ hotZone.getInt(1), hotZone.getInt(2) } );
                        auxiliarHotZone.setStudent2(new int[]{ hotZone.getInt(3), hotZone.getInt(4) } );
                        auxiliarHotZone.setProcent( hotZone.getInt(5) );
                        hotZones.add(auxiliarHotZone);
                    }

                    connection.close();
                } else {
                    throw new Exception( "Utilizatorul nu este logat sau nu are permisiunile necesare!" );
                }
            }
            catch (SQLException e)
            {
                logger.error(e.getMessage(), e);
                return  new ArrayList<>();
            }
            catch (Exception e)
            {
                logger.error(e.getMessage(), e);
                return  new ArrayList<>();
            }
        return hotZones;
    }
}
