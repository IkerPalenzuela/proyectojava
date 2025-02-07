package Gestiones;

import java.sql.*;
import Clases.Aviones;
import Clases.ConectorBD;
import Clases.Hangar;

import java.util.Scanner;

public class GestionAviones {

	// Metodo para añadir aviones
    public static void añadirAvion(int codigo, String fabricante, String modelo, double millas, double precio, int plazas, double capacidad, Hangar idHangar) throws SQLException{
    	String query = "INSERT INTO Avion (CodAvion, Fabricante, Modelo, Rango_millas, Precio, Capacidad_kg, Plazas, IdHangar) " +
    				   "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    	
    	try (PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(query)){
    		preparedStatement.setInt(1, codigo);
    		preparedStatement.setString(2, fabricante);
    		preparedStatement.setString(3, modelo);
    		preparedStatement.setDouble(4, millas);
    		preparedStatement.setDouble(5, precio);
    		preparedStatement.setInt(6, plazas);
    		preparedStatement.setDouble(7, capacidad);
    		preparedStatement.setInt(8, idHangar.getIdHangar());
    		
    		int filasAnadidas = preparedStatement.executeUpdate();
    		if(filasAnadidas > 0) {
    			System.out.println("Avion añadido exitosamente.");
    			mostrarAvionesDisponibles();
    		}else {
    			System.out.println("Error al añadir el avion");
    		}
    	}
    }
    
    // Metodo para eliminar aviones
    public static void eliminarAvion(Aviones avion) throws SQLException{
    	String query = "DELETE FROM Avion WHERE CodAvion = ?";
    	
    	try (PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(query)) {
            // Establecemos el parámetro para la consulta
            preparedStatement.setInt(1, avion.getCodigo());
            
            // Iniciamos la eliminacion del avion
            int filasEliminadas = preparedStatement.executeUpdate();
            
            if(filasEliminadas > 0) {
            	System.out.println("Avion eliminado correctamente.");
            }else {
            	System.out.println("No se pudo encontrar ningun avion con ese código.");
            }
    	}catch(SQLException e) {
    		System.out.println("Error al eliminar el avion: " + e.getMessage());
    	}
    }
    
    // Metodo para consultar los aviones disponibles
    public static void mostrarAvionesDisponibles() throws SQLException {
        String query = "SELECT a.CodAvion, a.Fabricante, a.Modelo, a.Precio AS Precio_dia, r.FechaIda, r.FechaVuelta, " +
                       "IFNULL(r.FechaIda, 'Disponible') AS EstadoReserva " +
                       "FROM Avion a " +
                       "LEFT JOIN Reserva r ON a.CodAvion = r.CodAvion";

        try (PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery(query)) {

            // Verificamos si hay aviones disponibles
            if (!resultSet.isBeforeFirst()) {
                System.out.println("No hay aviones disponibles en la base de datos.");
            } else {
                while (resultSet.next()) {
                    // Obtenemos los datos del avión
                    String codAvion = resultSet.getString("CodAvion");
                    String fabricante = resultSet.getString("Fabricante");
                    String modelo = resultSet.getString("Modelo");
                    String estado = resultSet.getString("EstadoReserva");

                    // Mostramos los datos del avión
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

    // Metodo para reservar un avion
    public static void reservarAvion() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Selecciona un avión:");

        String query = "SELECT * FROM Avion";

        try (Statement statement = ConectorBD.getConexion().createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Verificamos si hay aviones disponibles
            if (!resultSet.isBeforeFirst()) {
                System.out.println("No hay aviones disponibles.");
                return;
            }

            // Mostramos los datos del avión
            while (resultSet.next()) {
                String codAvion = resultSet.getString("CodAvion");
                String fabricante = resultSet.getString("Fabricante");
                String modelo = resultSet.getString("Modelo");
                System.out.println("CodAvion: " + codAvion + ", Fabricante: " + fabricante + ", Modelo: " + modelo);
            }

            // Solicitamos al usuario que ponga el código del avión
            System.out.print("\nIntroduce el código del avión que quieres alquilar: ");
            String codSeleccionado = sc.nextLine();

            // Validamos si el avión existe
            if (!validarCodAvion(codSeleccionado)) {
                System.out.println("El código del avión no es válido. Intenta de nuevo.");
                return;
            }

            // Mostramos detalles del avión seleccionado
            resultSet.beforeFirst();
            boolean avionEncontrado = false;
            String fabricante = "", modelo = "";
            while (resultSet.next()) {
                if (resultSet.getString("CodAvion").equals(codSeleccionado)) {
                    avionEncontrado = true;
                    fabricante = resultSet.getString("Fabricante");
                    modelo = resultSet.getString("Modelo");
                    break;
                }
            }

            if (!avionEncontrado) {
                System.out.println("No se ha encontrado el avión con ese código.");
                return;
            }

            // Mostramos la información del avión seleccionado
            System.out.println("\nAvión seleccionado:");
            System.out.println("Código: " + codSeleccionado);
            System.out.println("Fabricante: " + fabricante);
            System.out.println("Modelo: " + modelo);

            // Solicitamos las fechas de ida y vuelta
            System.out.print("\nIntroduce la fecha de ida (YYYY-MM-DD): ");
            String fechaIda = sc.nextLine();
            System.out.print("Introduce la fecha de vuelta (YYYY-MM-DD): ");
            String fechaVuelta = sc.nextLine();

            // Validamos fechas
            if (!validarFecha(fechaIda) || !validarFecha(fechaVuelta)) {
                System.out.println("Una de las fechas no es válida. Intenta de nuevo.");
                return;
            }

            // Verificamos la disponibilidad del avión en las fechas
            if (!verificarDisponibilidadAvion(codSeleccionado, fechaIda, fechaVuelta)) {
                System.out.println("El avión seleccionado ya está reservado en esas fechas.");
                return;
            }

            // Realizamos la reserva
            realizarReserva(codSeleccionado, fechaIda, fechaVuelta);
            System.out.println("Reserva realizada exitosamente.");

        } catch (SQLException e) {
            System.out.println("Error al consultar los aviones: " + e.getMessage());
        }
    }

    // Metodo para ver si el avion esta en la base de datos
    public static boolean validarCodAvion(String codAvion) throws SQLException {
        String query = "SELECT COUNT(*) FROM Avion WHERE CodAvion = ?";
        try (PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(query)) {
            preparedStatement.setString(1, codAvion);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        }
        return false;
    }

    // Metodo para validar las fechas
    private static boolean validarFecha(String fecha) {
        try {
            Date.valueOf(fecha);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    // Metodo para verificar la disponibilidad del avion seleccionado
    public static boolean verificarDisponibilidadAvion(String codAvion, String fechaIda, String fechaVuelta) throws SQLException {
        String query = "SELECT * FROM Reserva WHERE CodAvion = ? AND (FechaIda BETWEEN ? AND ? OR FechaVuelta BETWEEN ? AND ?)";
        try (PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(query)) {
            preparedStatement.setString(1, codAvion);
            preparedStatement.setString(2, fechaIda);
            preparedStatement.setString(3, fechaVuelta);
            preparedStatement.setString(4, fechaIda);
            preparedStatement.setString(5, fechaVuelta);
            ResultSet resultSet = preparedStatement.executeQuery();
            return !resultSet.isBeforeFirst();
        }
    }

    // Metodo para realizar la reserva en la base de datos
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
