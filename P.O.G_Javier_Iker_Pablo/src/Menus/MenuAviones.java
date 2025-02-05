package Menus;

import java.util.Scanner;
import java.sql.*;
import Gestiones.GestionAviones;

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
                    GestionAviones.consultarAvionesDisponibles();
                    break;
                case 2:
                    GestionAviones.reservarAvion();
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
