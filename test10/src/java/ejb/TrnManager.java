package ejb;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.Date;
import java.util.List;
import ent.Transactions;

@Stateless
public class TrnManager {
    @PersistenceContext(unitName="test10PU")
    private EntityManager em;
    public TrnManager() {
    }
    public void newTrn(String serial,String brand,String type,String color,Date tktInc) {
        Query qry=em.createNativeQuery("INSERT INTO Transactions(tktSer,brand,type,color,tktInc) VALUES(?,?,?,?,?)");
        qry.setParameter(1, serial);
        qry.setParameter(2, brand);
        qry.setParameter(3, type);
        qry.setParameter(4, color);
        qry.setParameter(5, tktInc);
        qry.executeUpdate();
        em.flush();
        Transactions tr = fndBySer(serial);
        em.refresh(tr);
    }
    public Transactions updTrn(Transactions ent) {
        ent=em.merge(ent);
        return ent;
    }
    public void rmTrnByTcktSer(String tckt) {
        em.createNamedQuery("trns.remByTktSer",Transactions.class).setParameter("tktSer", tckt).executeUpdate();
    }
    public List<Transactions> fndAllTrn() {
        return em.createNamedQuery("trns.fndAll",Transactions.class).getResultList();
    }
    public List<Transactions> fndTrnAfter(Date ini) {
        return em.createNamedQuery("trns.fndAfterTktInc",Transactions.class).setParameter("tktInc", ini).getResultList();
    }
    public Transactions fndBySer(String ser) {
        return em.createNamedQuery("trns.fndByTktSer",Transactions.class).setParameter("tktSer", ser).getSingleResult();
    }
}
