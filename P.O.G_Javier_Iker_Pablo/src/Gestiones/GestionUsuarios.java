
package Gestiones;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Clases.ConectorBD;
import Clases.Usuarios;
import Clases.Rol;

public class GestionUsuarios {
    private static List<Usuarios> listaUsuarios = new ArrayList<>(); // ArrayList de los usuarios
    private static final Scanner sc = new Scanner(System.in);

    // Metodo para registrar un usuario
    public static void registrarUsuario() {
        String dni, nombre, apellido, empresa, rol, contrasena;

        System.out.println("\nRegistrar usuario");

        while (true) {
            System.out.print("Introduce tu DNI (8 números + 1 letra): ");
            dni = sc.nextLine();
            if (dni.matches("\\d{8}[A-Za-z]")) break;
            System.out.println("DNI no válido. Inténtalo de nuevo.");
        }

        System.out.print("Introduce tu nombre: ");
        nombre = sc.nextLine();

        System.out.print("Introduce tu apellido: ");
        apellido = sc.nextLine();

        System.out.print("Introduce tu empresa (puede ser nulo): ");
        empresa = sc.nextLine();
        if (empresa.isEmpty()) {
            empresa = null;
        }

        Rol rolEnum = null;
        while (rolEnum == null) {
            System.out.print("Introduce tu rol (ADMINISTRADOR o CLIENTE): ");
            try {
                rolEnum = Rol.valueOf(sc.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Rol no válido. Debe ser 'ADMINISTRADOR' o 'CLIENTE'.");
            }
        }

        System.out.print("Introduce tu contraseña: ");
        contrasena = sc.nextLine();

        Usuarios nuevoUsuario = new Usuarios(dni, nombre, apellido, empresa, contrasena, rolEnum);
        listaUsuarios.add(nuevoUsuario);

        String query = "INSERT INTO Usuarios (DNI, Nombre, Apellido, Empresa, Rol, Contrasena) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(query)) {
            preparedStatement.setString(1, dni);
            preparedStatement.setString(2, nombre);
            preparedStatement.setString(3, apellido);
            preparedStatement.setString(4, empresa);
            preparedStatement.setString(5, rolEnum.toString());
            preparedStatement.setString(6, contrasena);
            
            int filasAfectadas = preparedStatement.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("El usuario se ha registrado correctamente.");
            } else {
                System.out.println("No se ha podido registrar el usuario.");
            }
        } catch (SQLException e) {
            System.out.println("Error al registrar el usuario: " + e.getMessage());
        }
    }

    // Metodo para iniciar sesion
    public static String iniciarSesion() {
        String nombre, contrasena;

        System.out.println("\nIniciar sesión");

        while (true) {
            System.out.print("Pon el nombre: ");
            nombre = sc.nextLine();

            System.out.print("Pon la contraseña: ");
            contrasena = sc.nextLine();

            // Buscar en memoria primero
            for (Usuarios usuario : listaUsuarios) {
                if (Usuarios.getNombre().equals(nombre) && usuario.validarContrasena(contrasena)) {
                    System.out.println("Inicio de sesión correcto. El rol es: " + Usuarios.getRol());
                    return Usuarios.getRol().toString();
                }
            }

            // Buscar en la base de datos
            String query = "SELECT Rol FROM Usuarios WHERE Nombre = ? AND Contrasena = ?";
            try (PreparedStatement statement = ConectorBD.getConexion().prepareStatement(query)) {
                statement.setString(1, nombre);
                statement.setString(2, contrasena);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String rol = resultSet.getString("Rol");
                        System.out.println("Inicio de sesión correcto. El rol es: " + rol);
                        return rol;
                    } else {
                        System.out.println("Nombre o contraseña incorrectos. Inténtalo de nuevo.");
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error al iniciar sesión: " + e.getMessage());
                return null;
            }
        }
    }
    
    // Metodo para consultar usuarios
    public static void consultarUsuarios() throws SQLException{
    	String query = "SELECT * FROM Usuarios";
    	
    	try(PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(query);
    		ResultSet resultSet = preparedStatement.executeQuery(query)){

    		// Verificamos si hay usaurios en la base de datos
    		if(!resultSet.isBeforeFirst()) {
    			System.out.println("No hay usuarios disponibles en la base de datos.");
    		}else {
    			while(resultSet.next()) {
    				// Obtenemos los datos del usuario
    				String dni = resultSet.getString("DNI");
    				String nombre = resultSet.getString("Nombre");
    				String apellido = resultSet.getString("Apellido");
    				String empresa = resultSet.getString("Empresa");
    				String rol = resultSet.getString("Rol");
    				String contraseña = resultSet.getString("Contrasena");
    				
    				// Mostramos los datos del usuario
    				System.out.println("DNI: " + dni +
    									", Nombre: " + nombre +
    									", Apellido: " + apellido +
    									", empresa: " + empresa +
    									", Rol: " + rol +
    									", Contraseña: " + contraseña) ;
    			}
    		}
    	}
    }
    
    // Método para eliminar usuarios
    public static void eliminarUsuarios() throws SQLException {
        System.out.print("Introduce el DNI del usuario a eliminar: ");
        String dni = sc.nextLine();

        String query = "DELETE FROM Usuarios WHERE DNI = ?";
        
        try (PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(query)) {
            preparedStatement.setString(1, dni);
                
            int filasEliminadas = preparedStatement.executeUpdate();
            
            if (filasEliminadas > 0) {
                System.out.println("El usuario se ha eliminado correctamente.");
            } else {
                System.out.println("No se ha encontrado ningun usuario con ese 'DNI'.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el usuario: " + e.getMessage());
        }
    }

}