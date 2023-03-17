package srvlt;

import ejb.TrnManager;
import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.jms.Connection;
import jakarta.jms.JMSException;
import jakarta.jms.MapMessage;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;
import jakarta.jms.Topic;
import jakarta.jms.TopicConnectionFactory;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import ent.Transactions;

@WebServlet(name = "tst03", urlPatterns = {"/tst03"})
public class tst03 extends HttpServlet {

    @Resource(mappedName = "jms/msgTopicFct")
    private TopicConnectionFactory tcf;
    @Resource(mappedName = "jms/msgTopic")
    private Topic mt;
    @EJB
    TrnManager tm;
    Transactions tr;

    protected void prReq(HttpServletRequest req, HttpServletResponse rsp) throws ServletException, IOException, JMSException {
        rsp.setContentType("text/html;charset=UTF-8");

        try (PrintWriter pwr = rsp.getWriter()) {
            /* TODO pwrput your page here. You may use following sample code. */
            pwr.println("<!DOCTYPE html><html><head><title>queueing a message</title></head><body><h2>Servlet at " + req.getContextPath() + "</h2>");
            String ser=req.getParameter("serial");
            if (ser==null || ser.isEmpty()) {
                pwr.println("Provided serial is empty or undefined ... aborting!<br/>");
                pwr.println("</body></html>");
                pwr.close();
                return;
            }
            pwr.println("<p>Updating ticket: "+ser+"</p><br/>\n");
            tr = tm.fndBySer(ser);
            if (tr != null) {
                pwr.println("Updating ticket: "+ser+"<br/>");
                Date dt=Date.from(Instant.now());
                Connection cnt = tcf.createConnection();
                cnt.start();
                Session ts = cnt.createSession(false, Session.AUTO_ACKNOWLEDGE);
                MessageProducer pbl = ts.createProducer(mt);
                MapMessage msg = ts.createMapMessage();
                msg.setStringProperty("id", tr.getTrnId().toString());
                pwr.println("Id: "+tr.getTrnId().toString()+"<br/>");
                msg.setStringProperty("serial", tr.getTktSer());
                pwr.println("Serial: "+tr.getTktSer()+"<br/>");
                msg.setStringProperty("brand", tr.getBrand());
                pwr.println("Brand: "+tr.getBrand()+"<br/>");
                msg.setStringProperty("type", tr.getType());
                pwr.println("Type: "+tr.getType()+"<br/>");
                msg.setStringProperty("color", tr.getColor());
                pwr.println("Color: "+tr.getColor()+"<br/>");
                msg.setStringProperty("incoming", tr.getTktInc().toString());
                pwr.println("Incoming: "+tr.getTktInc().toString()+"<br/>");
                msg.setStringProperty("outgoing", dt.toString());
                pwr.println("Outgoing: "+dt.toString()+"<br/>");
                pbl.send(msg);
                tr.setTktOut(dt);
                tm.updTrn(tr);
                pwr.println("</body></html>");
                pwr.close();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse rsp) throws ServletException, IOException {
        try {
            prReq(req, rsp);
        } catch (JMSException ex) {
            Logger.getLogger(tst03.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse rsp) throws ServletException, IOException {
        try {
            prReq(req, rsp);
        } catch (JMSException ex) {
            Logger.getLogger(tst03.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Ticket outgoing updater";
    }
}
