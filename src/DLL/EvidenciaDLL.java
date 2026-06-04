package DLL;

import BLL.Evidencia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EvidenciaDLL {

    private Connection getConexion() {
        return Conexion.getInstance().getConexion();
    }

   
    public boolean agregarEvidencia(Evidencia evidencia) {
        String sql = "INSERT INTO evidencia (id_cliente, id_tarea, archivo_url, estado, fecha_subida) VALUES (?, ?, ?, 'Pendiente de revisión', NOW())";
        
        try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
            
            
            int idClienteSimulado = (evidencia.getIdCliente() <= 0) ? 4 : evidencia.getIdCliente();
            int idTareaSimulada = (evidencia.getIdTarea() <= 0) ? 1 : evidencia.getIdTarea();
            
            ps.setInt(1, idClienteSimulado);
            ps.setInt(2, idTareaSimulada);
            ps.setString(3, evidencia.getArchivoUrl());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error en EvidenciaDLL al insertar: " + e.getMessage());
            return false;
        }
    }

    
    public List<Evidencia> obtenerEvidenciasPendientes() {
        List<Evidencia> lista = new ArrayList<>();
        
        
        String sql = "SELECT e.id_evidencia, e.id_cliente, e.id_tarea, e.archivo_url, e.estado, e.fecha_subida, " +
                     "u.email AS nombre_cliente, t.titulo AS titulo_tarea, t.puntos_otorgados AS puntos_tarea " +
                     "FROM evidencia e " +
                     "INNER JOIN usuario u ON e.id_cliente = u.id_usuario " +
                     "INNER JOIN tarea t ON e.id_tarea = t.id_tarea " +
                     "WHERE e.estado = 'Pendiente de revisión'";

        try (PreparedStatement ps = getConexion().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Evidencia ev = new Evidencia();
                ev.setIdEvidencia(rs.getInt("id_evidencia"));
                ev.setIdCliente(rs.getInt("id_cliente"));
                ev.setIdTarea(rs.getInt("id_tarea"));
                ev.setArchivoUrl(rs.getString("archivo_url"));
                ev.setEstado(rs.getString("estado"));
                ev.setFechaSubida(rs.getTimestamp("fecha_subida"));
                
                ev.setNombreCliente(rs.getString("nombre_cliente"));
                ev.setTituloTarea(rs.getString("titulo_tarea"));
                ev.setPuntosTarea(rs.getInt("puntos_tarea")); // Esto ahora va a funcionar perfecto

                lista.add(ev);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener evidencias pendientes: " + e.getMessage());
        }
        return lista;
    }
    
    public boolean procesarEvidencia(int idEvidencia, int idCliente, int puntos, boolean aprobado) {
        String nuevoEstado = aprobado ? "Aprobada" : "Rechazada";
        String sqlUpdateEvidencia = "UPDATE evidencia SET estado = ? WHERE id_evidencia = ?";
        
        
        String sqlUpdatePuntos = "UPDATE usuario SET puntos = puntos + ? WHERE id_usuario = ?";

        Connection conn = null;
        try {
            conn = getConexion();
            
            conn.setAutoCommit(false);

            
            try (PreparedStatement psEv = conn.prepareStatement(sqlUpdateEvidencia)) {
                psEv.setString(1, nuevoEstado);
                psEv.setInt(2, idEvidencia);
                psEv.executeUpdate();
            }

            
            if (aprobado && puntos > 0) {
                try (PreparedStatement psPts = conn.prepareStatement(sqlUpdatePuntos)) {
                    psPts.setInt(1, puntos);
                    psPts.setInt(2, idCliente);
                    psPts.executeUpdate();
                }
            }

            conn.commit(); 
            return true;

        } catch (SQLException e) {
            System.err.println("Error al procesar la validación de la evidencia: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback(); 
                } catch (SQLException ex) {
                    System.err.println("Error en el rollback: " + ex.getMessage());
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    System.err.println("Error al restaurar autoCommit: " + e.getMessage());
                }
            }
        }
    }
}