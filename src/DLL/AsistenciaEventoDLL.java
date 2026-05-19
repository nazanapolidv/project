package DLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AsistenciaEventoDLL {

   
    public boolean registrarAsistencia(int idCliente, int idEvento) {
        String sql = "INSERT INTO asistencia_evento (id_cliente, id_evento, fecha_inscripcion) VALUES (?, ?, CURRENT_TIMESTAMP)";
        boolean exito = false;
        try (Connection con = Conexion.getInstance().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            ps.setInt(2, idEvento);
            exito = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error [AsistenciaEventoDLL]: " + e.getMessage());
        }
        return exito;
    }
}