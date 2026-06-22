package vista;

import BLL.Evento;
import DLL.EventoDLL;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Creación de eventos para el administrador: formulario de alta más una
 * JTable con los eventos ya publicados, que se refresca tras cada alta.
 */
public class CrearEventoFrame extends JFrame {

    private final JTextField txtTitulo = UI.campo();
    private final JTextField txtFecha = UI.campo();
    private final JTextField txtUbicacion = UI.campo();
    private final JTextField txtCupo = UI.campo();
    private final JTextField txtDescripcion = UI.campo();

    private final DefaultTableModel modelo = UI.modeloNoEditable(
            new String[] { "ID", "Título", "Fecha", "Cupo", "Ubicación" });
    private final EventoDLL eventoDLL = new EventoDLL();

    public CrearEventoFrame() {
        super("EcoTrack — Crear evento");
        UI.configurarFrame(this, 780, 580);
        setLayout(new BorderLayout());

        add(UI.cabecera("Crear evento", "Publicá un evento para la comunidad"), BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(0, 16));
        centro.setBackground(UI.FONDO);
        centro.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));
        centro.add(construirFormulario(), BorderLayout.NORTH);

        JTable tabla = new JTable(modelo);
        centro.add(UI.tablaConEstilo(tabla), BorderLayout.CENTER);
        centro.add(construirBotones(), BorderLayout.SOUTH);

        add(centro, BorderLayout.CENTER);
        cargarEventos();
    }

    private JPanel construirFormulario() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(UI.FONDO);

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 6, 6, 6);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;

        agregarFila(form, g, 0, "Título", txtTitulo);
        agregarFila(form, g, 1, "Fecha y hora (dd/MM/yyyy HH:mm)", txtFecha);
        agregarFila(form, g, 2, "Ubicación", txtUbicacion);
        agregarFila(form, g, 3, "Cupo máximo", txtCupo);
        agregarFila(form, g, 4, "Descripción", txtDescripcion);
        return form;
    }

    private void agregarFila(JPanel form, GridBagConstraints g, int fila, String etiqueta, Component campo) {
        g.gridx = 0; g.gridy = fila; g.weightx = 0;
        form.add(UI.etiqueta(etiqueta), g);
        g.gridx = 1; g.weightx = 1;
        form.add(campo, g);
    }

    private JPanel construirBotones() {
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        barra.setBackground(UI.FONDO);

        JButton btnGuardar = UI.botonPrimario("Publicar evento");
        btnGuardar.addActionListener(e -> guardarEvento());

        JButton btnVolver = UI.botonSecundario("Volver");
        btnVolver.addActionListener(e -> {
            new MenuAdminFrame().setVisible(true);
            dispose();
        });

        barra.add(btnVolver);
        barra.add(btnGuardar);
        return barra;
    }

    private void guardarEvento() {
        String titulo = txtTitulo.getText().trim();
        String fechaStr = txtFecha.getText().trim();
        String ubicacion = txtUbicacion.getText().trim();
        String cupoStr = txtCupo.getText().trim();
        String descripcion = txtDescripcion.getText().trim();

        if (titulo.isEmpty() || fechaStr.isEmpty() || cupoStr.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Completá al menos el título, la fecha y el cupo.",
                    "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int cupo = Integer.parseInt(cupoStr);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            sdf.setLenient(false);
            Date fecha = sdf.parse(fechaStr);

            Evento nuevo = new Evento(0, titulo, fecha, cupo, ubicacion, descripcion);
            if (eventoDLL.crearEvento(nuevo)) {
                JOptionPane.showMessageDialog(this, "Evento publicado correctamente.",
                        "Listo", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                cargarEventos();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo guardar el evento.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El cupo debe ser un número entero.",
                    "Dato inválido", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "La fecha debe tener el formato dd/MM/yyyy HH:mm.",
                    "Dato inválido", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarEventos() {
        modelo.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        List<Evento> eventos = eventoDLL.listarEventos();
        for (Evento ev : eventos) {
            String fecha = (ev.getFecha() != null) ? sdf.format(ev.getFecha()) : "—";
            modelo.addRow(new Object[] {
                    ev.getIdEvento(), ev.getTitulo(), fecha, ev.getCupoMaximo(), ev.getUbicacion() });
        }
    }

    private void limpiarFormulario() {
        txtTitulo.setText("");
        txtFecha.setText("");
        txtUbicacion.setText("");
        txtCupo.setText("");
        txtDescripcion.setText("");
    }
}
