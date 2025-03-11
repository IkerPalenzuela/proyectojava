package Gestiones;

import Clases.ConectorBD;
import Clases.Reserva;
import java.sql.*;
import java.util.Scanner;

public class GestionReservas {

    private static Scanner sc = new Scanner(System.in);

    // Metodo para hacer reserva con DNI
    public static void realizarReserva(String dni, String codAvion, String fechaIda, String fechaVuelta) {
        while (!validarDni(dni)) {
            System.out.print("DNI no válido. Por favor ingresa un DNI válido (8 dígitos + 1 letra): ");
            dni = sc.nextLine();
        }

        // Validación de las fechas
        while (!validarFecha(fechaIda)) {
            System.out.print("Fecha de ida no válida. Introduce la fecha de ida (YYYY-MM-DD): ");
            fechaIda = sc.nextLine();
        }
        
        while (!validarFecha(fechaVuelta)) {
            System.out.print("Fecha de vuelta no válida. Introduce la fecha de vuelta (YYYY-MM-DD): ");
            fechaVuelta = sc.nextLine();
        }
        // Verificación de la disponibilidad de la reserva
        try {
			if (verificarDisponibilidad(codAvion, fechaIda, fechaVuelta)) {
			    String query = "INSERT INTO Reserva (DNI, CodAvion, FechaIda, FechaVuelta) VALUES (?, ?, ?, ?)";
			    
			    try (PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(query)) {
			        preparedStatement.setString(1, dni);
			        preparedStatement.setString(2, codAvion);
			        preparedStatement.setDate(3, Date.valueOf(fechaIda));
			        preparedStatement.setDate(4, Date.valueOf(fechaVuelta));
			        
			        int filasAfectadas = preparedStatement.executeUpdate();
			        if (filasAfectadas > 0) {
			            System.out.println("Reserva realizada con éxito.");
			            // Eliminamos esta llamada porque ya la hace mostrarDetallesYReservarAvion
			            //mostrarReserva(dni, codAvion, fechaIda, fechaVuelta); 
			        } else {
			            System.out.println("Error al realizar la reserva.");
			        }
			    }
			} else {
			    System.out.println("El avión no está disponible en esas fechas. Intenta de nuevo con otras fechas.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    // Método para validar el DNI
    private static boolean validarDni(String dni) {
        return dni.matches("\\d{8}[A-Za-z]"); 
    }

    // Método para validar las fechas
    private static boolean validarFecha(String fecha) {
        try {
            Date.valueOf(fecha);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    // Método para verificar si el avión está disponible
    private static boolean verificarDisponibilidad(String codAvion, String fechaIda, String fechaVuelta) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return false;
    }

    // Metodo para mostrar los datos de la reserva realizada
    public static void mostrarReserva(String dni, String codAvion, String fechaIda, String fechaVuelta) {
        String query = "SELECT u.Nombre, u.Apellido, a.Modelo, a.Precio, a.Capacidad_kg, a.Plazas FROM Usuarios u "
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
                System.out.println("Gracias por usar este programa. HASTA LA PROXIMA!!!2");
            } else {
                System.out.println("No se encontró la reserva en la base de datos.");
            }
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    // Metodo para consultar las reservas de un usuario
    public static void consultarReservaUsuario(String dni) {
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
            }
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    // Método para modificar una reserva
    public static void modificarReserva(Reserva reserva) {
        String query = "UPDATE Reserva SET FechaIda = ?, FechaVuelta = ?, DNI = ?, CodAvion = ? WHERE IdReserva = ?";
        
        try (PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(query)) {
            preparedStatement.setString(1, reserva.getFechaIda());
            preparedStatement.setString(2, reserva.getFechaVuelta());
            preparedStatement.setString(3, reserva.getDni());
            preparedStatement.setInt(4, reserva.getCodAvion());
            preparedStatement.setInt(5, reserva.getIdReserva());
            
            int filasModificadas = preparedStatement.executeUpdate();
            
            if (filasModificadas > 0) {
                System.out.println("Reserva modificada correctamente.");
            } else {
                System.out.println("No se encontró la reserva con el ID proporcionado.");
            }
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    // Método para eliminar una reserva
    public static void eliminarReserva(Reserva reserva) {
        String query = "DELETE FROM Reserva WHERE IdReserva = ?";
        
        try (PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(query)) {
            preparedStatement.setInt(1, reserva.getIdReserva());
            
            int filasEliminadas = preparedStatement.executeUpdate();
            
            if (filasEliminadas > 0) {
                System.out.println("Reserva eliminada correctamente.");
            } else {
                System.out.println("No se encontró la reserva con el ID proporcionado.");
            }
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    // Metodo para eliminar todas las reservas con su ID AUTO_INCREMENT
    public static void eliminarTodasLasReservas() {
        String query = "TRUNCATE TABLE reserva";

        try (PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(query)) {
            preparedStatement.executeUpdate();
            System.out.println("Se han eliminado todas las reservas correctamente.");
        } catch (SQLException e) {
            System.out.println("Ocurrió un error al eliminar las reservas: " + e.getMessage());
        }
    }

}