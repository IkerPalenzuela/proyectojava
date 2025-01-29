package Clases;

import java.sql.*;

public class ConectorBD {

    public static Connection conexion;
    
    // Getter & Setter
	public static Connection getConexion() {
		return conexion;
	}

	public static void setConexion(Connection conexion) {
		ConectorBD.conexion = conexion;
	}
	
	
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
    
    // Metodo para cerrar la conexion
    public static void cerrarConexion() throws SQLException {
            conexion.close();
            System.out.println("Conexión cerrada");
    }
}
