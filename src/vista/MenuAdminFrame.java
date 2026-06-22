package vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;

/**
 * Panel principal del Administrador. Centraliza la navegación hacia las
 * distintas funciones (cargar tarea, validar evidencias, crear evento,
 * dashboard) mediante JButton, abriendo cada una en su propia ventana.
 */
public class MenuAdminFrame extends JFrame {

    public MenuAdminFrame() {
        super("EcoTrack — Panel Administrador");
        UI.configurarFrame(this, 520, 460);
        setLayout(new BorderLayout());

        add(UI.cabecera("Panel Administrador", "Gestión de tareas, evidencias y eventos"),
                BorderLayout.NORTH);
        add(construirOpciones(), BorderLayout.CENTER);
    }

    private JPanel construirOpciones() {
        JPanel panel = UI.contenedor();
        panel.setLayout(new GridLayout(5, 1, 0, 14));

        JButton btnTarea = UI.botonPrimario("Cargar nueva tarea");
        btnTarea.addActionListener(e -> abrir(new CargarTareaFrame()));

        JButton btnEvidencias = UI.botonPrimario("Validar evidencias");
        btnEvidencias.addActionListener(e -> abrir(new ValidarEvidenciasFrame()));

        JButton btnEvento = UI.botonPrimario("Crear evento");
        btnEvento.addActionListener(e -> abrir(new CrearEventoFrame()));

        JButton btnDashboard = UI.botonPrimario("Ver dashboard");
        btnDashboard.addActionListener(e -> abrir(new DashboardFrame()));

        JButton btnSalir = UI.botonPeligro("Cerrar sesión");
        btnSalir.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        panel.add(btnTarea);
        panel.add(btnEvidencias);
        panel.add(btnEvento);
        panel.add(btnDashboard);
        panel.add(btnSalir);
        return panel;
    }

    private void abrir(JFrame ventana) {
        ventana.setVisible(true);
        dispose();
    }
}
