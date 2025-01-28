package Clases;

import java.sql.*;

public class ConectorBD {

    private static Connection conexion;

    public static void conectar() throws SQLException, ClassNotFoundException {
           
    	try {
             // Cargamos el driver, el driver es la libreria que nos permite conectarnos a la BD
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver cargado");
            try {
            	//Establecemos la conexion con la BD            
                //La BD se encuentra en el localhost(en mi ordenador)
            	//El usuario es root y la contraseña es 1DAW3_BBDD
                //La conexion se hace a traves del puerto 3306
                //La BD se llama pog
            	conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/pog", "root", "1DAW3_BBDD");
            		
            	System.out.println("Conexion establecida");
            		
            	}catch(Exception e) {
            		System.out.println("Error en la conexion");
            	}
            }catch(Exception e) {
            	System.out.println("Error en el driver");
            }
    }

    // Metodo para añadir un usuario a la tabla
    public static void añadirUsuario(Usuarios usuario) throws SQLException {
        String query = "INSERT INTO Usuarios (DNI, Nombre, Apellido, Empresa, Rol, Contrasena) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = conexion.prepareStatement(query)) {
                preparedStatement.setString(1, usuario.getDni());
                preparedStatement.setString(2, usuario.getNombre());
                preparedStatement.setString(3, usuario.getApellido());
                preparedStatement.setString(4, usuario.getEmpresa());
                preparedStatement.setString(5, usuario.getRol().name());
                preparedStatement.setString(6, usuario.getContrasena()); 
                preparedStatement.executeUpdate();
                
                System.out.println("Usuario añadido");
        }
    }
    
    
    // Metodo para consultar los usuarios que hay
    public static void consultarTodosLosUsuarios() throws SQLException {
    	System.out.println("\nLista de todos los Usuarios");
    	String query = "SELECT * FROM usuario";
    	
    	try (Statement statement = conexion.createStatement(); ResultSet resultSet = statement.executeQuery(query)){
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
    
	// Metodo para consultar aviones
	public static void consultarTodosLosAvioness(Aviones avion) throws SQLException {
			System.out.println("\nLista de todos los aviones");
			String query = "SELECT * FROM avion";
			
			try (Statement statement = conexion.createStatement(); ResultSet resultSet = statement.executeQuery(query)){
				// Verificamos si hay aviones para poder mostrar
				if(!resultSet.isBeforeFirst()) {
					System.out.println("No se encontraron aviones para poder mostrar en la base de datos");
				}else {
					while(resultSet.next()) {
						String fabricante = resultSet.getString("Fabricante");
	    				String modelo = resultSet.getString("Modelo");
	    				double millas = resultSet.getDouble("Millas");
	    				String hangar = resultSet.getString("Hangar");
	    				
	    				// Imprimimos la informacion del avion
	    				System.out.println("Fabricante: " + fabricante + 
	    						", Modelo: " + modelo + 
	    						", Millas: " + millas + 
	    						", Hangar: " + hangar);
					}
				}
			}catch (SQLException e) {
				System.out.println("Error al consultar los aviones: " + e.getMessage());
			}
	}
	// Metodo para cerrar la conexion
    public static void cerrarConexion() throws SQLException {
            conexion.close();
            System.out.println("Conexión cerrada");
    }
}
