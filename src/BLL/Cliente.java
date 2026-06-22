package BLL;

import java.util.Date;

public class Cliente {

    private int id;
    private String nombreORazonSocial;
    private String apellido;
    private String dniCuit;
    private String direccion;
    private Date fechaNacimiento;
    private String tipoPlan;
    private int puntosAcumulados;

    public Cliente() {
    }

    public Cliente(String nombreORazonSocial, String apellido, String dniCuit,
                   String direccion, Date fechaNacimiento, String tipoPlan) {
        this.nombreORazonSocial = nombreORazonSocial;
        this.apellido = apellido;
        this.dniCuit = dniCuit;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.tipoPlan = tipoPlan;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombreORazonSocial() { return nombreORazonSocial; }
    public void setNombreORazonSocial(String nombreORazonSocial) { this.nombreORazonSocial = nombreORazonSocial; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getDniCuit() { return dniCuit; }
    public void setDniCuit(String dniCuit) { this.dniCuit = dniCuit; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public Date getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getTipoPlan() { return tipoPlan; }
    public void setTipoPlan(String tipoPlan) { this.tipoPlan = tipoPlan; }

    public int getPuntosAcumulados() { return puntosAcumulados; }
    public void setPuntosAcumulados(int puntosAcumulados) { this.puntosAcumulados = puntosAcumulados; }
}
