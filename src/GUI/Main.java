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
        // inicio.mostrar();
        Conexion conexion = Conexion.getInstance();

        UsuarioController uc = new UsuarioController();
        List<Usuario> usuarios = uc.listarUsuarios();
        for (Usuario u : usuarios) {
            System.out.println(u);
}
    }
}