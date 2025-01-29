package Menus;

import java.util.Scanner;
import Clases.Usuarios;
import Clases.Rol;

public class MenuUsuarios {

    private Scanner sc;

    // Constructor
    public MenuUsuarios() {
        this.sc = new Scanner(System.in);
    }

    // Menú principal
    public int mostrarMenuPrincipal() {
        System.out.println("\nMenú principal");
        System.out.println("1. Registrar usuario");
        System.out.println("2. Iniciar sesión");
        System.out.println("3. Salir");
        return leerEntero();
    }

    // Leer un número entero con manejo de errores
    public int leerEntero() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor, introduce un número válido.");
            }
        }
    }

    // Leer texto
    public String leerTexto() {
        return sc.nextLine();
    }

    // Método para leer los datos de un nuevo usuario
    public Usuarios leerDatosUsuario() {
        System.out.println("\nAñadir un nuevo usuario");

        System.out.print("Ingrese el DNI (8 números y 1 letra): ");
        String dni = leerTexto();

        System.out.print("Ingrese el nombre: ");
        String nombre = leerTexto();

        System.out.print("Ingrese el apellido: ");
        String apellido = leerTexto();

        System.out.print("Ingrese la empresa: ");
        String empresa = leerTexto();

        System.out.print("Ingrese la contraseña: ");
        String contrasena = leerTexto();

        System.out.println("Seleccione el rol (1. ADMININSTRADOR, 2. CLIENTE): ");
        Rol rol = seleccionarRol();

        // Retornar el objeto con los datos ingresados
        return new Usuarios(dni, nombre, apellido, empresa, contrasena, rol);
    }

    // Método para seleccionar un rol
    public Rol seleccionarRol() {
        while (true) {
            int opcion = leerEntero();
            switch (opcion) {
                case 1:
                    return Rol.ADMINISTRADOR;
                case 2:
                    return Rol.CLIENTE;
                default:
                    System.out.println("Opción invalid. Inserta 1 si eres administrador o 2 si eres cliente");
            }
        }
    }
    
    // Metodo de error
    public void mostrarError(String mensaje) {
    	System.out.println("Error: " + mensaje);
    }
}
