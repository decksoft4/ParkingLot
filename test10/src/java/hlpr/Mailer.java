package hlpr;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.StringWriter;
import java.util.Properties;

public class Mailer {

    Properties prps;
    Session mses;

    public void mailer() {
        System.out.println("mailer helper class Creator invocation!");
        prps = new Properties();
        prps.setProperty("user", "deck@decksoft");
        prps.setProperty("mail.smtp.host", "decksoft");
        prps.setProperty("mail.smtp.port", "587");
        prps.setProperty("mail.smtp.auth", "false");
        prps.setProperty("mail.starttls.enable", "true");
        mses = Session.getInstance(prps);
    }

    public boolean sndMsg(String sbj, String bdy) {
        try {
            Message msg = new MimeMessage(mses);
            msg.setFrom(new InternetAddress(prps.getProperty("user")));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress("devteam@decksoft.ddns.net"));
            msg.setSubject(sbj);
            msg.setContent(bdy, "text/html");
            Transport.send(msg);
        } catch (MessagingException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public String prps2Html() {
        if (prps != null) {
            StringWriter s = new StringWriter();
            prps.forEach((k, v) -> {
                s.write(k + " : " + v + "<br/>\n");
            });
            return s.toString();
        } else {
            System.out.println("No properties at mailer!");
            return null;
        }
    }

}
