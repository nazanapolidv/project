package BLL;

import java.util.Date;

public class Evento {
    private int idEvento;
    private String titulo;
    private Date fecha;
    private int cupoMaximo;
    private String ubicacion;
    private String descripcion;

    public Evento() {}

    public Evento(int idEvento, String titulo, Date fecha, int cupoMaximo, String ubicacion, String descripcion) {
        this.idEvento = idEvento;
        this.titulo = titulo;
        this.fecha = fecha;
        this.cupoMaximo = cupoMaximo;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public int getIdEvento() { return idEvento; }
    public void setIdEvento(int idEvento) { this.idEvento = idEvento; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public int getCupoMaximo() { return cupoMaximo; }
    public void setCupoMaximo(int cupoMaximo) { this.cupoMaximo = cupoMaximo; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    @Override
    public String toString() {
        return "Evento [" + idEvento + "] " + titulo + " | Lugar: " + ubicacion + " | Fecha: " + fecha;
    }
}