package Gestiones;

import Clases.ConectorBD;
import Clases.Reserva;
import java.sql.*;

public class GestionReservas {

    // Metodo para hacer reserva
    public static void realizarReserva(String dni, String codAvion, String fechaIda, String fechaVuelta) throws SQLException {
        // Hacer la inserción de la reserva
        String query = "INSERT INTO Reserva (DNI, CodAvion, FechaIda, FechaVuelta) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(query)) {
            preparedStatement.setString(1, dni);
            preparedStatement.setString(2, codAvion);
            preparedStatement.setDate(3, Date.valueOf(fechaIda));
            preparedStatement.setDate(4, Date.valueOf(fechaVuelta));
            
            int filasAfectadas = preparedStatement.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Reserva realizada con éxito.");
                mostrarReserva(dni, codAvion, fechaIda, fechaVuelta); 
            } else {
                System.out.println("Error al realizar la reserva.");
            }
        }
    }

    // Metodo para mostrar los datos de la reserva realizada
    public static void mostrarReserva(String dni, String codAvion, String fechaIda, String fechaVuelta) throws SQLException {
        // Obtener datos de la reserva desde la base de datos
        String query = "SELECT u.Nombre, u.Apellido, a.Modelo, a.Precio FROM Usuarios u "
                     + "JOIN Reserva r ON u.DNI = r.DNI "
                     + "JOIN Avion a ON r.CodAvion = a.CodAvion "
                     + "WHERE u.DNI = ? AND r.CodAvion = ? AND r.FechaIda = ? AND r.FechaVuelta = ?";
        try (PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(query)) {
            preparedStatement.setString(1, dni);
            preparedStatement.setString(2, codAvion);
            preparedStatement.setString(3, fechaIda);
            preparedStatement.setString(4, fechaVuelta);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String nombre = resultSet.getString("Nombre");
                String apellido = resultSet.getString("Apellido");
                String modelo = resultSet.getString("Modelo");
                double precio = resultSet.getDouble("Precio");

                // Mostrar los detalles de la reserva
                System.out.println("\nDetalles de la reserva:");
                System.out.println("DNI: " + dni);
                System.out.println("Nombre: " + nombre);
                System.out.println("Apellido: " + apellido);
                System.out.println("Código del avión: " + codAvion);
                System.out.println("Modelo del avión: " + modelo);
                System.out.println("Fecha de ida: " + fechaIda);
                System.out.println("Fecha de vuelta: " + fechaVuelta);
                System.out.println("Precio: " + precio);
            } else {
                System.out.println("No se encontró la reserva en la base de datos.");
            }
        }
    }

    // Metodo para consultar las reservas de un usuario
    public static void consultarReservaUsuario(String dni) throws SQLException {
        String query = "SELECT r.IdReserva, r.FechaIda, r.FechaVuelta, a.Modelo, a.Precio "
                     + "FROM Reserva r "
                     + "JOIN Avion a ON r.CodAvion = a.CodAvion "
                     + "WHERE r.DNI = ?";

        try (PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(query)) {
            preparedStatement.setString(1, dni);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No tienes reservas realizadas.");
                return;
            }

            System.out.println("Tus reservas son:");
            while (resultSet.next()) {
                int idReserva = resultSet.getInt("IdReserva");
                String fechaIda = resultSet.getString("FechaIda");
                String fechaVuelta = resultSet.getString("FechaVuelta");
                String modeloAvion = resultSet.getString("Modelo");
                double precioAvion = resultSet.getDouble("Precio");

                System.out.println("Reserva ID: " + idReserva);
                System.out.println("Fecha de ida: " + fechaIda);
                System.out.println("Fecha de vuelta: " + fechaVuelta);
                System.out.println("Modelo del avión: " + modeloAvion);
                System.out.println("Precio: " + precioAvion);
                System.out.println("Gracias por usar este programa. Hasta la proxima!!!");
            }
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
            
            // Iniciamos la eliminación de la reserva
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
    
 // Realizar la reserva en la base de datos
    private static void realizarReserva(String codAvion, String fechaIda, String fechaVuelta) throws SQLException {
        String insertQuery = "INSERT INTO Reserva (CodAvion, FechaIda, FechaVuelta) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(insertQuery)) {
            preparedStatement.setString(1, codAvion);
            preparedStatement.setString(2, fechaIda);
            preparedStatement.setString(3, fechaVuelta);
            preparedStatement.executeUpdate();
        }
    }
}
