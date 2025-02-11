package Menus;

import java.sql.SQLException;
import java.util.Scanner;

import Gestiones.GestionAviones;
import Gestiones.GestionReservas;
import Gestiones.GestionUsuarios;
import Clases.Reserva;

public class MenuAdministrador {
    private static final Scanner sc = new Scanner(System.in);

    public static void mostrarMenuAdministrador() {
        int opcion;
        do {
            System.out.println("\nMenú Administrador");
            System.out.println("1. Consultar usuarios");
            System.out.println("2. Eliminar usuario");
            System.out.println("3. Consultar reservas");
            System.out.println("4. Modificar reserva");
            System.out.println("5. Eliminar una reserva");
            System.out.println("6. Eliminar todas las reservas");
            System.out.println("7. Añadir aviones");
            System.out.println("8. Eliminar aviones");
            System.out.println("9. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    try {
                        GestionUsuarios.consultarUsuarios();
                    } catch (SQLException e) {
                        System.out.println("Error al consultar usuarios: " + e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        GestionUsuarios.eliminarUsuarios();
                    } catch (SQLException e) {
                        System.out.println("Error al eliminar usuario: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.print("Introduce el DNI del usuario para consultar sus reservas: ");
                    String dniConsulta = sc.nextLine();
                    try {
                        GestionReservas.consultarReservaUsuario(dniConsulta);
                    } catch (SQLException e) {
                        System.out.println("Error al consultar reservas: " + e.getMessage());
                    }
                    break;
                case 4:
                    // Pedimos los datos al administrador para modificar la reserva
                    System.out.print("Introduce el ID de la reserva a modificar: ");
                    int reservaId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Introduce la nueva fecha de ida: ");
                    String fechaIda = sc.nextLine();
                    System.out.print("Introduce la nueva fecha de vuelta: ");
                    String fechaVuelta = sc.nextLine();
                    System.out.print("Introduce el DNI del usuario: ");
                    String dni = sc.nextLine();
                    System.out.print("Introduce el código del avión: ");
                    int codAvion = sc.nextInt();

                    Reserva reserva = new Reserva(reservaId, dni, codAvion, fechaIda, fechaVuelta);

                    try {
                        GestionReservas.modificarReserva(reserva);
                    } catch (SQLException e) {
                        System.out.println("Error al modificar la reserva: " + e.getMessage());
                    }
                    break;
                case 5:
                    // Pedimos al administrador los datos para eliminar la reserva
                    System.out.print("Introduce el ID de la reserva a eliminar: ");
                    int idReservaEliminar = sc.nextInt();
                    sc.nextLine();

                    Reserva reservaEliminar = new Reserva(idReservaEliminar);

                    try {
                        GestionReservas.eliminarReserva(reservaEliminar);
                    } catch (SQLException e) {
                        System.out.println("Error al eliminar la reserva: " + e.getMessage());
                    }
                    break;
                case 6: 
                	try {
                		GestionReservas.eliminarTodasLasReservas();
                	}catch (SQLException e) {
                		System.out.println("Error al eliminar todas las reservas: " + e.getMessage());
                	}
                	break;
                case 7:
                    // Pedimos al administrador los datos de un avion para añadir
                    System.out.print("Introduce el código del avión: ");
                    int codigo = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Introduce el fabricante del avión: ");
                    String fabricante = sc.nextLine();
                    System.out.print("Introduce el modelo del avión: ");
                    String modelo = sc.nextLine();
                    System.out.print("Introduce las millas del avión: ");
                    double millas = sc.nextDouble();
                    System.out.print("Introduce el precio del avión: ");
                    double precio = sc.nextDouble();
                    System.out.print("Introduce el número de plazas: ");
                    int plazas = sc.nextInt();
                    System.out.print("Introduce la capacidad en kg: ");
                    double capacidad = sc.nextDouble();
                    System.out.print("Introduce el ID del hangar: ");
                    int idHangar = sc.nextInt();
                    try {
                        GestionAviones.añadirAvion(codigo, fabricante, modelo, millas, precio, plazas, capacidad, idHangar);
                    } catch (SQLException e) {
                        System.out.println("Error al añadir el avión: " + e.getMessage());
                    }
                    break;
                case 8:
                    // Pedimos al administrador el id del avion para poder eliminarlos
                    System.out.print("Introduce el código del avión a eliminar: ");
                    int codigoAvionEliminar = sc.nextInt();
                    sc.nextLine();  // Consumir el salto de línea
                    try {
                        GestionAviones.eliminarAvion(codigoAvionEliminar);
                    } catch (SQLException e) {
                        System.out.println("Error al eliminar el avión: " + e.getMessage());
                    }
                    break;
                case 9:
                    System.out.println("Saliendo del menú administrador...");
                    break;
                default:
                    System.out.println("Opción no válida, inténtelo de nuevo.");
            }
        } while (opcion != 9);
    }
}
