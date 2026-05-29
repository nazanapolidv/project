package DLL;

import BLL.Usuario;
import org.mindrot.jbcrypt.BCrypt; 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioController {

    private Connection getConexion() {
        Connection conn = Conexion.getInstance().getConexion();
        if (conn == null) {
            System.err.println("⚠️ Alerta: La conexión con MySQL no está activa.");
        }
        return conn;
    }

    public boolean registrarUsuario(Usuario usuario, String passwordPlano) {
        if (getConexion() == null) return false;

        String sql = "INSERT INTO usuario (email, password_hash) VALUES (?, ?)";

        try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
            ps.setString(1, usuario.getEmail());
            
            String hashPassword = BCrypt.hashpw(passwordPlano, BCrypt.gensalt());
            ps.setString(2, hashPassword);
            
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al registrar usuario en la BD: " + e.getMessage());
            return false;
        }
    }

    public Usuario autenticarUsuario(String email, String passwordPlano) {
        if (getConexion() == null) return null;

        // Consulta inteligente: Une las tablas hijas para detectar el rol real del usuario
        String sql = "SELECT u.id_usuario, u.email, u.password_hash, " +
                     "CASE WHEN a.id_admin IS NOT NULL THEN 'admin' " +
                     "     WHEN c.id_cliente IS NOT NULL THEN 'cliente' " +
                     "     ELSE 'desconocido' END AS rol " +
                     "FROM usuario u " +
                     "LEFT JOIN admin a ON u.id_usuario = a.id_admin " +
                     "LEFT JOIN cliente c ON u.id_usuario = c.id_cliente " +
                     "WHERE u.email = ?";

        try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
            // .trim() remueve espacios accidentales antes o después del texto
            ps.setString(1, email.trim());
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hashGuardado = rs.getString("password_hash");
                    
                    if (hashGuardado == null) {
                        return null;
                    }
                    
                    boolean esValida = false;
                    
                    // Manejo seguro de contraseñas viejas en texto plano o encriptadas con BCrypt
                    if (!hashGuardado.startsWith("$2")) {
                        esValida = hashGuardado.equals(passwordPlano);
                    } else {
                        esValida = org.mindrot.jbcrypt.BCrypt.checkpw(passwordPlano, hashGuardado);
                    }
                    
                    if (esValida) {
                        Usuario user = new Usuario();
                        user.setId(rs.getInt("id_usuario"));
                        
                        // Si la consulta detectó que es de la tabla admin, forzamos el texto para MenuPrincipal
                        String rolObtenido = rs.getString("rol");
                        if ("admin".equals(rolObtenido)) {
                            user.setEmail("admin@ecotrack.com"); 
                        } else {
                            user.setEmail(rs.getString("email"));
                        }
                        return user; 
                    }
                }
            }
        } catch (IllegalArgumentException ex) {
            System.err.println("⚠️ Se detectó una contraseña antigua, pero el escudo evitó que la app se cierre.");
        } catch (SQLException e) {
            System.err.println("Error al autenticar usuario: " + e.getMessage());
        }
        return null;
    }

    public int obtenerPuntosUsuario(int idUsuario) {
        if (getConexion() == null) return 0;

        String sql = "SELECT puntos FROM usuario WHERE id_usuario = ?";
        try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("puntos");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener puntos del usuario: " + e.getMessage());
        }
        return 0;
    }

    public boolean reducirPuntosUsuario(int idUsuario, int puntosARestar) {
        if (getConexion() == null) return false;

        String sql = "UPDATE usuario SET puntos = puntos - ? WHERE id_usuario = ? AND puntos >= ?";
        try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
            ps.setInt(1, puntosARestar);
            ps.setInt(2, idUsuario);
            ps.setInt(3, puntosARestar); 
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al restar puntos por canje: " + e.getMessage());
            return false;
        }
    }
}