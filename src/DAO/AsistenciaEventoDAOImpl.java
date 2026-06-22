package DAO;
import DLL.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AsistenciaEventoDAOImpl implements AsistenciaEventoDAO {

    private Connection getConexion() {
        return Conexion.getInstance().getConexion();
    }

    @Override
    public boolean registrarAsistencia(int idCliente, int idEvento) {
        Connection conn = getConexion();
        if (conn == null) return false;

        String sql = "INSERT INTO asistencia_evento (id_cliente, id_evento, fecha_inscripcion) VALUES (?, ?, CURRENT_TIMESTAMP)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            ps.setInt(2, idEvento);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error en AsistenciaEventoDAOImpl al registrar asistencia: " + e.getMessage());
            return false;
        }
    }
}
