package hlpr;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class NewRmtTrn implements Serializable {
    private String brand;
    private String color;
    private String tktSer;
    private String type;

    private static final long serialVersionUID = 1L;

    public NewRmtTrn(String brand, String color, String tktSer, String type) {
        this.brand = brand;
        this.color = color;
        this.tktSer = tktSer;
        this.type = type;
    }

    public NewRmtTrn() {
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTktSer() {
        return tktSer;
    }

    public void setTktSer(String tktSer) {
        this.tktSer = tktSer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
