package srvlt;
import ejb.TrnManager;
import jakarta.ejb.EJB;
import java.io.*;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.util.List;
import ent.Transactions;


@WebServlet(name = "tst02", urlPatterns = {"/tst02"})
public class tst02 extends HttpServlet {

    @EJB
    TrnManager trm;

    @Override
    public void init() {
    }
    protected void proReq(HttpServletRequest req,HttpServletResponse rsp) throws ServletException,IOException {
        rsp.setContentType("text/html;charset=UTF-8");
        int rsl=-1;
        try (PrintWriter pwr=rsp.getWriter()) {
            pwr.println("<!DOCTYPE html><html><head><title>Ticket entry result</title></head><body><h2>Ticket entry at:"+req.getContextPath()+ "</h2><br/>");
            // Output table begin
            pwr.println("<style>table,tr,th,td {border: 1px solid;}</style>");
            pwr.println("<table>");
            pwr.println("<tr><th>Id</th><th>TcktSerial</th><th>CarBrand</th><th>CarType</th><th>CarColor</th><th>IncDt</th><th>OutDt</th><th>IncTS</th><th>OutTS</th></tr>");
            Date ini=new Date(2,1,23);
            List <Transactions> tlst=trm.fndTrnAfter(ini);
            tlst.stream().forEach(a->{pwr.println("<tr><td>"+a.getTrnId().toString()+"</td><td>"+a.getTktSer()+"</td><td>"+a.getBrand()+"</td><td>"+a.getType()+"</td><td>"+a.getColor()+"</td><td>"+a.getTktInc().toString()+"</td><td>"+((a.getTktOut()!=null)?a.getTktOut().toString():"")+"</td><td>"+a.getTsInc().toString()+"</td><td>"+((a.getTsOut()!=null)?a.getTsOut().toString():"")+"</td></tr>\n");});
            pwr.println("</table>");
            // Output table end
            pwr.println("</body></html>");
        }
    }
    @Override
    public void doGet(HttpServletRequest req,HttpServletResponse rsp) throws ServletException, IOException {
        proReq(req,rsp);
    }
    @Override
    public void doPost(HttpServletRequest req,HttpServletResponse rsp) throws ServletException, IOException {
        proReq(req,rsp);
    }
    @Override
    public String getServletInfo() {
        return "get ticket listing after a give date";
    }

}
