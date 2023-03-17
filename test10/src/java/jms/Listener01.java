package jms;
import hlpr.Mailer;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSException;
import jakarta.jms.MapMessage;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import java.util.Enumeration;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationName", propertyValue = "jms/msgTopic"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Topic")
    }, mappedName = "jms/msgTopic")
public class Listener01 implements MessageListener {

    @Override
    public void onMessage(Message msg) {
        try {
            System.out.println("message has been received ...");
            if (msg instanceof MapMessage) {
                MapMessage cmsg = (MapMessage) msg;
                Mailer ml = new Mailer();
                ml.mailer();
                String bdy = msg2Html(cmsg);
                System.out.println(" Ticket transaction :\n" + bdy);
                ml.sndMsg("transaction has been recived ... ", bdy);
            } else {
                System.out.println("Invalid message");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String msg2Html(MapMessage m) {
        String ky, s = "";
        try {
            Enumeration it = m.getPropertyNames();
            while (it.hasMoreElements()) {
                ky = (String)it.nextElement();
                s += ky + ": " + m.getStringProperty(ky) + "<br/>\n";
            }
        } catch (JMSException ex) {
            ex.printStackTrace();
        }
        return s;
    }
}
