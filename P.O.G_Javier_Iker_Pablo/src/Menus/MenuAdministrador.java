package Menus;

import java.sql.SQLException;
import java.util.Scanner;
import Gestiones.GestionAviones;
import Gestiones.GestionUsuarios;
import Gestiones.GestionHangares;
import Gestiones.GestionReservas;

public class MenuAdministrador {

	private static Scanner sc;

    // Constructor estático
    static {
        sc = new Scanner(System.in);
    }

    // Método para mostrar el menú
    public int mostrarMenuAdministrador() {
        System.out.println("\nMenuAviones");
        System.out.println("1. Consultar aviones");
        System.out.println("2. Añadir aviones");
        System.out.println("3. Eliminar aviones");
        System.out.println("4. Consultar reservas");
        System.out.println("5. Modificar reservas");
        System.out.println("6. Eliminar reservas");
        System.out.println("7. Consultar usuarios");
        System.out.println("8. Eliminar usuarios");
        System.out.println("9. Salir");
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
            int opcion = mostrarMenuAdministrador();
            
            switch(opcion) {
                case 1: 
                    GestionAviones.mostrarAvionesDisponibles();
                    break;
                case 2:
                    System.out.print("Introduce el código del avión: ");
                    int codigo = sc.nextInt();
                    sc.nextLine(); // Consumir la nueva línea

                    System.out.print("Introduce el fabricante: ");
                    String fabricante = sc.nextLine();

                    System.out.print("Introduce el modelo: ");
                    String modelo = sc.nextLine();

                    System.out.print("Introduce el rango en millas: ");
                    double millas = sc.nextDouble();

                    System.out.print("Introduce el precio: ");
                    double precio = sc.nextDouble();

                    System.out.print("Introduce la cantidad de plazas: ");
                    int plazas = sc.nextInt();

                    System.out.print("Introduce la capacidad en kg: ");
                    double capacidad = sc.nextDouble();
                    sc.nextLine(); // Consumir la nueva línea

                    System.out.print("Introduce el ID del hangar: ");
                    int idHangar = sc.nextInt();
                    sc.nextLine(); // Consumir la nueva línea

                    // Obtener el objeto Hangar según el ID (suponiendo que existe un método para esto)
                    Hangar hangar = GestionHangares.obtenerHangarPorId(idHangar);
                    if (hangar == null) {
                        System.out.println("El hangar no existe.");
                        break;
                    }

                    GestionAviones.añadirAvion(codigo, fabricante, modelo, millas, precio, plazas, capacidad, hangar);
                    break;

                    break;
                case 3: 
                	GestionAviones.eliminarAvion(null);
                    break;
                case 4: 
                	GestionReservas.consultarReservaUsuario(null);
                	break;
                case 5: 
                	GestionReservas.modificarReserva(null);
                	break;
                case 6:
                	GestionReservas.eliminarReserva(null);
                	break;
                case 7: 
                	GestionUsuarios.consultarUsuarios();
                	break;
                case 8:
                	GestionUsuarios.eliminarUsuarios();
                	break;
                case 9:
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