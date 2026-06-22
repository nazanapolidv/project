package GUI;

import DLL.Conexion;
import vista.LoginFrame;

import javax.swing.SwingUtilities;

/**
 * Punto de entrada de EcoTrack. Inicializa la conexión a la base de datos y
 * lanza la interfaz gráfica (LoginFrame) dentro del hilo de eventos de Swing.
 */
public class Main {

    public static void main(String[] args) {
        // Inicializa la conexión (singleton) antes de mostrar la UI
        Conexion.getInstance();

        // Toda la UI de Swing debe construirse en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
