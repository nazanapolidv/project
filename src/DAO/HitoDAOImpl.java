package DAO;
import BLL.Hito;
import DLL.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class HitoDAOImpl implements HitoDAO {

    private Connection getConexion() {
        return Conexion.getInstance().getConexion();
    }

    @Override
    public List<Hito> obtenerHitos() {
        List<Hito> lista = new ArrayList<>();
        Connection conn = getConexion();
        if (conn == null) return lista;

        String sql = "SELECT id_hito, nombre, puntos_requeridos FROM hito ORDER BY puntos_requeridos ASC";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Hito(
                        rs.getInt("id_hito"),
                        rs.getString("nombre"),
                        rs.getInt("puntos_requeridos")));
            }
        } catch (SQLException e) {
            System.err.println("Error en HitoDAOImpl al obtener hitos: " + e.getMessage());
        }
        return lista;
    }
}
