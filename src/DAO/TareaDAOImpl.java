package DAO;
import BLL.Tarea;
import DLL.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TareaDAOImpl implements TareaDAO {

    private Connection getConexion() {
        return Conexion.getInstance().getConexion();
    }

    @Override
    public boolean crearTarea(Tarea tarea) {
        Connection conn = getConexion();
        if (conn == null) return false;

        String sql = "INSERT INTO tarea (titulo, descripcion, puntos_otorgados, fecha_caducidad, eje, fecha_publicacion) "
                   + "VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tarea.getTitulo());
            ps.setString(2, tarea.getDescripcion());
            ps.setInt(3, tarea.getPuntosOtorgados());
            ps.setDate(4, new java.sql.Date(tarea.getFechaCaducidad().getTime()));
            ps.setString(5, tarea.getEje());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error en TareaDAOImpl al crear tarea: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Tarea> listarTareas() {
        List<Tarea> lista = new ArrayList<>();
        Connection conn = getConexion();
        if (conn == null) return lista;

        String sql = "SELECT id_tarea, titulo, descripcion, puntos_otorgados, fecha_caducidad, eje FROM tarea";
        try (PreparedStatement ps = conn.prepareStatement(sql);
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
            System.err.println("Error en TareaDAOImpl al listar tareas: " + e.getMessage());
        }
        return lista;
    }
}
