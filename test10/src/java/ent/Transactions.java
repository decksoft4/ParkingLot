package ent;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "Transactions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "trns.fndAll", query = "SELECT t FROM Transactions t"),
    @NamedQuery(name = "trns.fndByTrnId", query = "SELECT t FROM Transactions t WHERE t.trnId = :trnId"),
    @NamedQuery(name = "trns.fndByTktSer", query = "SELECT t FROM Transactions t WHERE t.tktSer = :tktSer"),
    @NamedQuery(name = "trns.fndByBrand", query = "SELECT t FROM Transactions t WHERE t.brand = :brand"),
    @NamedQuery(name = "trns.fndByType", query = "SELECT t FROM Transactions t WHERE t.type = :type"),
    @NamedQuery(name = "trns.fndByColor", query = "SELECT t FROM Transactions t WHERE t.color = :color"),
    @NamedQuery(name = "trns.fndByTktInc", query = "SELECT t FROM Transactions t WHERE t.tktInc = :tktInc"),
    @NamedQuery(name = "trns.fndAfterTktInc", query = "SELECT t FROM Transactions t WHERE t.tktInc >= :tktInc"),
    @NamedQuery(name = "trns.fndByTktOut", query = "SELECT t FROM Transactions t WHERE t.tktOut = :tktOut"),
    @NamedQuery(name = "trns.fndByTsInc", query = "SELECT t FROM Transactions t WHERE t.tsInc = :tsInc"),
    @NamedQuery(name = "trns.fndByTsOut", query = "SELECT t FROM Transactions t WHERE t.tsOut = :tsOut"),
    @NamedQuery(name = "trns.remByTktSer", query = "delete FROM Transactions t WHERE t.tktSer = :tktSer")})
public class Transactions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "trnId")
    private Integer trnId;
    @Basic(optional = false)
    @Column(name = "TktSer")
    private String tktSer;
    @Basic(optional = false)
    @Column(name = "Brand")
    private String brand;
    @Basic(optional = false)
    @Column(name = "Type")
    private String type;
    @Basic(optional = false)
    @Column(name = "Color")
    private String color;
    @Basic(optional = false)
    @Column(name = "TktInc")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tktInc;
    @Column(name = "TktOut")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tktOut;
    @Basic(optional = false)
    @Column(name = "TsInc")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tsInc;
    @Column(name = "TsOut")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tsOut;

    public Transactions() {
    }

    public Transactions(Integer trnId) {
        this.trnId = trnId;
    }

    public Transactions(Integer trnId, String tktSer, String brand, String type, String color, Date tktInc, Date tsInc) {
        this.trnId = trnId;
        this.tktSer = tktSer;
        this.brand = brand;
        this.type = type;
        this.color = color;
        this.tktInc = tktInc;
        this.tsInc = tsInc;
    }

    public Integer getTrnId() {
        return trnId;
    }

    public void setTrnId(Integer trnId) {
        this.trnId = trnId;
    }

    public String getTktSer() {
        return tktSer;
    }

    public void setTktSer(String tktSer) {
        this.tktSer = tktSer;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Date getTktInc() {
        return tktInc;
    }

    public void setTktInc(Date tktInc) {
        this.tktInc = tktInc;
    }

    public Date getTktOut() {
        return tktOut;
    }

    public void setTktOut(Date tktOut) {
        this.tktOut = tktOut;
    }

    public Date getTsInc() {
        return tsInc;
    }

    public void setTsInc(Date tsInc) {
        this.tsInc = tsInc;
    }

    public Date getTsOut() {
        return tsOut;
    }

    public void setTsOut(Date tsOut) {
        this.tsOut = tsOut;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (trnId != null ? trnId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Transactions)) {
            return false;
        }
        Transactions other = (Transactions) object;
        if ((this.trnId == null && other.trnId != null) || (this.trnId != null && !this.trnId.equals(other.trnId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ent.Transactions[ trnId=" + trnId.toString() + " ]";
    }
    
}
