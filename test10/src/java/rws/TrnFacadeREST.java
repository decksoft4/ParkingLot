package rws;
import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import ent.Transactions;
import hlpr.NewRmtTrn;
import jakarta.persistence.Query;
import java.time.Instant;
import java.util.Date;

@Stateless
@Path("trns")
public class TrnFacadeREST extends AbstractFacade<Transactions> {

    @PersistenceContext(unitName = "test10PU")
    private EntityManager em;

    public TrnFacadeREST() {
        super(Transactions.class);
    }

    @POST
//    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void nwRmtTrn(NewRmtTrn entity) {
//    public void create(Transactions entity) {
//        super.create(entity);
        Query qry=em.createNativeQuery("INSERT INTO Transactions(tktSer,brand,type,color,tktInc) VALUES(?,?,?,?,?)");
        qry.setParameter(1, entity.getTktSer());
        qry.setParameter(2, entity.getBrand());
        qry.setParameter(3, entity.getType());
        qry.setParameter(4, entity.getColor());
        qry.setParameter(5, Date.from(Instant.now()));
        qry.executeUpdate();
        em.flush();
        Transactions tr = fndBySer(entity.getTktSer());
        em.refresh(tr);
    }

    @PUT
    @Path("{tktSer}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("tktSer") String tktSer) {
//    public void edit(@PathParam("id") Integer id, Transactions entity) {
//        super.edit(entity);
        Query qry=em.createNativeQuery("UPDATE Transactions SET tktOut=? WHERE tktSer=?");
        qry.setParameter(1, Date.from(Instant.now()));
        qry.setParameter(2, tktSer);
        qry.executeUpdate();
        em.flush();
        Transactions tr = super.fndBySer(tktSer);
        em.refresh(tr);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Transactions find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Transactions> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Transactions> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
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
