package umg.edu.gt.producer.model;
import java.util.List;

public class LoteTransacciones {
    private String loteId;
    private String fechaGeneracion;
    private List<Transaccion> transacciones;

    // Constructor vacio (lo requiere asi jackson para mapear el json)
    public LoteTransacciones() {
    }

    //Getters y setters
    public String getLoteId() {
        return loteId;
    }
    public void setLoteId(String loteId) {
        this.loteId = loteId;
    }

    public String getFechaGeneracion() {
        return fechaGeneracion;
    }
    public void setFechaGeneracion(String fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public List<Transaccion> getTransaccions() {
        return transacciones;
    }
    public void setTransacciones(List<Transaccion> transacciones) {
        this.transacciones = transacciones;
    }
}
