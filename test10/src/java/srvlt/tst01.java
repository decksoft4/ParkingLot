package srvlt;

import ejb.TrnManager;
import hlpr.Mailer;
import jakarta.ejb.EJB;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.Date;
import ent.Transactions;

@WebServlet(name = "tst01", urlPatterns = {"/tst01"})
public class tst01 extends HttpServlet {
    @EJB
    TrnManager tm;
    Transactions tr;

    protected void prReq(HttpServletRequest req, HttpServletResponse rsp) throws ServletException, IOException {
        rsp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter pwr = rsp.getWriter()) {
            pwr.println("<!DOCTYPE html><html><head><title>New ticket</title></head><body><h2>adding new ticket " + req.getContextPath() + "</h2>");
            String ser=req.getParameter("tktSer");
            tm.newTrn(ser,req.getParameter("brand"),req.getParameter("type"),req.getParameter("color"),Date.from(Instant.now()));
            tr=tm.fndBySer(ser);
            Mailer ml=new Mailer();
            ml.mailer();
            String tx=trn2Html(tr);
            pwr.println("Ticket data:<br/>"+tx+"<br/>");
            ml.sndMsg("new car arrival id: "+tr.getTrnId().toString(), "Ticket data:<br/>"+tx);
            pwr.println("admin ctrl message has been sent ...");
            pwr.println("</body></html>");
        } catch(Exception e) {
            System.out.println("Exception: "+e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse rsp)
            throws ServletException, IOException {
        prReq(req, rsp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse rsp)
            throws ServletException, IOException {
        prReq(req, rsp);
    }

    @Override
    public String getServletInfo() {
        return "create a new ticket";
    }

    public String trn2Html(Transactions t) {
        String s;
        s="Id:"+t.getTrnId().toString()+"<br/>\n";
        s+="Serial:"+t.getTktSer()+"<br/>\n";
        s+="Brand:"+t.getBrand()+"<br/>\n";
        s+="Plate:"+t.getType()+"<br/>\n";
        s+="Color:"+t.getColor()+"<br/>\n";
        s+="Incoming:"+t.getTktInc().toString()+"<br/>\n";
        return s;
    }
}
