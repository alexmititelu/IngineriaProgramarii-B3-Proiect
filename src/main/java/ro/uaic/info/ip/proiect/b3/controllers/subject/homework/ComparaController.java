package ro.uaic.info.ip.proiect.b3.controllers.subject.homework;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.uaic.info.ip.proiect.b3.database.Database;
import ro.uaic.info.ip.proiect.b3.permissions.PermissionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
public class ComparaController {
    @RequestMapping(value = "/materii/{numeMaterie}/{numeTema}/compara", method = RequestMethod.GET)
    public String getComparaPage(
            @CookieValue(value = "user", defaultValue = "-1") String loginToken,
            @PathVariable("numeMaterie") String numeMaterie,
            @PathVariable("numeTema") String numeTema,
            @RequestParam("username1") String username1,
            @RequestParam("username2") String username2,
            @RequestParam("nrExercitiu") int nrExercitiu) {

        Connection connection = null;
        ResultSet user1;
        ResultSet user2;
        String nume1, prenume1 ,nume2, prenume2;

        try{
            if((!PermissionManager.isUserLoggedIn(loginToken))&&
                    (PermissionManager.isLoggedUserProfesor(loginToken)) &&
                    (PermissionManager.isUserAllowedToModifySubject(numeMaterie,loginToken))){

                connection = Database.getInstance().getConnection();

                user1 = Database.getInstance().selectQuery(connection, "select nume, prenume" +
                        "  from studenti s join conturi c on s.email = c.email " +
                        "where c.username = ?"
                        , username1 );
                while(user1.next()) {
                    nume1 = user1.getString(1);
                    prenume1 = user1.getString(2);
                }

                user2 = Database.getInstance().selectQuery(connection, "select nume, prenume" +
                                "  from studenti s join conturi c on s.email = c.email " +
                                "where c.username = ?"
                        , username2 );
                while(user2.next()) {
                    nume2 = user2.getString(1);
                    prenume2 = user2.getString(2);
                }

            }
            else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.getMessage();
            }
        }

        return "compara";
    }
}
