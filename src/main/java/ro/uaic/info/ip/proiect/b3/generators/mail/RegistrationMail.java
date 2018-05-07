package ro.uaic.info.ip.proiect.b3.generators.mail;

import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class RegistrationMail {
    static Email email;

    private static void build(String registrationToken, String toAdress) {
        email = EmailBuilder.startingBlank()
                .from("contact_elearning@yahoo.com")
                .to(toAdress)
                .withSubject("Registration link for elearning platform")
                .withPlainText("Hello, this is your registration link: localhost:80/register/"+registrationToken)
                .buildEmail();
    }

    private static Mailer getMailer() {
        Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.mail.yahoo.com",  465, "contact_elearning@yahoo.com", "proiectip")
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
//                .withProxy("socksproxy.host.com", 1080, "proxy user", "proxy password")
                .withSessionTimeout(10 * 1000)
                .clearEmailAddressCriteria() // turns off email validation
                .withProperty("mail.smtp.sendpartial", "true")
                .withDebugLogging(true)
                .buildMailer();
       return mailer;
    }

    public static void send(String registrationToken, String toAdress) {

//        build(registrationToken,toAdress);
//        getMailer().sendMail(email);
        MimeMessage mimeMessage = new JavaMailSenderImpl().createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {
            mimeMessageHelper.setTo("mititelu.alex@yahoo.com");
            mimeMessageHelper.setSubject("Account created");
            mimeMessageHelper.setText("Hello Alex");
            new JavaMailSenderImpl().send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
