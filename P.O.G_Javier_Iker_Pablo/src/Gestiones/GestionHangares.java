package Gestiones;
import Clases.ConectorBD;
import java.sql.*;

public class GestionHangares {

	// Metodo para mostrar los hangares
		public void mostrarHangares() throws SQLException{
			String query = "SELECT * FROM hangar";
					
			try (Statement statement = ConectorBD.getConexion().createStatement();
		             ResultSet resultSet = statement.executeQuery(query)) {
				// Verificamos si hay hangares para mostrar
				if(!resultSet.isBeforeFirst()) {
					System.out.println("No hay ningun hangar para mostrar");
				}else {
					while(resultSet.next()) {
						String idHangar = resultSet.getString("idHangar");
						int capacidadAviones = resultSet.getInt("CapacidadAviones");
						String localidad = resultSet.getString("Localidad");
						
						System.out.println("IdHangar: " + idHangar +
										    ", Capacidad de aviones: " + capacidadAviones + 
										    ", Localidad: " + localidad);
					}
				}
			}catch(SQLException e) {
				System.out.println("Error al consultar los hangares: " + e.getMessage());
			}
		}
		
}
