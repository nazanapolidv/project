package DLL;

import BLL.Tarea;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TareaDLL {

	public boolean crearTarea(Tarea tarea) {
		String sql = "INSERT INTO tarea (titulo, descripcion, puntos_otorgados, fecha_caducidad, eje, fecha_publicacion) "
				+ "VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
		boolean exito = false;

		try (Connection con = Conexion.getInstance().getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, tarea.getTitulo());
			ps.setString(2, tarea.getDescripcion());
			ps.setInt(3, tarea.getPuntosOtorgados());

			ps.setDate(4, new java.sql.Date(tarea.getFechaCaducidad().getTime()));
			ps.setString(5, tarea.getEje());

			int filas = ps.executeUpdate();
			if (filas > 0) {
				exito = true;
			}
		} catch (SQLException e) {
			System.out.println("Error [DLL] al crear tarea: " + e.getMessage());
		}
		return exito;
	}

	public List<Tarea> listarTareas() {
		String sql = "SELECT id_tarea, titulo, descripcion, puntos_otorgados, fecha_caducidad, eje FROM tarea";
		List<Tarea> lista = new ArrayList<>();

		try (Connection con = Conexion.getInstance().getConexion();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				Tarea t = new Tarea();
				t.setIdTarea(rs.getInt("id_tarea"));
				t.setTitulo(rs.getString("titulo"));
				t.setDescripcion(rs.getString("descripcion"));
				t.setPuntosOtorgados(rs.getInt("puntos_otorgados"));
				t.setFechaCaducidad(rs.getDate("fecha_caducidad"));
				t.setEje(rs.getString("eje"));
				lista.add(t);
			}
		} catch (SQLException e) {
			System.out.println("Error [DLL] al listar tareas: " + e.getMessage());
		}
		return lista;
	}
}