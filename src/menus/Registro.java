package menus;

import javax.swing.JOptionPane;

public class Registro implements Menu {

    @Override
    public void mostrar() {
        JOptionPane.showMessageDialog(null,
                "Formulario de Registro",
                "Registro", JOptionPane.INFORMATION_MESSAGE);

        pedirDatosPersonales();
        pedirPlan();

        JOptionPane.showMessageDialog(null,
                "Ya podes iniciar sesión con mail y contraseña.",
                "Registro", JOptionPane.INFORMATION_MESSAGE);
    }

    private void pedirDatosPersonales() {
        JOptionPane.showInputDialog(null, "Nombre:", "Registro", JOptionPane.PLAIN_MESSAGE);
        JOptionPane.showInputDialog(null, "Apellido:", "Registro", JOptionPane.PLAIN_MESSAGE);
        JOptionPane.showInputDialog(null, "DNI / CUIT:", "Registro", JOptionPane.PLAIN_MESSAGE);
        JOptionPane.showInputDialog(null, "Dirección:", "Registro", JOptionPane.PLAIN_MESSAGE);
        JOptionPane.showInputDialog(null, "Fecha de nacimiento (dd/mm/aaaa):", "Registro", JOptionPane.PLAIN_MESSAGE);
        JOptionPane.showInputDialog(null, "E-mail:", "Registro", JOptionPane.PLAIN_MESSAGE);
        JOptionPane.showInputDialog(null, "Contraseña:", "Registro", JOptionPane.PLAIN_MESSAGE);
    }

    private void pedirPlan() {
        String[] planes = { "Plan Gratuito", "Plan Empresa" };
        JOptionPane.showOptionDialog(null,
                "Seleccioná tu plan:",
                "Selección de Plan",
                0, JOptionPane.QUESTION_MESSAGE, null,
                planes, planes[0]);
    }
}
