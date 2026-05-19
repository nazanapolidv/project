package BLL;

public class Hito {
    private int idHito;
    private String nombre;
    private int puntosRequeridos;

    public Hito() {}

    public Hito(int idHito, String nombre, int puntosRequeridos) {
        this.idHito = idHito;
        this.nombre = nombre;
        this.puntosRequeridos = puntosRequeridos;
    }

    // Getters y Setters
    public int getIdHito() { return idHito; }
    public void setIdHito(int idHito) { this.idHito = idHito; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getPuntosRequeridos() { return puntosRequeridos; }
    public void setPuntosRequeridos(int puntosRequeridos) { this.puntosRequeridos = puntosRequeridos; }
}