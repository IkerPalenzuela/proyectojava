package Gestiones;

import java.sql.*;
import Clases.Aviones;
import Clases.ConectorBD;
import Clases.Hangar;

public class GestionAviones implements InterfazRepositorios<Aviones> {

    // Método para añadir aviones
    public static void añadirAvion(Aviones avion)  {
        String query = "INSERT INTO Avion (CodAvion, Fabricante, Modelo, Rango_millas, Precio, Capacidad_kg, Plazas, IdHangar) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(query)) {
            preparedStatement.setInt(1, avion.getCodigo());
            preparedStatement.setString(2, avion.getFabricante());
            preparedStatement.setString(3, avion.getModelo());
            preparedStatement.setDouble(4, avion.getMillas());
            preparedStatement.setDouble(5, avion.getPrecio());
            preparedStatement.setInt(6, avion.getPlazas());
            preparedStatement.setDouble(7, avion.getCapacidad());
            preparedStatement.setInt(8, avion.getHangar().getIdHangar());
            
            int filasAnadidas = preparedStatement.executeUpdate();
            if (filasAnadidas > 0) {
                System.out.println("Avión añadido exitosamente.");
                mostrarAvionesDisponibles();
            } else {
                System.out.println("Error al añadir el avión.");
            }
        } catch (SQLException e) {
			e.printStackTrace();
		}
    }

    // Método para eliminar aviones
    public static void eliminarAvion(int codigo) {
        String query = "DELETE FROM Avion WHERE CodAvion = ?";
        
        try (PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(query)) {
            preparedStatement.setInt(1, codigo);
            int filasEliminadas = preparedStatement.executeUpdate();
            
            if (filasEliminadas > 0) {
                System.out.println("Avión eliminado correctamente.");
            } else {
                System.out.println("No se pudo encontrar ningún avión con ese código.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el avión: " + e.getMessage());
        }
    }

    // Método para consultar los aviones disponibles
    public static void mostrarAvionesDisponibles(){
        String query = "SELECT a.CodAvion, a.Fabricante, a.Modelo, a.Precio AS Precio_dia, r.FechaIda, r.FechaVuelta, " +
                       "IFNULL(r.FechaIda, 'Disponible') AS EstadoReserva " +
                       "FROM Avion a " +
                       "LEFT JOIN Reserva r ON a.CodAvion = r.CodAvion";

        try (PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Error. No hay aviones.");
            } else {
                while (resultSet.next()) {
                    String codAvion = resultSet.getString("CodAvion");
                    String fabricante = resultSet.getString("Fabricante");
                    String modelo = resultSet.getString("Modelo");
                    String estado = resultSet.getString("EstadoReserva");

                    System.out.println("CodAvion: " + codAvion + 
                                       ", Fabricante: " + fabricante + 
                                       ", Modelo: " + modelo + 
                                       ", Estado: " + estado);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar los aviones: " + e.getMessage());
        }
    }

    // Método para verificar la disponibilidad de un avión
    public static boolean verificarDisponibilidadAvion(String codAvion, String fechaIda, String fechaVuelta) {
        String query = "SELECT COUNT(*) FROM Reserva WHERE CodAvion = ? AND ((FechaIda <= ? AND FechaVuelta >= ?) OR (FechaIda <= ? AND FechaVuelta >= ?))";

        try (PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(query)) {
            preparedStatement.setString(1, codAvion);
            preparedStatement.setString(2, fechaIda);
            preparedStatement.setString(3, fechaIda);
            preparedStatement.setString(4, fechaVuelta);
            preparedStatement.setString(5, fechaVuelta);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) == 0;
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar la disponibilidad del avión: " + e.getMessage());
        }
        return false;
    }

	@Override
	public void insertar(Aviones avion) {
		String query = "INSERT INTO Avion (CodAvion, Fabricante, Modelo, Rango_millas, Precio, Capacidad_kg, Plazas, IdHangar) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
 
		 try (PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(query)) {
		     preparedStatement.setInt(1, avion.getCodigo());
		     preparedStatement.setString(2, avion.getFabricante());
		     preparedStatement.setString(3, avion.getModelo());
		     preparedStatement.setDouble(4, avion.getMillas());
		     preparedStatement.setDouble(5, avion.getPrecio());
		     preparedStatement.setInt(6, avion.getPlazas());
		     preparedStatement.setDouble(7, avion.getCapacidad());
		     preparedStatement.setInt(8, avion.getHangar().getIdHangar());
		     
		     int filasAnadidas = preparedStatement.executeUpdate();
		     if (filasAnadidas > 0) {
		         System.out.println("Avión añadido exitosamente.");
		         mostrarAvionesDisponibles();
		     } else {
		         System.out.println("Error al añadir el avión.");
		     }
		 } catch (SQLException e) {
			e.printStackTrace();
		}
	}
} 

