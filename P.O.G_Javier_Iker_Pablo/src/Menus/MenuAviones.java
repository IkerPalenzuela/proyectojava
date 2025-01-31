package Menus;

import java.util.Scanner;
import java.sql.SQLException;
import Gestiones.GestionAviones;

public class MenuAviones {

    private Scanner sc;
    // Constructor
    public MenuAviones() {
        this.sc = new Scanner(System.in);
    }

    // Menú aviones
    public void mostrarMenuAviones() {
        boolean atras = false;
        while (!atras) {
            System.out.println("\nMenú aviones");
            System.out.println("1. Consultar aviones");
            System.out.println("2. Reservar avión");
            System.out.println("3. Volver atrás");

            int opcion = leerEntero();
            switch (opcion) {
                case 1:
                    try {
                        GestionAviones.consultarAvionesDisponibles();
                    } catch (SQLException e) {
                        System.out.println("Error al consultar los aviones: " + e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        GestionAviones.reservarAviones();
                    } catch (SQLException e) {
                        System.out.println("Error al reservar avión: " + e.getMessage());
                    }
                    break;
                case 3:
                    atras = true;
                    break;
                default:
                    System.out.println("Error: Inserta un número válido");
            }
        }
    }

    public int leerEntero() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor, introduce un número válido");
            }
        }
    }
}
