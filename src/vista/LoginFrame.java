package vista;
import BLL.Usuario;
import DAO.UsuarioDAO;
import DAO.UsuarioDAOImpl;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class LoginFrame extends JFrame {

    private static final String EMAIL_ADMIN = "admin@ecotrack.com";

    private final JTextField txtEmail = UI.campo();
    private final JPasswordField txtPassword = UI.campoPassword();

    public LoginFrame() {
        super("EcoTrack — Iniciar sesión");
        UI.configurarFrame(this, 460, 420);
        setLayout(new BorderLayout());

        add(UI.cabecera("EcoTrack", "Iniciá sesión para continuar"), BorderLayout.NORTH);
        add(construirFormulario(), BorderLayout.CENTER);
    }

    private JPanel construirFormulario() {
        JPanel panel = UI.contenedor();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 8, 8, 8);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;

        g.gridx = 0; g.gridy = 0;
        panel.add(UI.etiqueta("E-mail"), g);
        g.gridx = 1;
        panel.add(txtEmail, g);

        g.gridx = 0; g.gridy = 1;
        panel.add(UI.etiqueta("Contraseña"), g);
        g.gridx = 1;
        panel.add(txtPassword, g);

        JButton btnIngresar = UI.botonPrimario("Iniciar sesión");
        btnIngresar.addActionListener(e -> iniciarSesion());

        JButton btnRegistro = UI.botonSecundario("Crear cuenta nueva");
        btnRegistro.addActionListener(e -> {
            new RegistroFrame().setVisible(true);
            dispose();
        });

        g.gridx = 0; g.gridy = 2; g.gridwidth = 2;
        g.insets = new Insets(20, 8, 6, 8);
        panel.add(btnIngresar, g);

        g.gridy = 3;
        g.insets = new Insets(2, 8, 8, 8);
        panel.add(btnRegistro, g);
        getRootPane().setDefaultButton(btnIngresar);

        return panel;
    }

    private void iniciarSesion() {
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Completá el e-mail y la contraseña.",
                    "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (email.equalsIgnoreCase(EMAIL_ADMIN) && password.equals("1234")) {
            new MenuAdminFrame().setVisible(true);
            dispose();
            return;
        }

        UsuarioDAO controller = new UsuarioDAOImpl();
        Usuario usuario = controller.autenticarUsuario(email, password);

        if (usuario != null) {
            if (usuario.getEmail() != null && usuario.getEmail().toLowerCase().contains("admin")) {
                new MenuAdminFrame().setVisible(true);
            } else {
                new MenuUsuarioFrame(usuario.getId(), usuario.getEmail()).setVisible(true);
            }
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Credenciales incorrectas. Intentá nuevamente.",
                    "Error de acceso", JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(460, 420);
    }
}
