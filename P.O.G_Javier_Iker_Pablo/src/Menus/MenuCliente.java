package Menus;

import java.util.Scanner;
import Gestiones.GestionHangares;

public class MenuCliente {
    private Scanner sc;

    public MenuCliente() {
        this.sc = new Scanner(System.in);
    }

    public int mostrarMenuCliente() {
        System.out.println("\nMenuCliente: ");
        System.out.println("1. Hangares");
        System.out.println("2. Aviones");
        System.out.println("3. Salir");
        return leerEntero();
    }

    public int leerEntero() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Número no válido. Por favor, introduce un número válido.");
            }
        }
    }

    public void menuClientes() {
        boolean salir = false;

        while (!salir) {
            int opcion = mostrarMenuCliente();

            switch (opcion) {
                case 1:
                    try {
                        GestionHangares gestionHangares = new GestionHangares();
                        gestionHangares.mostrarHangares();
                    } catch (Exception e) {
                        System.out.println("Error al obtener los hangares: " + e.getMessage());
                    }
                    break;

                case 2:
                    try {
                        MenuAviones menuAviones = new MenuAviones();
                        menuAviones.mostrarMenuAviones();
                    } catch (Exception e) {
                        System.out.println("Error al obtener los aviones: " + e.getMessage());
                    }
                    break;

                case 3:
                    salir = true;
                    break;

                default:
                    System.out.println("Opción no válida. Intenta nuevamente.");
                    break;
            }
        }
        sc.close();
    }
    
    // Metodo para mostrar errores
    public void mostrarErrotres(String mensaje) {
    	System.out.println("Error:  " + mensaje);
    }
}
