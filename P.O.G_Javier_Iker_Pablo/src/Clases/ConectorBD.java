package Clases;

import java.sql.*;

public class ConectorBD {

	private static Connection conexion;
	
	public static void conectar() {
		try {
			Class.forName("cin.mysqul.cj.jdbc.Driver");
			System.out.println("Driver cargado");
			try {
				// Establecemos conexion con la BD
				// La BD se encuentra en el localhost(en mi ordenador)
				// El usuario es root y la contraseña es 1DAW3_BBDD
				// La conexion se hace a traves del puerto 3306
				// La BD se llama pog, es la que viene por defecto en MySQL
				conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/pog", "root", "1DAW3_BBDD");
				
				System.out.println("Conexión establecida");
				
			}catch(Exception e) {
				System.out.println("Error en la conexión");
			}
		}catch(Exception e) {
			System.out.println("Error en el driver");
		}
	}
	
	public static void añadirUsuario(Usuarios usuario) throws SQLException{
		String query = "INSER INTO Usuarios (DNI, Nombre, Apellido, Rol, Empresa, Contrasena VALUES (?, ?, ?, ?, ?, ?)";
	}
}
