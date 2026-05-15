package GUI;
import DLL.Conexion;
import DLL.UsuarioController;
import java.util.List;

import BLL.Usuario;
import menus.Menu;
import menus.MenuPrincipal;

public class Main {

    public static void main(String[] args) {
        Menu inicio = new MenuPrincipal();
        inicio.mostrar();
        Conexion conexion = Conexion.getInstance();
    }
}