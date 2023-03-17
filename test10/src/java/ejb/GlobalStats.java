package ejb;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.ConcurrencyManagement;
import jakarta.ejb.ConcurrencyManagementType;
import jakarta.ejb.Lock;
import jakarta.ejb.LockType;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

@Singleton(name="GlobalStats")
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class GlobalStats {
    private int sessCount;

    @Lock(LockType.READ)
    public int getSessCount() {
        return sessCount;
    }

    @Lock(LockType.WRITE)
    public void decSessCount() {
        sessCount--;
    }
    @Lock(LockType.WRITE)
    public void incSessCount() {
        sessCount++;
    }
    @Lock(LockType.WRITE)
    public void rstSessCount() {
        sessCount=0;
    }
    @PostConstruct
    public void applicationStartup() {
        System.out.println("Application startup event (Singleton)");
        rstSessCount();
    }
    @PreDestroy
    public void applicationShutdown() {
        System.out.println("Application is shutting down (Singleton)");
    }
}
