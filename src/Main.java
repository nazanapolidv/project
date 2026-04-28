import DLL.Conexion;
import menus.Menu;
import menus.MenuPrincipal;

public class Main {

    public static void main(String[] args) {
        Menu inicio = new MenuPrincipal();
        // inicio.mostrar();
        Conexion conexion = Conexion.getInstance();
    }
}