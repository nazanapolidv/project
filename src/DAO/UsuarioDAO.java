package DAO;
import BLL.Usuario;

public interface UsuarioDAO {

    boolean registrarUsuario(Usuario usuario, String passwordPlano);

    Usuario autenticarUsuario(String email, String passwordPlano);

    int obtenerPuntosUsuario(int idUsuario);

    boolean reducirPuntosUsuario(int idUsuario, int puntosARestar);
}
