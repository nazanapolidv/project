package DAO;
import BLL.Premio;
import DLL.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PremioDAOImpl implements PremioDAO {

    private Connection getConexion() {
        return Conexion.getInstance().getConexion();
    }

    @Override
    public boolean guardarPremio(Premio premio) {
        Connection conn = getConexion();
        if (conn == null) return false;

        String sql = "INSERT INTO premio (descripcion, costo_puntos) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, premio.getDescripcion());
            ps.setInt(2, premio.getCostoPuntos());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error en PremioDAOImpl al guardar premio: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Premio> listarPremios() {
        List<Premio> lista = new ArrayList<>();
        Connection conn = getConexion();
        if (conn == null) return lista;

        String sql = "SELECT id_premio, descripcion, costo_puntos FROM premio";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Premio(
                        rs.getInt("id_premio"),
                        rs.getString("descripcion"),
                        rs.getInt("costo_puntos")));
            }
        } catch (SQLException e) {
            System.err.println("Error en PremioDAOImpl al listar premios: " + e.getMessage());
        }
        return lista;
    }
}
