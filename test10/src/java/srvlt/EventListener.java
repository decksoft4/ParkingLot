package srvlt;
import ejb.GlobalStats;
import jakarta.ejb.EJB;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@WebListener
public class EventListener implements ServletContextListener,HttpSessionAttributeListener,HttpSessionListener {
    private final String TAG="-> Event listener: ";
    private ServletContext ctx;
    @EJB
    private GlobalStats gs;

    @Override
    public void contextDestroyed(ServletContextEvent evt) {
        System.out.println(TAG+"servlet shutdown info: "+evt.getServletContext().getServerInfo()+" mils: "+System.currentTimeMillis());
    }
    @Override
    public void attributeAdded(HttpSessionBindingEvent se) {
        HttpSession sess=se.getSession();
        String sid=sess.getId();
        String name=se.getName();
        String value=(String)se.getValue();
        StringBuffer msg=new StringBuffer(TAG+"New attribute has been added to session "+sid).append(" Name: ").append(name).append(" = ").append(value);
        log(msg);
    }
    @Override
    public void attributeRemoved(HttpSessionBindingEvent se) {
        HttpSession sess=se.getSession();
        String sid=sess.getId();
        String name=se.getName();
        if (name==null) name="Unknown";
        StringBuffer msg=new StringBuffer(TAG+"Attribute has been removed from session "+sid).append(" Name: ").append(name);
        log(msg);
    }
    @Override
    public void attributeReplaced(HttpSessionBindingEvent se) {
        HttpSession sess=se.getSession();
        String sid=sess.getId();
        String name=se.getName();
        if (name==null) name="Unknown";
        String value=(String)se.getValue();
        StringBuffer msg=new StringBuffer(TAG+"Attribute has been replaced from session "+sid).append(" Name: ").append(name).append(" = ").append(value);
        log(msg);
    }
    private void log(StringBuffer msg) {
        if (ctx!=null) {
            ctx.log(TAG+msg);
        } else {
            System.out.println(TAG+msg);
        }
    }
    @Override
    public void contextInitialized(ServletContextEvent evt) {
        this.ctx=evt.getServletContext();
        log(new StringBuffer(TAG+"Context has been initialized"+ctx.getServerInfo()+" mils: "+System.currentTimeMillis()));
    }
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession sess=se.getSession();
        sess.setMaxInactiveInterval(60);
        gs.incSessCount();
        log(new StringBuffer(TAG+"Session created; current session count: "+gs.getSessCount()));
    }
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        gs.decSessCount();
        log(new StringBuffer(TAG+"Session destroyed; current session count: "+gs.getSessCount()));
    }
}
