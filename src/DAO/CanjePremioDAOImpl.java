package DAO;
import DLL.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class CanjePremioDAOImpl implements CanjePremioDAO {

    private Connection getConexion() {
        return Conexion.getInstance().getConexion();
    }

    @Override
    public boolean ejecutarCanje(int idCliente, int idPremio, int costoPuntos) {
        Connection conn = getConexion();
        if (conn == null) return false;

        String sqlCanje = "INSERT INTO canje_premio (id_cliente, id_premio, fecha_canje) VALUES (?, ?, CURRENT_TIMESTAMP)";
        String sqlRestar = "UPDATE cliente SET puntos_acumulados = puntos_acumulados - ? WHERE id_cliente = ?";

        try {
            conn.setAutoCommit(false);

            try (PreparedStatement psCanje = conn.prepareStatement(sqlCanje)) {
                psCanje.setInt(1, idCliente);
                psCanje.setInt(2, idPremio);
                psCanje.executeUpdate();
            }

            try (PreparedStatement psPuntos = conn.prepareStatement(sqlRestar)) {
                psPuntos.setInt(1, costoPuntos);
                psPuntos.setInt(2, idCliente);
                psPuntos.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("Error en CanjePremioDAOImpl (transacción de canje): " + e.getMessage());
            try { conn.rollback(); } catch (SQLException ex) {
                System.err.println("Error en rollback: " + ex.getMessage());
            }
            return false;
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException e) {
                System.err.println("Error al restaurar autoCommit: " + e.getMessage());
            }
        }
    }
}
