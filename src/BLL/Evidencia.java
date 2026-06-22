package BLL;

import java.util.Date;

public class Evidencia {
    private int idEvidencia;
    private int idCliente;
    private int idTarea;
    private String archivoUrl;
    private String estado;
    private Date fechaSubida;

    private String nombreCliente;
    private String tituloTarea;
    private int puntosTarea;

    public Evidencia() {}

    // Getters y Setters
    public int getIdEvidencia() { return idEvidencia; }
    public void setIdEvidencia(int idEvidencia) { this.idEvidencia = idEvidencia; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public int getIdTarea() { return idTarea; }
    public void setIdTarea(int idTarea) { this.idTarea = idTarea; }

    public String getArchivoUrl() { return archivoUrl; }
    public void setArchivoUrl(String archivoUrl) { this.archivoUrl = archivoUrl; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Date getFechaSubida() { return fechaSubida; }
    public void setFechaSubida(Date fechaSubida) { this.fechaSubida = fechaSubida; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getTituloTarea() { return tituloTarea; }
    public void setTituloTarea(String tituloTarea) { this.tituloTarea = tituloTarea; }

    public int getPuntosTarea() { return puntosTarea; }
    public void setPuntosTarea(int puntosTarea) { this.puntosTarea = puntosTarea; }
}