package BLL;

import java.util.Date;

public class CanjePremio {
    private int idCanje;
    private int idCliente;
    private int idPremio;
    private Date fechaCanje;

    public CanjePremio() {}

    public CanjePremio(int idCanje, int idCliente, int idPremio, Date fechaCanje) {
        this.idCanje = idCanje;
        this.idCliente = idCliente;
        this.idPremio = idPremio;
        this.fechaCanje = fechaCanje;
    }

    // Getters y Setters
    public int getIdCanje() { return idCanje; }
    public void setIdCanje(int idCanje) { this.idCanje = idCanje; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public int getIdPremio() { return idPremio; }
    public void setIdPremio(int idPremio) { this.idPremio = idPremio; }

    public Date getFechaCanje() { return fechaCanje; }
    public void setFechaCanje(Date fechaCanje) { this.fechaCanje = fechaCanje; }
}