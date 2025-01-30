package Menus;

import java.util.Scanner;

public class MenuPrincipal {

    private Scanner sc;
    private MenuUsuarios menuUsuarios;
    private MenuReserva menuReserva;
    private MenuAviones menuAviones;

    // Constructor
    public MenuPrincipal() {
        this.sc = new Scanner(System.in);
        this.menuUsuarios = new MenuUsuarios();
        this.menuReserva = new MenuReserva();
        this.menuAviones = new MenuAviones();
    }

    // Método para iniciar el menú principal
    public void iniciarMenu() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\nMenú principal");
            System.out.println("1. Usuarios");
            System.out.println("2. Reserva");
            System.out.println("3. Aviones");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = leerEntero();
            
            switch (opcion) {
                case 1:
                    menuUsuarios.mostrarMenuUsuarios();
                    break;
                case 2:
                    menuReserva.mostrarMenuReservas();
                    break;
                case 3: 
                	menuAviones.mostrarMenuAviones();
                case 4:
                    System.out.println("Saliendo del programa...");
                    salir = true;
                    break;
                default:
                    System.out.println("Error: Por favor, introduce un número válido.");
            }
        }
    }

    // Método para leer un número entero con control de errores
    private int leerEntero() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor, introduce un número válido.");
            }
        }
    }
}
