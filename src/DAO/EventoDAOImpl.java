package DAO;

import BLL.Evento;
import DLL.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventoDAOImpl implements EventoDAO {

    private Connection getConexion() {
        return Conexion.getInstance().getConexion();
    }

    @Override
    public boolean crearEvento(Evento evento) {
        Connection conn = getConexion();
        if (conn == null) return false;

        String sql = "INSERT INTO evento (titulo, fecha, cupo_maximo, ubicacion, descripcion) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, evento.getTitulo());
            ps.setTimestamp(2, new java.sql.Timestamp(evento.getFecha().getTime()));
            ps.setInt(3, evento.getCupoMaximo());
            ps.setString(4, evento.getUbicacion());
            ps.setString(5, evento.getDescripcion());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error en EventoDAOImpl al crear evento: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Evento> listarEventos() {
        List<Evento> lista = new ArrayList<>();
        Connection conn = getConexion();
        if (conn == null) return lista;

        String sql = "SELECT id_evento, titulo, fecha, cupo_maximo, ubicacion, descripcion FROM evento";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Evento(
                        rs.getInt("id_evento"),
                        rs.getString("titulo"),
                        rs.getDate("fecha"),
                        rs.getInt("cupo_maximo"),
                        rs.getString("ubicacion"),
                        rs.getString("descripcion")));
            }
        } catch (SQLException e) {
            System.err.println("Error en EventoDAOImpl al listar eventos: " + e.getMessage());
        }
        return lista;
    }
}
