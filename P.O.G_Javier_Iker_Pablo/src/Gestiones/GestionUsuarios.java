
package Gestiones;

import java.sql.*;
import java.util.Scanner;
import Clases.ConectorBD;

public class GestionUsuarios {

    // Método para registrar un usuario
    public static void registrarUsuario() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\nRegistro de usuario");
        System.out.print("Introduce tu DNI: ");
        String dni = sc.nextLine();
        System.out.print("Introduce tu nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Introduce tu apellido: ");
        String apellido = sc.nextLine();
        System.out.print("Introduce tu empresa: ");
        String empresa = sc.nextLine();
        System.out.print("Introduce tu rol: ");
        String rol = sc.nextLine();
        System.out.print("Introduce tu contraseña: ");
        String contrasena = sc.nextLine();

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
                System.out.println("Usuario registrado correctamente.");
            } else {
                System.out.println("No se pudo registrar el usuario.");
            }
        } catch (SQLException e) {
            System.out.println("Error al registrar el usuario: " + e.getMessage());
        }
    }

    // Método para iniciar sesión
    public static String iniciarSesion() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\nIniciar sesión");

        System.out.print("Ingrese su nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Ingrese su contraseña: ");
        String contrasena = sc.nextLine();

        String query = "SELECT Rol FROM Usuarios WHERE Nombre = ? AND Contrasena = ?";

        try (PreparedStatement statement = ConectorBD.getConexion().prepareStatement(query)) {
            statement.setString(1, nombre);
            statement.setString(2, contrasena);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String rol = resultSet.getString("Rol");
                    System.out.println(" Inicio de sesión exitoso. Rol: " + rol);
                    return rol; 
                } else {
                    System.out.println("El nombre o la contraseña son incorrectos. Intente de nuevo.");
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al iniciar sesión: " + e.getMessage());
            return null;
        }
    }

}