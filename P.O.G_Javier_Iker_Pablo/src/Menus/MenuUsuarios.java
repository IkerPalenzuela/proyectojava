package Menus;

import Gestiones.GestionUsuarios;
import Clases.Usuarios;
import Clases.Rol;
import java.util.Scanner;
import java.sql.*;

public class MenuUsuarios {

    private Scanner sc;

    // Constructor
    public MenuUsuarios() {
        this.sc = new Scanner(System.in);
    }

 // Método que muestra el menú de usuarios
    public void mostrarMenuUsuarios() {
        boolean volverAtras = false;
        while (!volverAtras) {
            System.out.println("\nMenú de Usuarios");
            System.out.println("1. Registrar usuario");
            System.out.println("2. Consultar usuarios");
            System.out.println("3. Iniciar sesión");
            System.out.println("4. Volver atrás");
            System.out.print("Seleccione una opción: ");

            int opcion = leerEntero();

            switch (opcion) {
                case 1:
                    registrarUsuario();  // Llamar a registrarUsuario() para crear y registrar el usuario
                    break;
                case 2:
                    try {
                        GestionUsuarios.consultarTodosLosUsuarios(); // Consulta todos los usuarios
                    } catch (SQLException e) {
                        System.out.println("Error al consultar los usuarios: " + e.getMessage());
                    }
                    break;
                case 3:
                    // Llamar directamente a iniciarSesion sin try-catch para SQLException
                    GestionUsuarios.iniciarSesion(); // Este método maneja internamente cualquier SQLException
                    break;
                case 4:
                    volverAtras = true; // Regresar al menú principal
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }


    // Método para registrar un usuario
    private void registrarUsuario() {
        // Recoger los datos del usuario
        System.out.print("Inserta el DNI: ");
        String dni = sc.nextLine();

        System.out.print("Inserta el nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Inserta el apellido: ");
        String apellido = sc.nextLine();

        System.out.print("Inserta la empresa (puede ser null si no perteneces a ninguna): ");
        String empresa = sc.nextLine();

        // Esto es para obtener el rol bien sea ADMINISTRADOR o CLIENTE
        Rol rol = obtenerRol();

        System.out.print("Inserta la contraseña: ");
        String contrasena = sc.nextLine();

        Usuarios nuevoUsuario = new Usuarios(dni, nombre, apellido, empresa, contrasena, rol);

        try {
            GestionUsuarios.registrarUsuario(nuevoUsuario);
        } catch (SQLException e) {
            System.out.println("Error al registrar el usuario: " + e.getMessage());
        }
    }


 // Método para obtener el rol del usuario
    private Rol obtenerRol() {
        Rol rol = null;
        while (rol == null) {
            System.out.println("Selecciona el rol del usuario (1. ADMINISTRADOR, 2. CLIENTE): ");
            int opcionRol = leerEntero();
            switch (opcionRol) {
                case 1:
                    rol = Rol.ADMINISTRADOR;  // Asignamos el rol de ADMINISTRADOR
                    break;
                case 2:
                    rol = Rol.CLIENTE;  // Asignamos el rol de CLIENTE
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
        return rol;
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
