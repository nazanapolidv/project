
package menus;

import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class MenuPrincipal implements Menu {

    private static final String EMAIL_ADMIN = "admin@ecotrack.com";
    private static final String PASS_ADMIN = "admin123";
    private static final String EMAIL_USUARIO = "usuario@gmail.com";
    private static final String PASS_USUARIO = "1234";

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

        } while (seleccion != 2);

        JOptionPane.showMessageDialog(null,
                "Gracias por usar EcoTrack",
                "EcoTrack", JOptionPane.INFORMATION_MESSAGE);
    }

    private void iniciarSesion() {
        String email = JOptionPane.showInputDialog(null, "Ingrese su e-mail:", "Login", JOptionPane.PLAIN_MESSAGE);
        if (email == null)
            return;

        String contrasena = JOptionPane.showInputDialog(null, "Ingrese su contraseña:", "Login",
                JOptionPane.PLAIN_MESSAGE);
        if (contrasena == null)
            return;

        if (email.equals(EMAIL_ADMIN) && contrasena.equals(PASS_ADMIN)) {
            new MenuAdmin().mostrar();

        } else if (email.equals(EMAIL_USUARIO) && contrasena.equals(PASS_USUARIO)) {
            new MenuUsuario().mostrar();

        } else {
            JOptionPane.showMessageDialog(null,
                    "Credenciales incorrectas. Intente nuevamente.",
                    "Error de acceso", JOptionPane.ERROR_MESSAGE);
        }
    }
}
