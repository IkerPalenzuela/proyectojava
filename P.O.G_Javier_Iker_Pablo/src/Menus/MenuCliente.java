package Menus;

import Gestiones.GestionHangares;
import java.util.Scanner;

public class MenuCliente {

    private Scanner scanner;

    // Constructor
    public MenuCliente() {
        scanner = new Scanner(System.in);
        seleccionarHangar();
    }

    public void seleccionarHangar() {
        try {
            // Pedimos al usuario que seleccione un hangar
            System.out.println("Obteniendo lista de hangares disponibles...");
            GestionHangares gestionHangares = new GestionHangares();
            gestionHangares.mostrarHangaresDisponibles(); // Llamamos al metodo de mostrar los hangares

            // Ahora pedimos el ID del hangar
            System.out.print("Introduce el ID del hangar que deseas consultar: ");
            String idHangar = scanner.nextLine();

            // Llamamos al método de GestionHangares pasando el ID del hangar
            gestionHangares.mostrarAvionesEnHangar(idHangar);

        } catch (Exception e) {
            System.out.println("Error al obtener los hangares: " + e.getMessage());
        }
    }

    // Método para mostrar errores
    public void mostrarErrores(String mensaje) {
        System.out.println("Error: " + mensaje);
    }
}
