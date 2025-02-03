package Gestiones;

import java.sql.*;
import java.util.Scanner;
import Clases.Usuarios;
import Clases.ConectorBD;

public class GestionUsuarios {

    // Método para registrar un usuario
    public static void registrarUsuario() throws SQLException {
        String query = "INSERT INTO Usuarios (DNI, Nombre, Apellido, Empresa, Rol, Contrasena) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(query)) {
            preparedStatement.setString(1, Usuarios.getDni());
            preparedStatement.setString(2, Usuarios.getNombre());
            preparedStatement.setString(3, Usuarios.getApellido());
            preparedStatement.setString(4, Usuarios.getEmpresa());
            preparedStatement.setString(5, Usuarios.getRol().name());
            preparedStatement.setString(6, Usuarios.getContrasena()); 
            preparedStatement.executeUpdate();
            
            System.out.println("Usuario registrado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al registrar el usuario: " + e.getMessage());
        }
    }

    // Método para iniciar sesión
    public static boolean iniciarSesion() {
        Scanner sc = new Scanner(System.in);
        boolean accesoConcedido = false;

        while (!accesoConcedido) {
            System.out.print("Ingrese su nombre: ");
            String nombre = sc.nextLine();

            System.out.print("Ingrese su contraseña: ");
            String contrasena = sc.nextLine();

            // Consultamos en la base de datos para saber si estan los datos insertados
            String query = "SELECT * FROM Usuarios WHERE Nombre = ? AND Contrasena = ?";

            try (PreparedStatement statement = ConectorBD.getConexion().prepareStatement(query)) {
                statement.setString(1, nombre);
                statement.setString(2, contrasena);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        System.out.println("Inicio de sesión exitoso. Bienvenido, " + resultSet.getString("Nombre"));
                        accesoConcedido = true;
                    } else {
                        System.out.println("Nombre o contraseña incorrectos. Intente nuevamente.");
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error al iniciar sesión: " + e.getMessage());
                return false;
            }
        }
        return true;
    }
}
