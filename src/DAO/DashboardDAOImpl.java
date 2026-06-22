package DAO;
import DLL.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardDAOImpl implements DashboardDAO {

    private Connection getConexion() {
        return Conexion.getInstance().getConexion();
    }

    @Override
    public int getCantidadUsuarios() {
        return ejecutarConteo("SELECT COUNT(*) AS total FROM cliente");
    }

    @Override
    public int getTareasActivas() {
        return ejecutarConteo("SELECT COUNT(*) AS total FROM tarea WHERE fecha_caducidad >= CURRENT_DATE");
    }

    @Override
    public int getEvidenciasPendientes() {
        return ejecutarConteo("SELECT COUNT(*) AS total FROM evidencia WHERE estado = 'Pendiente'");
    }

    @Override
    public int getEventosProximos() {
        return ejecutarConteo("SELECT COUNT(*) AS total FROM evento WHERE fecha >= CURRENT_DATE");
    }

    @Override
    public String getEvidenciasPorEje() {
        Connection conn = getConexion();
        if (conn == null) return "  Sin datos.\n";

        String sql = "SELECT t.eje, COUNT(e.id_evidencia) AS cantidad " +
                     "FROM evidencia e " +
                     "JOIN tarea t ON e.id_tarea = t.id_tarea " +
                     "WHERE e.estado = 'Pendiente' " +
                     "GROUP BY t.eje";

        StringBuilder resultado = new StringBuilder();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                resultado.append("  ").append(rs.getString("eje"))
                         .append(" : ").append(rs.getInt("cantidad")).append("\n");
            }
        } catch (SQLException e) {
            System.err.println("Error en DashboardDAOImpl (ejes): " + e.getMessage());
        }
        return resultado.length() > 0 ? resultado.toString() : "  Sin evidencias pendientes.\n";
    }

    private int ejecutarConteo(String sql) {
        Connection conn = getConexion();
        if (conn == null) return 0;

        int total = 0;
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("Error en DashboardDAOImpl (conteo): " + e.getMessage());
        }
        return total;
    }
}
