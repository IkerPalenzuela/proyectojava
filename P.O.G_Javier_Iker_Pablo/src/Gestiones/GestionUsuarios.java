package Gestiones;

import java.sql.*;
<<<<<<< HEAD

import Clases.ConectorBD;
=======
import java.util.Scanner;
>>>>>>> branch 'master' of https://github.com/IkerPalenzuela/proyectojava.git
import Clases.Usuarios;
import Clases.ConectorBD;

public class GestionUsuarios {

<<<<<<< HEAD
    // Método para registrar un usuario en la tabla
    public static void registrarUsuario(Usuarios usuario) throws SQLException {
=======
    // Método para registrar un usuario
    public static void registrarUsuario() throws SQLException {
>>>>>>> branch 'master' of https://github.com/IkerPalenzuela/proyectojava.git
        String query = "INSERT INTO Usuarios (DNI, Nombre, Apellido, Empresa, Rol, Contrasena) VALUES (?, ?, ?, ?, ?, ?)";

<<<<<<< HEAD
        // Usamos la conexión proporcionada por ConectorBD
        try (PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(query)) {
            preparedStatement.setString(1, usuario.getDni());
            preparedStatement.setString(2, usuario.getNombre());
            preparedStatement.setString(3, usuario.getApellido());
            preparedStatement.setString(4, usuario.getEmpresa());
            preparedStatement.setString(5, usuario.getRol().name());
            preparedStatement.setString(6, usuario.getContrasena());
            preparedStatement.executeUpdate();

            System.out.println("Usuario registrado");
        } catch (SQLException e) {
            System.out.println("Error al registrar el usuario: " + e.getMessage());
            e.printStackTrace();
=======
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
>>>>>>> branch 'master' of https://github.com/IkerPalenzuela/proyectojava.git
        }
    }

<<<<<<< HEAD
    // Método para consultar los usuarios
    public static void consultarTodosLosUsuarios() throws SQLException {
        System.out.println("\nLista de todos los usuarios");
        String query = "SELECT * FROM Usuarios";

        // Usamos la conexión de la clase ConectorBD
        try (Statement statement = ConectorBD.getConexion().createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Verificamos si hay usuarios para mostrar
            if (!resultSet.isBeforeFirst()) {
                System.out.println("No se encontraron usuarios en la base de datos");
            } else {
                while (resultSet.next()) {
                    // Obtenemos los datos del usuario
                    String dni = resultSet.getString("DNI");
                    String nombre = resultSet.getString("Nombre");
                    String apellido = resultSet.getString("Apellido");
                    String empresa = resultSet.getString("Empresa");
                    String rol = resultSet.getString("Rol");
                    String contrasena = resultSet.getString("Contrasena");

                    // Imprimimos la información del usuario
                    System.out.println("DNI: " + dni +
                            ", Nombre: " + nombre +
                            ", Apellido: " + apellido +
                            ", Empresa: " + empresa +
                            ", Rol: " + rol +
                            ", Contraseña: " + contrasena);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar los usuarios: " + e.getMessage());
            e.printStackTrace();
        }
        // aaaaa
=======
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
>>>>>>> branch 'master' of https://github.com/IkerPalenzuela/proyectojava.git
    }
<<<<<<< HEAD

=======
>>>>>>> branch 'master' of https://github.com/IkerPalenzuela/proyectojava.git
}
