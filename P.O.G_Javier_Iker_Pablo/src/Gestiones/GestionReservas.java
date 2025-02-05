package Gestiones;

import Clases.ConectorBD;
import Clases.Reserva;
import java.sql.*;

public class GestionReservas {

    // Metodo para hacer reserva
	public static void hacerReserva(Reserva reserva) throws SQLException{
		String query = "INSERT INTO (DNI, CodAvion, FechaIda, FechaVuelta) VALUES (?, ?, ?, ?, ?)";
		
		 try (PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(query)) {
	            preparedStatement.setString(2, reserva.getDni());
	            preparedStatement.setInt(3, reserva.getCodAvion());
	            preparedStatement.setString(4, reserva.getFechaIda());
	            preparedStatement.setString(5, reserva.getFechaVuelta());
	            preparedStatement.executeUpdate();
	            
	            System.out.println("Reserva correctamente realizada.");
	        } catch (SQLException e) {
	            System.out.println("Error al hacer la reserva: " + e.getMessage());
	        }
	    }
	
	// Metodo para consultar todas las reservas
	public static void consultarTodasReservas() throws SQLException{
		String query = "SELECT r.idreserva, r.fechaida, r.fechaida, r.dni, r.codavion, a.fabricante, a.modelo, a.rango_millas, a.precio, a.capacidad_kg, a.plazas, h.localidad " +
						"FROM reserva r JOIN avion a on r.codavion = a.codavion " +
						"JOIN hangar h on a.idhangar = h.idhangar";
		
		
	}
	// Metodo para consultar la reserva
	public static void consultarReserva(Reserva reserva) throws SQLException{
		String query = "SELECT * FROM Reserva WHERE DNI = ?";
		
		try (Statement statement = ConectorBD.getConexion().createStatement()) {
			ResultSet resultSet = statement.executeQuery(query);
			
			// Verificamos si esta hecha la reserva
			if (!resultSet.isBeforeFirst()) {
				System.out.println("No se ha encontrado la reserva");
			}else {
				while(resultSet.next()) {
					// Obtenemos todos los datos de la reserva
					int idReserva = resultSet.getInt("IdReserva");
					String dni = resultSet.getString("DNI");
					int codAvion = resultSet.getInt("CodAvion");
					String fechaIda = resultSet.getString("FechaIda");
					String fechaVuelta = resultSet.getString("FechVuelta");
					
					// Mostramos los datos de la reserva
					System.out.println("IdReserva: " + idReserva + 
							", DNI: " + dni + 
							", CodAvion: " + codAvion + 
							", FechaIda: " + fechaIda + 
							", FechaVuelta: " + fechaVuelta);
				}
			}
		} catch (SQLException e) {
			System.out.println("Error al consultar la reserva: " + e.getMessage());
		}
	}
	
	// Método para modificar una reserva
	public static void modificarReserva(Reserva reserva) throws SQLException {
	    String query = "UPDATE Reserva SET FechaIda = ?, FechaVuelta = ?, DNI = ?, CodAvion = ? WHERE IdReserva = ?";
	    
	    try (PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(query)) {
	        // Establecemos los nuevos valores en el PreparedStatement
	        preparedStatement.setString(1, reserva.getFechaIda());
	        preparedStatement.setString(2, reserva.getFechaVuelta());
	        preparedStatement.setString(3, reserva.getDni());
	        preparedStatement.setInt(4, reserva.getCodAvion());
	        preparedStatement.setInt(5, reserva.getIdReserva());
	        
	        // Ejecutamos la actualización
	        int filasModificadas = preparedStatement.executeUpdate();
	        
	        if (filasModificadas > 0) {
	            System.out.println("Reserva modificada correctamente.");
	        } else {
	            System.out.println("No se encontró la reserva con el ID proporcionado.");
	        }
	    } catch (SQLException e) {
	        System.out.println("Error al modificar la reserva: " + e.getMessage());
	    }
	}

	
	// Método para eliminar una reserva
	public static void eliminarReserva(Reserva reserva) throws SQLException {
	    String query = "DELETE FROM Reserva WHERE IdReserva = ?";
	    
	    try (PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(query)) {
	        // Establecemos el parámetro para la consulta
	        preparedStatement.setInt(1, reserva.getIdReserva());
	        
	        // Iniciamos la eliminacion de la reserva
	        int filasEliminadas = preparedStatement.executeUpdate();
	        
	        if (filasEliminadas > 0) {
	            System.out.println("Reserva eliminada correctamente.");
	        } else {
	            System.out.println("No se encontró la reserva con el ID proporcionado.");
	        }
	    } catch (SQLException e) {
	        System.out.println("Error al eliminar la reserva: " + e.getMessage());
	    }
	}


	}