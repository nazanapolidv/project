package BLL;

public class Premio {
    private int idPremio;
    private String descripcion;
    private int costoPuntos;

    public Premio() {}

    public Premio(int idPremio, String descripcion, int costoPuntos) {
        this.idPremio = idPremio;
        this.descripcion = descripcion;
        this.costoPuntos = costoPuntos;
    }

    public Premio(String descripcion, int costoPuntos) {
        this.descripcion = descripcion;
        this.costoPuntos = costoPuntos;
    }

    // Getters y Setters
    public int getIdPremio() { return idPremio; }
    public void setIdPremio(int idPremio) { this.idPremio = idPremio; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getCostoPuntos() { return costoPuntos; }
    public void setCostoPuntos(int costoPuntos) { this.costoPuntos = costoPuntos; }

    @Override
    public String toString() {
        return "ID: " + idPremio + " | " + descripcion + " (" + costoPuntos + " pts)";
    }
}