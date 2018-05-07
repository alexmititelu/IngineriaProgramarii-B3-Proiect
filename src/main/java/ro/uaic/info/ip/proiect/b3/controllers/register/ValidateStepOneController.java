package ro.uaic.info.ip.proiect.b3.controllers.register;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.uaic.info.ip.proiect.b3.database.Database;
import ro.uaic.info.ip.proiect.b3.generators.TokenGenerator;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;


/**
 * Aceasta clasa reprezinta un controller pentru metoda POST a paginii de inregistrare din primul pas. (Vezi utilis/autentificare/ pentru detalii despre sistemul de autentificare)
 */
@Controller
public class ValidateStepOneController {
    /**
     * Metoda returneaza body-ul raspunsului HTTP pentru o cerere de inregistrare pentru primul pas.
     * .
     * In cazul in care numarul matricol introdus se regaseste in tabela studenti iar emailul asociat acestui numar matricol nu se regaseste in tabela conturi se vor face urmatorii pasi:
     * 1. Generarea unui token random de lungime 64 ce contine doar caractere alfanumerice unic. (Sa nu existe deja in tabela register_links)
     * 2. Introducerea tokenului si emailului asociat numarului matricol in tabela register_links.
     * 3. Trimiterea unui mail catre utilizator ce contine un mesaj de bun venit si linkul corespunzator pasului doi din procesul de autentificare. (www.host-name.ro/register/{generated-token})
     * 4. Se returneaza un mesaj pozitiv.
     * <p>
     * In cazul in care numarul matricol introdus nu se regaseste in tabela studenti sau emailul asociat acestui numar matricol se regaseste in tabela conturi se vor face urmatorii pasi:
     * 1. Setarea raspunsului drept 400 Bad Request cu ajutorul parametrului response.  -- response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
     * 2. Trimiterea unui raspuns negativ.
     *
     * @param nrMatricol numarul matricol al utilizatorului care a realizat cererea
     * @param response   un obiect java reprezentand raspunsul http; cu ajutorul acestuia se pot seta headere, se poate seta codul de status etc.
     * @return mesaj in functie de rezultatul validarii
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public @ResponseBody
    String register(@RequestParam("nr_matricol") String nrMatricol, HttpServletResponse response) {

        if (isNrMatricolValid(nrMatricol)) {
            String email = getEmailForNrMatricol(nrMatricol);
            if (!isEmailAlreadyUsed(email)) {
                String token = TokenGenerator.getToken(64);
                addRegisterLink(token, email);
                sendEmailForRegistration(email, token);
                return "A fost trimis un email de confirmare pe adresa asociata acestui numar matricol.";

            } else {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                return "Exista deja un cont creat pentru acest numar matricol.";
            }

        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Numarul matricol nu este valid.";
        }
    }


    private boolean isNrMatricolValid(String nrMatricol) {
        Connection dbConnection = null;
        boolean validation = false;
        try {
            dbConnection = Database.getInstance().getConnection();
            String query = "SELECT * FROM studenti WHERE nr_matricol LIKE ?";
            Statement queryStatement = dbConnection.prepareStatement(query);
            ((PreparedStatement) queryStatement).setString(1, nrMatricol);


            ResultSet resultSet = ((PreparedStatement) queryStatement).executeQuery();

            while (resultSet.next()) {
                validation = true;
                break;
            }

        } catch (Exception e) {
            System.out.println("[" + System.nanoTime() + "] " + e.getMessage());
        } finally {
            try {
                dbConnection.close();
            } catch (Exception e) {
                System.out.println("[" + System.nanoTime() + "] " + e.getMessage());
            }
        }
        return validation;
    }

    private String getEmailForNrMatricol(String nrMatricol) {
        Connection dbConnection = null;
        String email = null;
        try {
            dbConnection = Database.getInstance().getConnection();
            String query = "SELECT email FROM studenti WHERE nr_matricol LIKE ?";
            Statement queryStatement = dbConnection.prepareStatement(query);
            ((PreparedStatement) queryStatement).setString(1, nrMatricol);

            ResultSet resultSet = ((PreparedStatement) queryStatement).executeQuery();

            while (resultSet.next()) {
                email = resultSet.getString(1);
                break;
            }
        } catch (Exception e) {
            System.out.println("[" + System.nanoTime() + "] " + e.getMessage());
        } finally {
            try {
                dbConnection.close();
            } catch (Exception e) {
                System.out.println("[" + System.nanoTime() + "] " + e.getMessage());
            }
        }
        return email;
    }

    private boolean isEmailAlreadyUsed(String email) {
        boolean emailUsage = false;
        Connection dbConnection = null;
        try {
            dbConnection = Database.getInstance().getConnection();
            String query = "SELECT email FROM conturi WHERE email LIKE ?";
            Statement queryStatement = dbConnection.prepareStatement(query);
            ((PreparedStatement) queryStatement).setString(1, email);

            ResultSet resultSet = ((PreparedStatement) queryStatement).executeQuery();

            while (resultSet.next()) {
                emailUsage = true;
                break;
            }

        } catch (Exception e) {
            System.out.println("[" + System.nanoTime() + "] " + e.getMessage());
        } finally {
            try {
                dbConnection.close();
            } catch (Exception e) {
                System.out.println("[" + System.nanoTime() + "] " + e.getMessage());
            }
        }
        return emailUsage;
    }

    private void addRegisterLink(String token, String email) {
        Connection dbConnection = null;
        try {
            dbConnection = Database.getInstance().getConnection();
            String query = "INSERT INTO REGISTER_LINKS (token,email) VALUES (?,?)";
            Statement queryStatement = dbConnection.prepareStatement(query);
            ((PreparedStatement) queryStatement).setString(1, token);
            ((PreparedStatement) queryStatement).setString(2, email);
            ResultSet resultSet = ((PreparedStatement) queryStatement).executeQuery();
        } catch (Exception e) {
            System.out.println("[" + System.nanoTime() + "] " + e.getMessage());
        } finally {
            try {
                dbConnection.close();
            } catch (Exception e) {
                System.out.println("[" + System.nanoTime() + "] " + e.getMessage());
            }
        }
    }

    public void sendEmailForRegistration(String email, String token) {
        String to = email;
        String from = "contact_elearning@yahoo.com";

        String host = "localhost";
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port","8443");
        Session session = Session.getDefaultInstance(properties);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Register to e-learning platform");
            message.setText("Click here to continue your registration process: localhost:8080/register/" + token);
            Transport.send(message);
            System.out.println("Sent register message successfully....");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
