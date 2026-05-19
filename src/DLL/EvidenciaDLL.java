package DLL;

import BLL.Evidencia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EvidenciaDLL {

	public boolean enviarEvidencia(Evidencia evidencia) {
		String sql = "INSERT INTO evidencia (id_cliente, id_tarea, archivo_url, estado, fecha_subida) "
				+ "VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)";
		boolean exito = false;
		try (Connection con = Conexion.getInstance().getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, evidencia.getIdCliente());
			ps.setInt(2, evidencia.getIdTarea());
			ps.setString(3, evidencia.getArchivoUrl());
			ps.setString(4, evidencia.getEstado());

			int filas = ps.executeUpdate();
			if (filas > 0) {
				exito = true;
			}
		} catch (SQLException e) {
			System.out.println("Error [DLL] al guardar evidencia: " + e.getMessage());
		}
		return exito;
	}

	public List<Evidencia> listarPendientes() {
		String sql = "SELECT id_evidencia, id_cliente, id_tarea, archivo_url, estado, fecha_subida FROM evidencia WHERE estado = 'Pendiente'";
		List<Evidencia> lista = new ArrayList<>();

		try (Connection con = Conexion.getInstance().getConexion();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				Evidencia ev = new Evidencia();
				ev.setIdEvidencia(rs.getInt("id_evidencia"));
				ev.setIdCliente(rs.getInt("id_cliente"));
				ev.setIdTarea(rs.getInt("id_tarea"));
				ev.setArchivoUrl(rs.getString("archivo_url"));
				ev.setEstado(rs.getString("estado"));
				ev.setFechaSubida(rs.getTimestamp("fecha_subida"));
				lista.add(ev);
			}
		} catch (SQLException e) {
			System.out.println("Error [DLL] al listar evidencias pendientes: " + e.getMessage());
		}
		return lista;
	}
}