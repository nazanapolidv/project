package menus;

public class MenuEcoTrack implements Menu {

    private static MenuEcoTrack instancia;

    private MenuEcoTrack() {
    }

    public static MenuEcoTrack getInstancia() {
        if (instancia == null) {
            instancia = new MenuEcoTrack();
        }
        return instancia;
    }

    @Override
    public void menuPrincipal() {
        System.out.println("\nBienvenido al sistema de sustentabilidad EcoTrack");
        System.out.println("1- Iniciar sesión");
        System.out.println("2- Registrarse");
        System.out.println("0- Salir");
        System.out.print("Seleccione una opción: ");
    }

    @Override
    public void menuLogin() {
        System.out.println("\n--- INICIO DE SESIÓN ---");
        System.out.println("Ingrese su email: ");
    }

    @Override
    public void menuAdmin() {
        System.out.println("\n--- PANEL ADMIN ---");
    }

    @Override
    public void menuCliente() {
        System.out.println("\n--- PANEL CLIENTE ---");
    }
}
