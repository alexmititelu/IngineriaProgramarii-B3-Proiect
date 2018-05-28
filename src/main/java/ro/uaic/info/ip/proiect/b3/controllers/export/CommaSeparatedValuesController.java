package ro.uaic.info.ip.proiect.b3.controllers.export;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.uaic.info.ip.proiect.b3.configurations.ServerErrorMessages;
import ro.uaic.info.ip.proiect.b3.database.Database;
import ro.uaic.info.ip.proiect.b3.database.objects.comentariuprofesor.ComentariuProfesor;
import ro.uaic.info.ip.proiect.b3.database.objects.contconectat.ContConectat;
import ro.uaic.info.ip.proiect.b3.database.objects.materie.Materie;
import ro.uaic.info.ip.proiect.b3.database.objects.tema.Tema;
import ro.uaic.info.ip.proiect.b3.permissions.PermissionManager;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
public class CommaSeparatedValuesController {
    private final static Logger logger = Logger.getLogger(CommaSeparatedValuesController.class);

    @RequestMapping(value = "/materii/{numeMaterie}/{numeTema}/raport.csv", method = RequestMethod.GET)
    public @ResponseBody
    String getRaportCSV(@CookieValue(value = "user", defaultValue = "-1") String loginToken,
                        @PathVariable("numeMaterie") String numeMaterie,
                        @PathVariable("numeTema") String numeTema) {
        try {
            if (PermissionManager.isUserAllowedToModifySubject(numeMaterie, loginToken)) {

                Materie materie = Materie.getByTitlu(numeMaterie);
                if (materie == null) {
                    return "Materia specificata nu exista.";
                }

                Tema tema = Tema.getByMaterieIdAndNumeTema(materie.getId(), numeTema);
                if (tema == null) {
                    return "Tema specificata nu exista.";
                }

                StringBuilder csvReport = new StringBuilder();
                csvReport.append("Nume,Prenume,Nota tema\n");

                Connection connection = Database.getInstance().getConnection();

                PreparedStatement statementStudentiInscrisi = connection.prepareStatement(
                        "SELECT conturi.id,studenti.nume,studenti.prenume FROM " +
                                "studenti JOIN conturi ON studenti.email = conturi.email JOIN inscrieri ON inscrieri.id_cont = conturi.id " +
                                "WHERE inscrieri.id_materie = ?");

                statementStudentiInscrisi.setLong(1, materie.getId());

                ResultSet studentiInscrisi = statementStudentiInscrisi.executeQuery();

                while (studentiInscrisi.next()) {
                    Long idStudent = studentiInscrisi.getLong(1);
                    String numeStudent = studentiInscrisi.getString(2);
                    String prenumeStudent = studentiInscrisi.getString(3);

                    PreparedStatement sumNoteStudent = connection.prepareStatement("SELECT SUM(nota) FROM teme_incarcate WHERE id_cont = ? AND id_tema = ?");
                    sumNoteStudent.setLong(1,idStudent);
                    sumNoteStudent.setLong(2,tema.getId());

                    ResultSet resultSumNoteStudent = sumNoteStudent.executeQuery();
                    if(resultSumNoteStudent.next()) {
                        Integer sumaNote = resultSumNoteStudent.getInt(1);
                        Float medie = sumaNote.floatValue() / tema.getNrExercitii();
                        csvReport.append(numeStudent + "," + prenumeStudent + "," + medie + "\n");
                    }
                }

                connection.close();

                return csvReport.toString();
            } else {
                return "Incercare de delogare cand utilizatorul nu era logat!";
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return ServerErrorMessages.INTERNAL_ERROR_MESSAGE;
        }
    }
}
