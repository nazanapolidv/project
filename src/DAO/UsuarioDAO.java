package DAO;

import BLL.Cliente;
import BLL.Usuario;


public interface UsuarioDAO {

    boolean registrarUsuario(Usuario usuario, Cliente cliente, String passwordPlano);

    Usuario autenticarUsuario(String email, String passwordPlano);

    int obtenerPuntosUsuario(int idUsuario);

    boolean reducirPuntosUsuario(int idUsuario, int puntosARestar);
}
