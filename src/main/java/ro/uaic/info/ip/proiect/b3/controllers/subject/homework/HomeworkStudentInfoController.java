package ro.uaic.info.ip.proiect.b3.controllers.subject.homework;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ro.uaic.info.ip.proiect.b3.clientinfo.ExercitiuInfoStudent;
import ro.uaic.info.ip.proiect.b3.database.Database;
import ro.uaic.info.ip.proiect.b3.permissions.PermissionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class HomeworkStudentInfoController {
    @RequestMapping(value = "/materii/{numeMaterie}/{numeTema}/student_info", method = RequestMethod.GET)
    public ArrayList<ExercitiuInfoStudent> getStudentInfo(
            @CookieValue(value = "user", defaultValue = "-1") String loginToken,
            @PathVariable("numeMaterie") String numeMaterie,
            @PathVariable("numeTema") String numeTema) throws SQLException {

        if (PermissionManager.isUserLoggedIn(loginToken)) {
            return null;
        }
//        if (!PermissionManager.isLoggedUserStudent(loginToken)) {
//            return null;
//        }
        Connection connection = Database.getInstance().getConnection();
        ArrayList<ExercitiuInfoStudent> exercitiuInfoStudents = new ArrayList<>();
        ResultSet exercitii = Database.getInstance().selectQuery(connection,
                "SELECT teme.nume_tema ,teme.enunt , tema_exercitiu_extensie.extensie_acceptata , teme_incarcate.nota from teme" +
                        "join tema_exercitiu_extensie on tema_exercitiu_extensie.id_tema = teme.id" +
                        "join teme_incarcate on teme_incarcate.id_tema = teme.id" +
                        "join materii on materii.id = teme.id_materie" +
                        "WHERE materii.titlu=? AND teme.nume_tema=?",numeMaterie,numeTema);


        String nume;
        String enunt;
        String extensie;
        boolean isUploaded;
        String nota;

        while (exercitii.next()) {
            nume = exercitii.getString(1);
            enunt = exercitii.getString(2);
            extensie=exercitii.getString(3);
            isUploaded = true;
            nota = exercitii.getString(4);
            exercitiuInfoStudents.add(new ExercitiuInfoStudent(nume,enunt,extensie,isUploaded,nota));
        }

        return exercitiuInfoStudents;
    }
}
