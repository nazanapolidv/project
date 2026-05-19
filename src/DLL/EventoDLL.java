package DLL;

import BLL.Evento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventoDLL {

  
    public boolean crearEvento(Evento evento) {
        String sql = "INSERT INTO evento (titulo, fecha, cupo_maximo, ubicacion, descripcion) VALUES (?, ?, ?, ?, ?)";
        boolean exito = false;
        try (Connection con = Conexion.getInstance().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, evento.getTitulo());
            ps.setDate(2, new java.sql.Date(evento.getFecha().getTime()));
            ps.setInt(3, evento.getCupoMaximo());
            ps.setString(4, evento.getUbicacion());
            ps.setString(5, evento.getDescripcion());
            exito = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error [EventoDLL]: " + e.getMessage());
        }
        return exito;
    }

    public List<Evento> listarEventos() {
        String sql = "SELECT id_evento, titulo, fecha, cupo_maximo, ubicacion, descripcion FROM evento";
        List<Evento> lista = new ArrayList<>();
        try (Connection con = Conexion.getInstance().getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                lista.add(new Evento(
                    rs.getInt("id_evento"),
                    rs.getString("titulo"),
                    rs.getDate("fecha"),
                    rs.getInt("cupo_maximo"),
                    rs.getString("ubicacion"),
                    rs.getString("descripcion")
                ));
            }
            
        } catch (SQLException e) {
            System.out.println("Error [EventoDLL]: " + e.getMessage());
        }
        
        return lista;
    } 
     }