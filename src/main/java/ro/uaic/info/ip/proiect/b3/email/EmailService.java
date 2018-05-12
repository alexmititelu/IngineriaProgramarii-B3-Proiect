package ro.uaic.info.ip.proiect.b3.email;

import ro.uaic.info.ip.proiect.b3.configurations.EmailConfiguration;
import ro.uaic.info.ip.proiect.b3.configurations.HostConfiguration;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailService {
    public static void sendRegistrationMail(String toEmail, String registerToken) {
        EmailConfiguration emailConfiguration = new EmailConfiguration();
        HostConfiguration hostConfiguration = new HostConfiguration();

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
            message.setText("Hello Student,"
                    + "\n\n You can create your account here: " + hostConfiguration.getName() + "register/" +  registerToken);

            Transport.send(message);

            System.out.println("Done");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
