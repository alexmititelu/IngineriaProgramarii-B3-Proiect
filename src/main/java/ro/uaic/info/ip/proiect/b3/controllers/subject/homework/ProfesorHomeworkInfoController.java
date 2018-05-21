package ro.uaic.info.ip.proiect.b3.controllers.subject.homework;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.uaic.info.ip.proiect.b3.clientinfo.InfoExercitiuProfesor;
import ro.uaic.info.ip.proiect.b3.clientinfo.StudentNenotat;
import ro.uaic.info.ip.proiect.b3.clientinfo.StudentNotat;
import ro.uaic.info.ip.proiect.b3.clientinfo.TemaPlagiata;
import ro.uaic.info.ip.proiect.b3.database.Database;
import ro.uaic.info.ip.proiect.b3.database.objects.materie.Materie;
import ro.uaic.info.ip.proiect.b3.database.objects.tema.Tema;
import ro.uaic.info.ip.proiect.b3.permissions.PermissionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class ProfesorHomeworkInfoController {
    private Logger logger = Logger.getLogger(ProfesorHomeworkInfoController.class);

    @RequestMapping(value = "/materii/{numeMaterie}/{numeTema}/profesor_info", method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<InfoExercitiuProfesor> getProfesorInfo(
            @CookieValue(value = "user", defaultValue = "-1") String loginToken,
            @PathVariable("numeMaterie") String numeMaterie,
            @PathVariable("numeTema") String numeTema) {

        try {
            if (!PermissionManager.isUserLoggedIn(loginToken) ||
                    !PermissionManager.isLoggedUserProfesor(loginToken) ||
                    !PermissionManager.isUserAllowedToModifySubject(numeMaterie, loginToken)) {
                return null;
            }

            Materie materie = Materie.getByTitlu(numeMaterie);
            Tema tema = null;

            if (materie != null) {
                tema = Tema.getByMaterieIdAndNumeTema(materie.getId(), numeTema);
            }

            if (tema == null) {
                return null;
            }

            // aici toate cerintele sunt verificate, incepem sa construim obiectele de returnat
            // nume, enunt, extensie, studenti nenotati[], studenti notati[], temePlagiate[]

            ArrayList<StudentNotat> studentiNotati = new ArrayList<>();
            ArrayList<StudentNenotat> studentiNenotati = new ArrayList<>();

            Connection connection = Database.getInstance().getConnection();
            ResultSet studenti = Database.getInstance().selectQuery(connection, "SELECT conturi.username, " +
                    "        studenti.nume, " +
                    "        studenti.prenume, " +
                    "        teme_incarcate.data_incarcarii, " +
                    "        teme_incarcate.nota " +
                    "FROM studenti JOIN conturi ON studenti.email = conturi.email " +
                    "JOIN teme_incarcate ON conturi.id = teme_incarcate.id_cont " +
                    "JOIN teme ON teme_incarcate.id_tema = teme.id " +
                    "WHERE teme.id = ?", Long.toString(tema.getId()));

            String username, nume, prenume;
            Date data_creare;
            int nota;

            while (studenti.next()) {
                username = studenti.getString(1);
                nume = studenti.getString(2);
                prenume = studenti.getString(3);
                data_creare = studenti.getDate(4);
                nota = studenti.getInt(5);

                if (nota == 0) {
                    studentiNenotati.add(new StudentNenotat(username, nume, prenume, data_creare));
                } else {
                    studentiNotati.add(new StudentNotat(username, nume, prenume, data_creare, nota));
                }
            }

            ArrayList<TemaPlagiata> temePlagiate = new ArrayList<>();
            ResultSet teme = Database.getInstance().selectQuery(connection, "SELECT s1.nume," +
                    "        s1.prenume," +
                    "        plagiat.username1," +
                    "        s2.nume," +
                    "        s2.prenume," +
                    "        plagiat.username2," +
                    "        plagiat.procent_plagiat " +
                    "FROM teme   JOIN plagiat ON teme.id=plagiat.id_tema" +
                    "            JOIN conturi c1 on c1.username=plagiat.username1" +
                    "            JOIN conturi c2 on c2.username=plagiat.username2" +
                    "            JOIN studenti s1 ON s1.email=c1.email AND c1.username=plagiat.username1" +
                    "            JOIN studenti s2 ON s2.email=c2.email AND c2.username=plagiat.username2 " +
                    "WHERE teme.id = ?", Long.toString(tema.getId()));

            String username1, nume1, prenume1, username2, nume2, prenume2;
            Integer procent;

            while (teme.next()) {
                nume1 = teme.getString(1);
                prenume1 = teme.getString(2);
                username1 = teme.getString(3);
                nume2 = teme.getString(4);
                prenume2 = teme.getString(5);
                username2 = teme.getString(6);
                procent = teme.getInt(7);
                temePlagiate.add(new TemaPlagiata(username1, nume1, prenume1, username2, nume2, prenume2, procent));
            }

            ArrayList<InfoExercitiuProfesor> finalList = new ArrayList<>();
            ResultSet exercitii = Database.getInstance().selectQuery(connection, "SELECT  tema_exercitiu_extensie.nr_exercitiu," +
                    "      tema_exercitiu_extensie.enunt," +
                    "      tema_exercitiu_extensie.extensie_acceptata " +
                    "FROM tema_exercitiu_extensie JOIN teme_incarcate ON tema_exercitiu_extensie.id_tema=teme_incarcate.id_tema" +
                    "                             JOIN teme ON teme.id = tema_exercitiu_extensie.id_tema" +
                    "                             JOIN materii on materii.id = teme.id_materie " +
                    "WHERE materii.titlu=? AND teme.nume_tema=?", numeMaterie, numeTema);


            Integer nrExercitiu;
            String enunt;
            String extensie;
            while (exercitii.next()) {
                nrExercitiu = exercitii.getInt(1);
                enunt = exercitii.getString(2);
                extensie = exercitii.getString(3);
                finalList.add(new InfoExercitiuProfesor(Integer.toString(nrExercitiu), enunt, extensie, studentiNenotati, studentiNotati, temePlagiate));
            }

            return finalList;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
