package im2ag.m2pcci.maildemo.servlets;

/**
 * Programme de test, pour vérifier que l'envoi de mail fonctionne
 * en utilisant le protocole SMTP avec TLS
 *
 * @author Philippe GENOUD - Université Grenoble Alpes - Lab LIG-Steamer
 */
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailTLS {

    public static void main(String[] args) {

        final String username = "A_Completer";
        final String password = "A_Completer";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "152.77.82.189");     //smtps.univ-grenoble-alpes.fr
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("expediteur@toto.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("trcepteur@titi.org"));
            message.setSubject("Test envoi de mail");
            message.setText("Cher utilisateur"
                    + "\n\n si tu lit ce mail c'est que mon porgramme march !");

            Transport.send(message);

            System.out.println("Envoi du mail réussi !");
        } catch (MessagingException e) {
            System.out.println("Echec de l'envoi du mail");
            e.printStackTrace(System.out);
        }
    }
}
