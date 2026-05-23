package DLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardDLL {

    public int getCantidadUsuarios() {
        String sql = "SELECT COUNT(*) AS total FROM cliente";
        return ejecutarConteo(sql);
    }

    public int getTareasActivas() {
        String sql = "SELECT COUNT(*) AS total FROM tarea WHERE fecha_caducidad >= CURRENT_DATE";
        return ejecutarConteo(sql);
    }

    public int getEvidenciasPendientes() {
        String sql = "SELECT COUNT(*) AS total FROM evidencia WHERE estado = 'Pendiente'";
        return ejecutarConteo(sql);
    }

    public int getEventosProximos() {
        String sql = "SELECT COUNT(*) AS total FROM evento WHERE fecha >= CURRENT_DATE";
        return ejecutarConteo(sql);
    }

    public String getEvidenciasPorEje() {
        String sql = "SELECT t.eje, COUNT(e.id_evidencia) AS cantidad " +
                     "FROM evidencia e " +
                     "JOIN tarea t ON e.id_tarea = t.id_tarea " +
                     "WHERE e.estado = 'Pendiente' " +
                     "GROUP BY t.eje";
                     
        StringBuilder resultado = new StringBuilder();
        try (Connection con = Conexion.getInstance().getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                resultado.append("  ").append(rs.getString("eje"))
                         .append(" : ").append(rs.getInt("cantidad")).append("\n");
            }
        } catch (SQLException e) {
            System.out.println("Error [DashboardDLL - Ejes]: " + e.getMessage());
        }
        
        return resultado.length() > 0 ? resultado.toString() : "  Sin evidencias pendientes.\n";
    }

    private int ejecutarConteo(String sql) {
        int total = 0;
        try (Connection con = Conexion.getInstance().getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
             
            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("Error [DashboardDLL - Conteo]: " + e.getMessage());
        }
        return total;
    }
}