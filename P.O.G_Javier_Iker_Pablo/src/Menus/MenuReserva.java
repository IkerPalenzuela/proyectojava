package Menus;

import java.util.Scanner;
import Clases.Reserva;
import Gestiones.GestionReservas;
import java.sql.*;

public class MenuReserva {

    private Scanner sc;

    // Constructor
    public MenuReserva() {
        this.sc = new Scanner(System.in);
    }

    public void mostrarMenuReservas() {
        boolean atras = false;
        while (!atras) {
            // Menu de reservas
            System.out.println("\nMenú reservas");
            System.out.println("1. Hacer reserva");
            System.out.println("2. Consultar reserva");
            System.out.println("3. Modificar reserva");
            System.out.println("4. Eliminar reserva");
            System.out.println("5. Volver atrás");

            int opcion = leerEntero();
            switch (opcion) {
                case 1:
                    hacerReserva();
                    break;
                case 2:
                    consultarReserva(); 
                    break;
                case 3:
                    modificarReserva();
                    break;
                case 4:
                    eliminarReserva();
                    break;
                case 5:
                    atras = true; 
                    break;
                default:
                    System.out.println("Error: Inserta un número válido");
            }
        }
    }

    // Método para hacer una reserva
    private void hacerReserva() {
        // Recoger los datos para la reserva
        System.out.print("Inserta el ID de la reserva: ");
        int idReserva = leerEntero();

        System.out.print("Inserta el DNI del cliente: ");
        String dni = sc.nextLine();

        System.out.print("Inserta el código del avión: ");
        int codAvion = leerEntero();

        System.out.print("Inserta la fecha de ida (AAAA-MM-DD): ");
        String fechaIda = sc.nextLine();

        System.out.print("Inserta la fecha de vuelta (AAAA-MM-DD): ");
        String fechaVuelta = sc.nextLine();

        // Crear el objeto Reserva con los datos
        Reserva reserva = new Reserva(idReserva, dni, codAvion, fechaIda, fechaVuelta);

        // Llamar al método para hacer la reserva
        try {
            GestionReservas.hacerReserva(reserva);
        } catch (SQLException e) {
            System.out.println("Error al hacer la reserva: " + e.getMessage());
        }
    }

    // Método para consultar una reserva
    private void consultarReserva() {
        // Recoger el ID de la reserva
        System.out.print("Inserta el ID de la reserva a consultar: ");
        int idReserva = leerEntero();

        // Crear el objeto Reserva con el ID
        Reserva reserva = new Reserva(idReserva);

        // Llamar al método para consultar la reserva
        try {
            GestionReservas.consultarReserva(reserva);
        } catch (SQLException e) {
            System.out.println("Error al consultar la reserva: " + e.getMessage());
        }
    }

    // Método para modificar una reserva
    private void modificarReserva() {
        // Recoger los datos para la reserva a modificar
        System.out.print("Inserta el ID de la reserva a modificar: ");
        int idReserva = leerEntero();

        System.out.print("Inserta el DNI del cliente: ");
        String dni = sc.nextLine();

        System.out.print("Inserta el código del avión: ");
        int codAvion = leerEntero();

        System.out.print("Inserta la nueva fecha de ida (AAAA-MM-DD): ");
        String fechaIda = sc.nextLine();

        System.out.print("Inserta la nueva fecha de vuelta (AAAA-MM-DD): ");
        String fechaVuelta = sc.nextLine();

        // Crear el objeto Reserva con los datos
        Reserva reserva = new Reserva(idReserva, dni, codAvion, fechaIda, fechaVuelta);

        // Llamar al método para modificar la reserva
        try {
            GestionReservas.modificarReserva(reserva);
        } catch (SQLException e) {
            System.out.println("Error al modificar la reserva: " + e.getMessage());
        }
    }

    // Método para eliminar una reserva
    private void eliminarReserva() {
        // Recoger el ID de la reserva a eliminar
        System.out.print("Inserta el ID de la reserva a eliminar: ");
        int idReserva = leerEntero();

        // Crear el objeto Reserva con el ID
        Reserva reserva = new Reserva(idReserva);

        // Llamar al método para eliminar la reserva
        try {
            GestionReservas.eliminarReserva(reserva);
        } catch (SQLException e) {
            System.out.println("Error al eliminar la reserva: " + e.getMessage());
        }
    }

    // Método para leer un número entero con control de errores
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
