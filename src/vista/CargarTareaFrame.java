package vista;

import BLL.Tarea;
import DLL.TareaDLL;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Carga de tareas para el administrador. Combina un formulario de alta
 * (JLabel + JTextField + JComboBox) con una JTable que muestra las tareas
 * ya cargadas en la base, refrescándose después de cada alta.
 */
public class CargarTareaFrame extends JFrame {

    private static final String[] EJES = { "Agua", "Energía", "Residuos", "Insumos", "Gestión Integral" };

    private final JTextField txtTitulo = UI.campo();
    private final JTextField txtDescripcion = UI.campo();
    private final JTextField txtPuntos = UI.campo();
    private final JTextField txtFecha = UI.campo();
    private final JComboBox<String> cmbEje = new JComboBox<>(EJES);

    private final DefaultTableModel modelo = UI.modeloNoEditable(
            new String[] { "ID", "Título", "Eje", "Puntos", "Vence" });
    private final TareaDLL tareaDLL = new TareaDLL();

    public CargarTareaFrame() {
        super("EcoTrack — Cargar tarea");
        UI.configurarFrame(this, 760, 560);
        setLayout(new BorderLayout());

        add(UI.cabecera("Cargar tarea", "Creá una tarea ambiental para los usuarios"), BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(0, 16));
        centro.setBackground(UI.FONDO);
        centro.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));
        centro.add(construirFormulario(), BorderLayout.NORTH);

        JTable tabla = new JTable(modelo);
        centro.add(UI.tablaConEstilo(tabla), BorderLayout.CENTER);
        centro.add(construirBotones(), BorderLayout.SOUTH);

        add(centro, BorderLayout.CENTER);
        cargarTareas();
    }

    private JPanel construirFormulario() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(UI.FONDO);

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 6, 6, 6);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;

        agregarFila(form, g, 0, "Título", txtTitulo);
        agregarFila(form, g, 1, "Descripción", txtDescripcion);
        agregarFila(form, g, 2, "Puntos a otorgar", txtPuntos);
        agregarFila(form, g, 3, "Vence (dd/MM/yyyy)", txtFecha);
        agregarFila(form, g, 4, "Eje temático", cmbEje);

        return form;
    }

    private void agregarFila(JPanel form, GridBagConstraints g, int fila, String etiqueta, java.awt.Component campo) {
        g.gridx = 0; g.gridy = fila; g.weightx = 0;
        form.add(UI.etiqueta(etiqueta), g);
        g.gridx = 1; g.weightx = 1;
        form.add(campo, g);
    }

    private JPanel construirBotones() {
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        barra.setBackground(UI.FONDO);

        JButton btnGuardar = UI.botonPrimario("Guardar tarea");
        btnGuardar.addActionListener(e -> guardarTarea());

        JButton btnVolver = UI.botonSecundario("Volver");
        btnVolver.addActionListener(e -> {
            new MenuAdminFrame().setVisible(true);
            dispose();
        });

        barra.add(btnVolver);
        barra.add(btnGuardar);
        return barra;
    }

    private void guardarTarea() {
        String titulo = txtTitulo.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String puntosStr = txtPuntos.getText().trim();
        String fechaStr = txtFecha.getText().trim();
        String eje = (String) cmbEje.getSelectedItem();

        if (titulo.isEmpty() || puntosStr.isEmpty() || fechaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Completá al menos el título, los puntos y la fecha.",
                    "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int puntos = Integer.parseInt(puntosStr);
            if (puntos < 0) {
                JOptionPane.showMessageDialog(this, "Los puntos no pueden ser negativos.",
                        "Dato inválido", JOptionPane.WARNING_MESSAGE);
                return;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            Date fechaCaducidad = sdf.parse(fechaStr);

            Tarea nueva = new Tarea(titulo, descripcion, puntos, fechaCaducidad, eje);
            if (tareaDLL.crearTarea(nueva)) {
                JOptionPane.showMessageDialog(this, "Tarea cargada y disponible para los usuarios.",
                        "Listo", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                cargarTareas();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo guardar la tarea. Intentá de nuevo.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Los puntos deben ser un número entero.",
                    "Dato inválido", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "La fecha debe tener el formato dd/MM/yyyy.",
                    "Dato inválido", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarTareas() {
        modelo.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        List<Tarea> tareas = tareaDLL.listarTareas();
        for (Tarea t : tareas) {
            String vence = (t.getFechaCaducidad() != null) ? sdf.format(t.getFechaCaducidad()) : "—";
            modelo.addRow(new Object[] {
                    t.getIdTarea(), t.getTitulo(), t.getEje(), t.getPuntosOtorgados(), vence });
        }
    }

    private void limpiarFormulario() {
        txtTitulo.setText("");
        txtDescripcion.setText("");
        txtPuntos.setText("");
        txtFecha.setText("");
        cmbEje.setSelectedIndex(0);
    }
}
