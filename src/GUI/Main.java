package GUI;

import DLL.Conexion;
import vista.LoginFrame;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        Conexion.getInstance();

        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
