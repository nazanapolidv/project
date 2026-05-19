package DLL;

import BLL.Hito;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HitoDLL {

	public List<Hito> obtenerHitos() {
		String sql = "SELECT id_hito, nombre, puntos_requeridos FROM hito ORDER BY puntos_requeridos ASC";
		List<Hito> lista = new ArrayList<>();

		try (Connection con = Conexion.getInstance().getConexion();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				lista.add(new Hito(rs.getInt("id_hito"), rs.getString("nombre"), rs.getInt("puntos_requeridos")));
			}
		} catch (SQLException e) {
			System.out.println("Error [HitoDLL]: " + e.getMessage());
		}
		return lista;
	}
}