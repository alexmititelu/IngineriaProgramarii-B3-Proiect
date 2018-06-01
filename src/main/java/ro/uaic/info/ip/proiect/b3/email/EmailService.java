package ro.uaic.info.ip.proiect.b3.email;

import org.apache.log4j.Logger;
import ro.uaic.info.ip.proiect.b3.configurations.EmailConfiguration;
import ro.uaic.info.ip.proiect.b3.configurations.HostConfiguration;
import ro.uaic.info.ip.proiect.b3.database.objects.registerlink.RegisterLink;
import ro.uaic.info.ip.proiect.b3.database.objects.student.Student;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Properties;
import java.util.stream.Stream;

public class EmailService {
    private static final Logger logger = Logger.getLogger(EmailService.class);

    public static void sendRegistrationMail(String toEmail, String registerToken) throws SQLException {
        EmailConfiguration emailConfiguration = new EmailConfiguration();

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(emailConfiguration.getAddress(), emailConfiguration.getPassword());
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailConfiguration.getAddress()));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toEmail));
            message.setSubject("UAIC E-Learning Registration Link");
            String emailTemplate = getTemplateFor(registerToken);
            message.setContent(emailTemplate, "text/html; charset=utf-8");
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getTemplateFor(String registerToken) throws SQLException {
        RegisterLink registerLink = RegisterLink.getByToken(registerToken);
        Student student = Student.getByEmail(registerLink.getEmail());
        HostConfiguration hostConfiguration = new HostConfiguration();
        StringBuilder sb;
        String response;

        try {
            try (BufferedReader br = new BufferedReader(new FileReader(".\\src\\main\\resources\\static\\email-template.html"))) {
                sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }

                response = sb.toString();
                response = response.replace("${register-link}", hostConfiguration.getName() + "/" + registerToken);
                response = response.replace("${nume-student}", student.getPrenume());
                response = response.replace("${current-host}", hostConfiguration.getName());
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            response = "Hello Student,"
                    + "\n\n You can create your account here: " + hostConfiguration.getName() + "register/" + registerToken;
        }

        return response;
    }
}
