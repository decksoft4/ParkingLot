package srvlt;
import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

@WebFilter("/*")
public class LoggingFilter implements Filter {
    private FilterConfig filterConf=null;
    @Override
    public void init(FilterConfig fc) {
        this.filterConf=fc;
    }
    @Override
    public void doFilter(ServletRequest req,ServletResponse rsp,FilterChain fc) throws IOException,ServletException {
        System.out.println("-> Visitor: "+req.getRemoteHost()+" - "+req.getRemoteAddr());
        fc.doFilter(req, rsp);
    }
    @Override
    public void destroy() {
        System.out.println("-> Destroying filter!");
    }
}
