package Gestiones;

import java.sql.*;
import java.util.Scanner;
import Clases.ConectorBD;

public class GestionUsuarios {

    // Método para registrar un usuario
    public static void registrarUsuario() {
        Scanner sc = new Scanner(System.in);
        String dni, nombre, apellido, empresa, rol, contrasena;

        System.out.println("\nRegistrar usuario");

        // Validación del DNI
        while (true) {
            System.out.print("Introduce tu DNI (8 números + 1 letra): ");
            dni = sc.nextLine();
            if (dni.matches("\\d{8}[A-Za-z]")) {
                break;
            }
            System.out.println("DNI no válido. Por favor, intentalo de nuevo.");
        }

        System.out.print("Introduce tu nombre: ");
        nombre = sc.nextLine();

        System.out.print("Introduce tu apellido: ");
        apellido = sc.nextLine();

        System.out.print("Introduce tu empresa (puede ser nulo): ");
        empresa = sc.nextLine();

        // Validación del rol
        while (true) {
            System.out.print("Introduce tu rol (administrador o cliente): ");
            rol = sc.nextLine().toLowerCase();
            if (rol.equals("administrador") || rol.equals("cliente")) {
                break;
            }
            System.out.println("Rol no válido. El rol debe ser 'administrador' o 'cliente'.");
        }

        System.out.print("Introduce tu contraseña: ");
        contrasena = sc.nextLine();

        String query = "INSERT INTO Usuarios (DNI, Nombre, Apellido, Empresa, Rol, Contrasena) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(query)) {
            preparedStatement.setString(1, dni);
            preparedStatement.setString(2, nombre);
            preparedStatement.setString(3, apellido);
            preparedStatement.setString(4, empresa);
            preparedStatement.setString(5, rol);
            preparedStatement.setString(6, contrasena);
            
            int filasAfectadas = preparedStatement.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("El usuario se ha registrado correctamente");
            } else {
                System.out.println("No se ha podido registrar el usuario.");
            }
        } catch (SQLException e) {
            System.out.println("Error al registrar el usuario: " + e.getMessage());
        }
    }

    // Método para iniciar sesión con validación
    public static String iniciarSesion() {
        Scanner sc = new Scanner(System.in);
        String nombre, contrasena;

        System.out.println("\nIniciar sesión");

        while (true) {
            System.out.print("Pon el nombre: ");
            nombre = sc.nextLine();

            System.out.print("Pon la contraseña: ");
            contrasena = sc.nextLine();

            String query = "SELECT Rol FROM Usuarios WHERE Nombre = ? AND Contrasena = ?";

            try (PreparedStatement statement = ConectorBD.getConexion().prepareStatement(query)) {
                statement.setString(1, nombre);
                statement.setString(2, contrasena);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String rol = resultSet.getString("Rol");
                        System.out.println("Inicio de sesion correcto. El rol es: " + rol);
                        return rol; 
                    } else {
                        System.out.println("El nombre o la contraseña son incorrectos. Inténtelo de nuevo.");
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error al iniciar sesión: " + e.getMessage());
                return null;
            }
        }
    }
}
