package Menus;

import java.sql.*;
import java.util.Scanner;
import Gestiones.GestionUsuarios;
import Menus.MenuCliente;
import Menus.MenuAdministrador;

public class MenuRegistro {
    private static Scanner sc = new Scanner(System.in);

    public static int mostrarMenuRegistro() {
        System.out.println("\nMenu de registro");
        System.out.println("1. Registrarse");
        System.out.println("2. Iniciar sesión");
        System.out.println("3. Salir");
        return leerEntero();
    }

    public static int leerEntero() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: por favor, introduce un número válido.");
            }
        }
    }

    public void menuRegistro() {
        boolean salir = false;

        while (!salir) {
            int opcion = mostrarMenuRegistro();

            switch (opcion) {
                case 1:
                    GestionUsuarios.registrarUsuario();
                    break;
                case 2:
                    String rol = GestionUsuarios.iniciarSesion();
                    if (rol != null) {
                        if (rol.equalsIgnoreCase("Cliente")) {
                            MenuCliente menuCliente = new MenuCliente();
                            menuCliente.seleccionarHangar();
                        } else if (rol.equalsIgnoreCase("Administrador")) {
                            MenuAdministrador menuAdministrador = new MenuAdministrador();
                            try {
								menuAdministrador.mostrarMenuAdministrador();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                        } else {
                            System.out.println("Rol desconocido");
                        }
                    } else {
                        System.out.println("Inicio de sesión fallido. Intenta nuevamente.");
                    }
                    break;
                case 3:
                    salir = true;
                    System.out.println("Gracias por usar el programa. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Número no válido. Intente nuevamente.");
                    break;
            }
        }
    }

    // Metodo para mostrar errores
    public void mostrarErrores(String mensaje) {
        System.out.println("Error: " + mensaje);
    }
}