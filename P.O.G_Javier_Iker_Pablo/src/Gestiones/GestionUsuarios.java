package Gestiones;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Clases.Usuarios;

public class GestionUsuarios {

	// Metodo para registrar un usuario a la tabla
    public static void registrarUsuario(Usuarios usuario) throws SQLException {
        String query = "INSERT INTO Usuarios (DNI, Nombre, Apellido, Empresa, Rol, Contrasena) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = getConexion.prepareStatement(query)) {
                preparedStatement.setString(1, usuario.getDni());
                preparedStatement.setString(2, usuario.getNombre());
                preparedStatement.setString(3, usuario.getApellido());
                preparedStatement.setString(4, usuario.getEmpresa());
                preparedStatement.setString(5, usuario.getRol().name());
                preparedStatement.setString(6, usuario.getContrasena()); 
                preparedStatement.executeUpdate();
                
                System.out.println("Usuario registrado");
        }
    }
    
    
    // Metodo para consultar los usuarios que hay
    public static void consultarTodosLosUsuarios() throws SQLException {
    	System.out.println("\nLista de todos los Usuarios");
    	String query = "SELECT * FROM usuario";
    	
    	try (Statement statement = getConexion.createStatement(); ResultSet resultSet = statement.executeQuery(query)){
    		// Verificamos si hay usuarios para poder mostrar
    		if (!resultSet.isBeforeFirst()) {
    			System.out.println("No se encontraron usuarios en la base de datos");
    		}else {
    			while (resultSet.next()) {
    				// Obtenemos los datos del usuario
    				String dni = resultSet.getString("DNI");
    				String nombre = resultSet.getString("Nombre");
    				String apellido = resultSet.getString("Apellido");
    				String empresa = resultSet.getString("Empresa");
    				String rol = resultSet.getString("Rol");
    				String contrasena = resultSet.getString("Contrasena");
    				
    				// Imprimimos la informacion del usuario
    				System.out.println("DNI: " + dni +
    						", Nombre: " + nombre +
    						", Apellido: " + apellido + 
    						", Empresa: " + empresa + 
    						", Rol: " + rol + 
    						", Contraseña " + contrasena);
    				
    			}
    		}
    	} catch (SQLException e) {
    		System.out.println("Error al consultar los usuarios: " + e.getMessage());
    	}    		
    }
    // aaaad
}
