package vista;

import BLL.Premio;
import DLL.PremioDLL;
import DLL.UsuarioController;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

/**
 * Canje de puntos por premios. Lista los premios reales (PremioDLL) en una
 * JTable, muestra el saldo del usuario y descuenta los puntos al canjear
 * (UsuarioController), igual que el flujo original del menú de usuario.
 */
public class CanjearPuntosFrame extends JFrame {

    private final int idUsuario;
    private final String email;

    private final UsuarioController usuarioController = new UsuarioController();
    private final PremioDLL premioDLL = new PremioDLL();
    private final DefaultTableModel modelo = UI.modeloNoEditable(
            new String[] { "ID", "Premio", "Costo (pts)" });
    private final JTable tabla = new JTable(modelo);
    private final JLabel lblSaldo = new JLabel();
    private List<Premio> premios = new ArrayList<>();

    public CanjearPuntosFrame(int idUsuario, String email) {
        super("EcoTrack — Canjear puntos");
        this.idUsuario = idUsuario;
        this.email = email;

        UI.configurarFrame(this, 700, 520);
        setLayout(new BorderLayout());

        add(UI.cabecera("Canjear puntos", "Cambiá tus puntos por premios"), BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(0, 16));
        centro.setBackground(UI.FONDO);
        centro.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));
        centro.add(construirSaldo(), BorderLayout.NORTH);
        centro.add(UI.tablaConEstilo(tabla), BorderLayout.CENTER);
        centro.add(construirBotones(), BorderLayout.SOUTH);

        add(centro, BorderLayout.CENTER);
        cargarPremios();
        actualizarSaldo();
    }

    private JPanel construirSaldo() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(UI.BLANCO);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UI.BORDE),
                BorderFactory.createEmptyBorder(12, 16, 12, 16)));

        JLabel etiqueta = new JLabel("Tu saldo");
        etiqueta.setFont(UI.SUBTITULO);
        etiqueta.setForeground(UI.TEXTO_SUAVE);

        lblSaldo.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSaldo.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblSaldo.setForeground(UI.VERDE);

        card.add(etiqueta, BorderLayout.WEST);
        card.add(lblSaldo, BorderLayout.EAST);
        return card;
    }

    private JPanel construirBotones() {
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        barra.setBackground(UI.FONDO);

        JButton btnCanjear = UI.botonPrimario("Canjear premio");
        btnCanjear.addActionListener(e -> canjear());

        JButton btnVolver = UI.botonSecundario("Volver");
        btnVolver.addActionListener(e -> {
            new MenuUsuarioFrame(idUsuario, email).setVisible(true);
            dispose();
        });

        barra.add(btnVolver);
        barra.add(btnCanjear);
        return barra;
    }

    private void cargarPremios() {
        modelo.setRowCount(0);
        premios = premioDLL.listarPremios();
        for (Premio p : premios) {
            modelo.addRow(new Object[] { p.getIdPremio(), p.getDescripcion(), p.getCostoPuntos() });
        }
    }

    private void actualizarSaldo() {
        int puntos = usuarioController.obtenerPuntosUsuario(idUsuario);
        lblSaldo.setText(puntos + " pts");
    }

    private void canjear() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                    "Seleccioná un premio de la tabla.",
                    "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Premio premio = premios.get(fila);
        int costo = premio.getCostoPuntos();
        int saldo = usuarioController.obtenerPuntosUsuario(idUsuario);

        if (saldo < costo) {
            JOptionPane.showMessageDialog(this,
                    "Puntos insuficientes. Te faltan " + (costo - saldo) + " pts.",
                    "Saldo insuficiente", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (usuarioController.reducirPuntosUsuario(idUsuario, costo)) {
            JOptionPane.showMessageDialog(this,
                    "¡Canje exitoso!\nPremio: " + premio.getDescripcion() +
                            "\nSe descontaron " + costo + " pts.",
                    "Canje realizado", JOptionPane.INFORMATION_MESSAGE);
            actualizarSaldo();
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se pudo procesar el canje en la base de datos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
