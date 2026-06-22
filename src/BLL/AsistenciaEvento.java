package BLL;

import java.util.Date;

public class AsistenciaEvento {
    private int idCliente;
    private int idEvento;
    private Date fechaInscripcion;

    public AsistenciaEvento() {}

    public AsistenciaEvento(int idCliente, int idEvento, Date fechaInscripcion) {
        this.idCliente = idCliente;
        this.idEvento = idEvento;
        this.fechaInscripcion = fechaInscripcion;
    }

    // Getters y Setters
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public int getIdEvento() { return idEvento; }
    public void setIdEvento(int idEvento) { this.idEvento = idEvento; }

    public Date getFechaInscripcion() { return fechaInscripcion; }
    public void setFechaInscripcion(Date fechaInscripcion) { this.fechaInscripcion = fechaInscripcion; }
}