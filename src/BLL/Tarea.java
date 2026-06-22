package BLL;

import java.util.Date;

public class Tarea {
	private int idTarea;
	private String titulo;
	private String descripcion;
	private int puntosOtorgados;
	private Date fechaCaducidad;
	private String eje;

	public Tarea() {
	}

	public Tarea(int idTarea, String titulo, String descripcion, int puntosOtorgados, Date fechaCaducidad, String eje) {
		this.idTarea = idTarea;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.puntosOtorgados = puntosOtorgados;
		this.fechaCaducidad = fechaCaducidad;
		this.eje = eje;
	}

	public Tarea(String titulo, String descripcion, int puntosOtorgados, Date fechaCaducidad, String eje) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.puntosOtorgados = puntosOtorgados;
		this.fechaCaducidad = fechaCaducidad;
		this.eje = eje;
	}

	// Getters y Setters
	public int getIdTarea() {
		return idTarea;
	}

	public void setIdTarea(int idTarea) {
		this.idTarea = idTarea;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getPuntosOtorgados() {
		return puntosOtorgados;
	}

	public void setPuntosOtorgados(int puntosOtorgados) {
		this.puntosOtorgados = puntosOtorgados;
	}

	public Date getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	public String getEje() {
		return eje;
	}

	public void setEje(String eje) {
		this.eje = eje;
	}

	@Override
	public String toString() {
		return "[" + eje + "] ID: " + idTarea + " - " + titulo + " (" + puntosOtorgados + " pts) | Vence: "
				+ fechaCaducidad;
	}
}
