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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class RegistroFrame extends JFrame {

    private final JTextField txtEmail = UI.campo();
    private final JPasswordField txtPassword = UI.campoPassword();
    private final JPasswordField txtRepetir = UI.campoPassword();

    public RegistroFrame() {
        super("EcoTrack — Crear cuenta");
        UI.configurarFrame(this, 460, 440);
        setLayout(new BorderLayout());

        add(UI.cabecera("Crear cuenta", "Registrate para sumarte a EcoTrack"), BorderLayout.NORTH);
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
        g.gridx = 1; panel.add(txtEmail, g);

        g.gridx = 0; g.gridy = 1;
        panel.add(UI.etiqueta("Contraseña"), g);
        g.gridx = 1; panel.add(txtPassword, g);

        g.gridx = 0; g.gridy = 2;
        panel.add(UI.etiqueta("Repetir contraseña"), g);
        g.gridx = 1; panel.add(txtRepetir, g);

        JButton btnRegistrar = UI.botonPrimario("Crear cuenta");
        btnRegistrar.addActionListener(e -> registrar());

        JButton btnVolver = UI.botonSecundario("Volver al login");
        btnVolver.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        g.gridx = 0; g.gridy = 3; g.gridwidth = 2;
        g.insets = new Insets(20, 8, 6, 8);
        panel.add(btnRegistrar, g);

        g.gridy = 4;
        g.insets = new Insets(2, 8, 8, 8);
        panel.add(btnVolver, g);

        getRootPane().setDefaultButton(btnRegistrar);
        return panel;
    }

    private void registrar() {
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());
        String repetir = new String(txtRepetir.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Completá el e-mail y la contraseña.",
                    "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!password.equals(repetir)) {
            JOptionPane.showMessageDialog(this,
                    "Las contraseñas no coinciden.",
                    "Revisá los datos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario nuevo = new Usuario();
        nuevo.setEmail(email);

        UsuarioDAO controller = new UsuarioDAOImpl();
        boolean exito = controller.registrarUsuario(nuevo, password);

        if (exito) {
            JOptionPane.showMessageDialog(this,
                    "Cuenta creada con éxito. Ya podés iniciar sesión.",
                    "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
            new LoginFrame().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se pudo registrar. El e-mail ya podría estar en uso o el servidor no responde.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
