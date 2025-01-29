package Gestiones;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Clases.Aviones;
import Clases.ConectorBD;

public class GestionAviones {

	// Metodo para consultar aviones
	public static void consultarTodosLosAvioness(Aviones avion) throws SQLException {
			System.out.println("\nLista de todos los aviones");
			String query = "SELECT * FROM avion";
			
			try (Statement statement = ConectorBD.getConexion().createStatement(); ResultSet resultSet = statement.executeQuery(query)){
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
}
