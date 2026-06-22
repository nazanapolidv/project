package DAO;
import BLL.Usuario;
import DLL.Conexion;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsuarioDAOImpl implements UsuarioDAO {

    private Connection getConexion() {
        return Conexion.getInstance().getConexion();
    }

    @Override
    public boolean registrarUsuario(Usuario usuario, String passwordPlano) {
        Connection conn = getConexion();
        if (conn == null) return false;

        String sqlUsuario = "INSERT INTO usuario (email, password_hash) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, usuario.getEmail());
            ps.setString(2, BCrypt.hashpw(passwordPlano, BCrypt.gensalt()));
            if (ps.executeUpdate() == 0) return false;

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (!keys.next()) return false;
                int idGenerado = keys.getInt(1);

                String sqlCliente = "INSERT INTO cliente (id_cliente, puntos_acumulados, tipo_plan) VALUES (?, 0, 'basico')";
                try (PreparedStatement psC = conn.prepareStatement(sqlCliente)) {
                    psC.setInt(1, idGenerado);
                    psC.executeUpdate();
                }
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Error en UsuarioDAOImpl al registrar: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Usuario autenticarUsuario(String email, String passwordPlano) {
        Connection conn = getConexion();
        if (conn == null) return null;

        String sql = "SELECT u.id_usuario, u.email, u.password_hash, " +
                     "CASE WHEN a.id_admin IS NOT NULL THEN 'admin' " +
                     "     WHEN c.id_cliente IS NOT NULL THEN 'cliente' " +
                     "     ELSE 'desconocido' END AS rol " +
                     "FROM usuario u " +
                     "LEFT JOIN admin a ON u.id_usuario = a.id_admin " +
                     "LEFT JOIN cliente c ON u.id_usuario = c.id_cliente " +
                     "WHERE u.email = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email.trim());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hashGuardado = rs.getString("password_hash");
                    if (hashGuardado == null) return null;

                    boolean esValida;
                    // Soporta contraseñas viejas en texto plano y nuevas con BCrypt
                    if (!hashGuardado.startsWith("$2")) {
                        esValida = hashGuardado.equals(passwordPlano);
                    } else {
                        esValida = BCrypt.checkpw(passwordPlano, hashGuardado);
                    }

                    if (esValida) {
                        Usuario user = new Usuario();
                        user.setId(rs.getInt("id_usuario"));
                        String rol = rs.getString("rol");
                        if ("admin".equals(rol)) {
                            user.setEmail("admin@ecotrack.com");
                        } else {
                            user.setEmail(rs.getString("email"));
                        }
                        return user;
                    }
                }
            }
        } catch (IllegalArgumentException ex) {
            System.err.println("Se detectó una contraseña con formato antiguo: " + ex.getMessage());
        } catch (SQLException e) {
            System.err.println("Error en UsuarioDAOImpl al autenticar: " + e.getMessage());
        }
        return null;
    }

    @Override
    public int obtenerPuntosUsuario(int idUsuario) {
        Connection conn = getConexion();
        if (conn == null) return 0;

        String sql = "SELECT puntos_acumulados FROM cliente WHERE id_cliente = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("puntos_acumulados");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en UsuarioDAOImpl al obtener puntos: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public boolean reducirPuntosUsuario(int idUsuario, int puntosARestar) {
        Connection conn = getConexion();
        if (conn == null) return false;

        String sql = "UPDATE cliente SET puntos_acumulados = puntos_acumulados - ? WHERE id_cliente = ? AND puntos_acumulados >= ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, puntosARestar);
            ps.setInt(2, idUsuario);
            ps.setInt(3, puntosARestar);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error en UsuarioDAOImpl al reducir puntos: " + e.getMessage());
            return false;
        }
    }
}
