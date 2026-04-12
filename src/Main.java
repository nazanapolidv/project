import java.util.Scanner;
import menus.Menu;         
import menus.MenuEcoTrack;  

public class Main {
    public static void main(String[] args) {
        
        Menu menu = MenuEcoTrack.getInstancia();
        
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        System.out.println("Iniciando el sistema de EcoTrack...");

        do {
            menu.menuPrincipal();
            
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1; 
            }

            switch (opcion) {
                case 1:
                    menu.menuLogin();
                    break;
                case 2:
                    System.out.println("\n[Redirigiendo a la pantalla de registro...]");
                    break;
                case 0:
                    System.out.println("\n¡Gracias por usar EcoTrack! Hasta pronto.");
                    break;
                default:
                    System.out.println("\nOpción no válida. Por favor, ingrese un número del menú.");
                    break;
            }
            break;
            
        } while (opcion != 0); 

        scanner.close();
    }
}