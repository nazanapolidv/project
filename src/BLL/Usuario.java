package BLL;

import java.sql.Timestamp;

public class Usuario {
    private int id;
    private String email;
    private String passwordHash;
    private Timestamp fechaCreacion;
    private String tipoPlan;

    public Usuario() {
    }

    public Usuario(int id, String email, String passwordHash, Timestamp fechaCreacion, String tipoPlan) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.fechaCreacion = fechaCreacion;
        this.tipoPlan = tipoPlan;
    }

    public Usuario(String email, String passwordHash, String tipoPlan) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.tipoPlan = tipoPlan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public String getTipoPlan() {
        return tipoPlan;
    }

    public void setTipoPlan(String tipoPlan) {
        this.tipoPlan = tipoPlan;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Email: " + email + " | Fecha Alta: " + fechaCreacion;
    }
}