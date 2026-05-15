package DLL;

import BLL.Usuario;
import org.mindrot.jbcrypt.BCrypt; 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioController {

    private Connection getConexion() {
        return Conexion.getInstance().getConexion();
    }

    public boolean registrarUsuario(Usuario usuario, String passwordPlano) {
        String sql = "INSERT INTO usuario (email, password_hash) VALUES (?, ?)";

        try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
            ps.setString(1, usuario.getEmail());
            
            String hashPassword = BCrypt.hashpw(passwordPlano, BCrypt.gensalt());
            ps.setString(2, hashPassword);
            
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }

    public Usuario autenticarUsuario(String email, String passwordPlano) {
        String sql = "SELECT id_usuario, email, password_hash FROM usuario WHERE email = ?";

        try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
            ps.setString(1, email);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hashGuardado = rs.getString("password_hash");
                    
                    if (BCrypt.checkpw(passwordPlano, hashGuardado)) {
                        Usuario user = new Usuario();
                        user.setId(rs.getInt("id_usuario"));
                        user.setEmail(rs.getString("email"));
                        return user; 
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al autenticar usuario: " + e.getMessage());
        }
        return null;
    }
}