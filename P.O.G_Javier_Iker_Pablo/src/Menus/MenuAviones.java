package Menus;

import java.util.Scanner;
import java.sql.*;
import Gestiones.GestionAviones;
import Gestiones.GestionHangares;

public class MenuAviones {

    private static Scanner sc;

    // Constructor estático
    static {
        sc = new Scanner(System.in);
    }

    // Método para mostrar el menú
    public int mostrarMenuAviones() {
        System.out.println("\nMenuAviones");
        System.out.println("1. Consultar aviones disponibles");
        System.out.println("2. Reservar aviones");
        System.out.println("3. Salir");
        return leerEntero();
    }

    // Método para leer enteros
    public int leerEntero() {
        while(true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch(NumberFormatException e) {
                System.out.println("Número no válido. Por favor introduce un número válido.");
            }
        }
    }

    // Método para mostrar el menú de aviones
    public void menuAviones() throws SQLException {
        boolean salir = false;

        while(!salir) {
            int opcion = mostrarMenuAviones();

            switch(opcion) {
                case 1:
                    GestionAviones.mostrarAvionesDisponibles();
                    break;
                case 2:
                    // Aquí se recoge la información necesaria para la reserva
                    System.out.println("Introduce el código del avión:");
                    String codigoAvion = sc.nextLine();
                    System.out.println("Introduce la fecha de ida (formato YYYY-MM-DD):");
                    String fechaIda = sc.nextLine();
                    System.out.println("Introduce la fecha de vuelta (formato YYYY-MM-DD):");
                    String fechaVuelta = sc.nextLine();
                    System.out.println("Introduce tu nombre (para la reserva):");
                    String cliente = sc.nextLine();

                    // Llamada al método de reserva con los parámetros necesarios
                    GestionHangares.realizarReserva(codigoAvion, fechaIda, fechaVuelta, cliente);
                    break;
                case 3:
                    salir = true;
                    break;
                default:
                    System.out.println("Número no válido. Por favor inserta un número válido");
                    break;
            }
        }
    }

    // Método para mostrar errores
    public void mostrarError(String mensaje) {
        System.out.println("Error: " + mensaje);
    }
}
