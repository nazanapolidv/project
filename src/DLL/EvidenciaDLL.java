package DLL;

import BLL.Evidencia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EvidenciaDLL {

    public List<Evidencia> obtenerEvidenciasPendientes() {
        List<Evidencia> lista = new ArrayList<>();
        String sql = "SELECT e.id_evidencia, e.id_cliente, e.id_tarea, e.archivo_url, e.fecha_subida, " +
                     "c.nombre_o_razon_social, t.titulo, t.puntos_otorgados " +
                     "FROM evidencia e " +
                     "JOIN cliente c ON e.id_cliente = c.id_cliente " +
                     "JOIN tarea t ON e.id_tarea = t.id_tarea " +
                     "WHERE e.estado = 'Pendiente'";

        try (Connection con = Conexion.getInstance().getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Evidencia ev = new Evidencia();
                ev.setIdEvidencia(rs.getInt("id_evidencia"));
                ev.setIdCliente(rs.getInt("id_cliente"));
                ev.setIdTarea(rs.getInt("id_tarea"));
                ev.setArchivoUrl(rs.getString("archivo_url"));
                ev.setFechaSubida(rs.getTimestamp("fecha_subida"));
                ev.setNombreCliente(rs.getString("nombre_o_razon_social"));
                ev.setTituloTarea(rs.getString("titulo"));
                ev.setPuntosTarea(rs.getInt("puntos_otorgados"));
                lista.add(ev);
            }
        } catch (SQLException e) {
            System.out.println("Error [EvidenciaDLL - listar]: " + e.getMessage());
        }
        return lista;
    }

    public boolean procesarEvidencia(int idEvidencia, int idCliente, int puntos, boolean aprobada) {
        String estadoNuevo = aprobada ? "Aprobada" : "Rechazada";
        String sqlUpdateEvidencia = "UPDATE evidencia SET estado = ? WHERE id_evidencia = ?";
        String sqlUpdatePuntos = "UPDATE cliente SET puntos_acumulados = puntos_acumulados + ? WHERE id_cliente = ?";
        
        Connection con = null;
        try {
            con = Conexion.getInstance().getConexion();
            con.setAutoCommit(false); 
            try (PreparedStatement psEvidencia = con.prepareStatement(sqlUpdateEvidencia)) {
                psEvidencia.setString(1, estadoNuevo);
                psEvidencia.setInt(2, idEvidencia);
                psEvidencia.executeUpdate();
            }

            if (aprobada) {
                try (PreparedStatement psPuntos = con.prepareStatement(sqlUpdatePuntos)) {
                    psPuntos.setInt(1, puntos);
                    psPuntos.setInt(2, idCliente);
                    psPuntos.executeUpdate();
                }
            }

            con.commit();
            return true;

        } catch (SQLException e) {
            if (con != null) {
                try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            System.out.println("Error [EvidenciaDLL - procesar]: " + e.getMessage());
            return false;
        } finally {
            if (con != null) {
                try { con.setAutoCommit(true); con.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }
}