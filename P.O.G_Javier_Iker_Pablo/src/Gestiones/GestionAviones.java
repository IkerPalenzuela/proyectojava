package Gestiones;

<<<<<<< HEAD
import java.sql.*;
import Clases.ConectorBD;
import Clases.Aviones;

public class GestionAviones {

    // Método para consultar aviones
    public static void consultarTodosLosAviones() throws SQLException {
        System.out.println("\nLista de todos los aviones");
        String query = "SELECT * FROM avion";

        // Usamos la conexión de la clase ConectorBD
        try (Statement statement = ConectorBD.getConexion().createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Verificamos si hay aviones para poder mostrar
            if (!resultSet.isBeforeFirst()) {
                System.out.println("No se encontraron aviones para mostrar en la base de datos");
            } else {
                while (resultSet.next()) {
                    String fabricante = resultSet.getString("Fabricante");
                    String modelo = resultSet.getString("Modelo");
                    double millas = resultSet.getDouble("Millas");
                    String hangar = resultSet.getString("Hangar");

                    // Imprimimos la información del avión
                    System.out.println("Fabricante: " + fabricante +
                            ", Modelo: " + modelo +
                            ", Millas: " + millas +
                            ", Hangar: " + hangar);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar los aviones: " + e.getMessage());
            e.printStackTrace();
        }
    }
=======
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Clases.Aviones;

public class GestionAviones {

	// Metodo para consultar aviones
	public static void consultarTodosLosAvioness(Aviones avion) throws SQLException {
			System.out.println("\nLista de todos los aviones");
			String query = "SELECT * FROM avion";
			
			try (Statement statement = getConexion.createStatement(); ResultSet resultSet = statement.executeQuery(query)){
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
>>>>>>> branch 'master' of https://github.com/IkerPalenzuela/proyectojava.git
}
