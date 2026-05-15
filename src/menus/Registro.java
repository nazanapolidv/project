package menus;

import BLL.Usuario;
import DLL.UsuarioController;
import javax.swing.JOptionPane;

public class Registro implements Menu {

    @Override
    public void mostrar() {
        JOptionPane.showMessageDialog(null, "Bienvenido al Registro de EcoTrack");

        String email = JOptionPane.showInputDialog(null, "Ingresá tu e-mail:", "Registro", JOptionPane.PLAIN_MESSAGE);
        if (email == null || email.trim().isEmpty()) return;

        String contrasena = JOptionPane.showInputDialog(null, "Creá una contraseña:", "Registro", JOptionPane.PLAIN_MESSAGE);
        if (contrasena == null || contrasena.trim().isEmpty()) return;

        Usuario nuevoUsuario = new Usuario(email, contrasena);
        UsuarioController controller = new UsuarioController();

        boolean exito = controller.registrarUsuario(nuevoUsuario, contrasena);

        if (exito) {
            JOptionPane.showMessageDialog(null, "Te registraste de manera exitosa. Podes iniciar sesión.", "EcoTrack", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Error al registrar. El e-mail podría ya estar en uso.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}