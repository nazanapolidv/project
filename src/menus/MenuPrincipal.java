package menus;

import BLL.Usuario;
import DLL.UsuarioController;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class MenuPrincipal implements Menu {

    private static final String EMAIL_ADMIN = "admin@ecotrack.com";

    @Override
    public void mostrar() {
        String[] opciones = { "Iniciar Sesión", "Registrarse", "Salir" };
        URL logoUrl = MenuPrincipal.class.getResource("/img/logo.png");
        ImageIcon logoIcon = (logoUrl != null) ? new ImageIcon(logoUrl) : null;

        int seleccion;
        do {
            seleccion = JOptionPane.showOptionDialog(
                    null,
                    "Bienvenido a EcoTrack",
                    "EcoTrack",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    logoIcon,
                    opciones, opciones[0]);

            switch (seleccion) {
                case 0 -> iniciarSesion();
                case 1 -> new Registro().mostrar();
            }

        } while (seleccion != 2 && seleccion != JOptionPane.CLOSED_OPTION);

        JOptionPane.showMessageDialog(null,
                "Gracias por usar EcoTrack",
                "EcoTrack", JOptionPane.INFORMATION_MESSAGE);
    }

    private void iniciarSesion() {
        String email = JOptionPane.showInputDialog(null, "Ingrese su e-mail:", "Login", JOptionPane.PLAIN_MESSAGE);
        if (email == null || email.trim().isEmpty()) return;

        String contrasena = JOptionPane.showInputDialog(null, "Ingrese su contraseña:", "Login", JOptionPane.PLAIN_MESSAGE);
        if (contrasena == null || contrasena.trim().isEmpty()) return;

        if (email.equalsIgnoreCase(EMAIL_ADMIN) && contrasena.equals("1234")) {
            JOptionPane.showMessageDialog(null, "¡Bienvenido Administrador!", "Acceso concedido", JOptionPane.INFORMATION_MESSAGE);
            new MenuAdmin().mostrar();
            return;
        }

        UsuarioController controller = new UsuarioController();
        Usuario usuarioLogueado = controller.autenticarUsuario(email, contrasena);

        if (usuarioLogueado != null) {
            JOptionPane.showMessageDialog(null, "¡Bienvenido " + usuarioLogueado.getEmail() + "!", "Acceso concedido", JOptionPane.INFORMATION_MESSAGE);
            new MenuUsuario().mostrar();
        } else {
            JOptionPane.showMessageDialog(null,
                    "Credenciales incorrectas. Intente nuevamente.",
                    "Error de acceso", JOptionPane.ERROR_MESSAGE);
        }
    }
}