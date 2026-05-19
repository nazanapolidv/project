package DLL;

import BLL.Premio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PremioDLL {

    
    public boolean guardarPremio(Premio premio) {
        String sql = "INSERT INTO premio (descripcion, costo_puntos) VALUES (?, ?)";
        boolean exito = false;
        
        
        try (Connection con = Conexion.getInstance().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, premio.getDescripcion());
            ps.setInt(2, premio.getCostoPuntos());
            
            exito = ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("Error [PremioDLL]: " + e.getMessage());
        }
        return exito;
    }

   
    public List<Premio> listarPremios() {
        String sql = "SELECT id_premio, descripcion, costo_puntos FROM premio";
        List<Premio> lista = new ArrayList<>();
        
       
        try (Connection con = Conexion.getInstance().getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                lista.add(new Premio(
                    rs.getInt("id_premio"),
                    rs.getString("descripcion"),
                    rs.getInt("costo_puntos")
                ));
            }
            
        } catch (SQLException e) {
            System.out.println("Error [PremioDLL]: " + e.getMessage());
        }
        return lista;
    }
}