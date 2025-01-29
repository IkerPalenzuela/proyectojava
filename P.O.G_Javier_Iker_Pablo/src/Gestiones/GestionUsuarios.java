package Gestiones;

import java.sql.*;

import Clases.ConectorBD;
import Clases.Usuarios;

public class GestionUsuarios {

    // Método para registrar un usuario en la tabla
    public static void registrarUsuario(Usuarios usuario) throws SQLException {
        String query = "INSERT INTO Usuarios (DNI, Nombre, Apellido, Empresa, Rol, Contrasena) VALUES (?, ?, ?, ?, ?, ?)";

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
        }
    }

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
    }
}
