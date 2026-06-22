package vista;
import BLL.Cliente;
import BLL.Usuario;
import DAO.UsuarioDAO;
import DAO.UsuarioDAOImpl;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegistroFrame extends JFrame {

    private final JTextField txtEmail = UI.campo();
    private final JPasswordField txtPassword = UI.campoPassword();
    private final JPasswordField txtRepetir = UI.campoPassword();
    private final JTextField txtNombre = UI.campo();
    private final JTextField txtApellido = UI.campo();
    private final JTextField txtDni = UI.campo();
    private final JTextField txtDireccion = UI.campo();
    private final JTextField txtNacimiento = UI.campo();
    private final JComboBox<String> cmbPlan = new JComboBox<>(new String[] { "Comun", "Premium" });

    public RegistroFrame() {
        super("EcoTrack — Crear cuenta");
        UI.configurarFrame(this, 500, 620);
        setLayout(new BorderLayout());

        add(UI.cabecera("Crear cuenta", "Registrate para sumarte a EcoTrack"), BorderLayout.NORTH);
        add(construirFormulario(), BorderLayout.CENTER);
    }

    private JPanel construirFormulario() {
        JPanel panel = UI.contenedor();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 8, 6, 8);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;

        agregarFila(panel, g, 0, "E-mail", txtEmail);
        agregarFila(panel, g, 1, "Contraseña", txtPassword);
        agregarFila(panel, g, 2, "Repetir contraseña", txtRepetir);
        agregarFila(panel, g, 3, "Nombre o razón social", txtNombre);
        agregarFila(panel, g, 4, "Apellido", txtApellido);
        agregarFila(panel, g, 5, "DNI / CUIT", txtDni);
        agregarFila(panel, g, 6, "Dirección", txtDireccion);
        agregarFila(panel, g, 7, "Fecha de nac. (dd/MM/yyyy)", txtNacimiento);
        agregarFila(panel, g, 8, "Tipo de plan", cmbPlan);

        JButton btnRegistrar = UI.botonPrimario("Crear cuenta");
        btnRegistrar.addActionListener(e -> registrar());

        JButton btnVolver = UI.botonSecundario("Volver al login");
        btnVolver.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        g.gridx = 0; g.gridy = 9; g.gridwidth = 2;
        g.insets = new Insets(18, 8, 6, 8);
        panel.add(btnRegistrar, g);

        g.gridy = 10;
        g.insets = new Insets(2, 8, 8, 8);
        panel.add(btnVolver, g);

        getRootPane().setDefaultButton(btnRegistrar);
        return panel;
    }

    private void agregarFila(JPanel panel, GridBagConstraints g, int fila, String etiqueta, Component campo) {
        g.gridx = 0; g.gridy = fila; g.gridwidth = 1; g.weightx = 0;
        panel.add(UI.etiqueta(etiqueta), g);
        g.gridx = 1; g.weightx = 1;
        panel.add(campo, g);
    }

    private void registrar() {
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());
        String repetir = new String(txtRepetir.getPassword());
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String dni = txtDni.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String fechaStr = txtNacimiento.getText().trim();
        String plan = (String) cmbPlan.getSelectedItem();

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completá el e-mail y la contraseña.",
                    "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!password.equals(repetir)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.",
                    "Revisá los datos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty() || fechaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Completá nombre, apellido, DNI/CUIT y fecha de nacimiento.",
                    "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Date fechaNacimiento;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            fechaNacimiento = sdf.parse(fechaStr);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "La fecha debe tener el formato dd/MM/yyyy.",
                    "Dato inválido", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(email);

        Cliente cliente = new Cliente(nombre, apellido, dni, direccion, fechaNacimiento, plan);

        UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
        boolean exito = usuarioDAO.registrarUsuario(usuario, cliente, password);

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
