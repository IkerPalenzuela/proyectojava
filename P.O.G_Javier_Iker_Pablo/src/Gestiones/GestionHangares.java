package Gestiones;

import Clases.ConectorBD;
import java.util.Scanner;
import java.sql.*;

public class GestionHangares {

    // Método para mostrar los hangares disponibles
    public void mostrarHangaresDisponibles() throws SQLException {
        String query = "SELECT * FROM Hangar"; // Suponiendo que tienes una tabla llamada Hangar

        try (PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            // Verificar si hay hangares disponibles
            if (!resultSet.isBeforeFirst()) {
                System.out.println("No hay hangares disponibles.");
                return;
            }

            // Mostrar los hangares disponibles
            System.out.println("Hangares disponibles:");
            while (resultSet.next()) {
                String idHangar = resultSet.getString("IdHangar");
                String localidad = resultSet.getString("Localidad");

                System.out.println(idHangar + " - " + localidad);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar los hangares: " + e.getMessage());
        }
    }

    public void mostrarAvionesEnHangar(String idHangar) throws SQLException {
        String query = "SELECT * FROM Avion WHERE IdHangar = ?";

        try (PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(query)) {
            preparedStatement.setString(1, idHangar);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Verificar si hay aviones en el hangar
            if (!resultSet.isBeforeFirst()) {
                System.out.println("No hay aviones en el hangar " + idHangar);
                return;
            }

            // Mostrar los aviones en el hangar
            System.out.println("Aviones disponibles en el hangar " + idHangar + ":");
            while (resultSet.next()) {
                String codAvion = resultSet.getString("CodAvion");
                String modelo = resultSet.getString("Modelo");
                int capacidad = resultSet.getInt("Capacidad");

                System.out.println(codAvion + " - Modelo: " + modelo + ", Capacidad: " + capacidad);
            }

            // Solicitar al usuario que seleccione un avión
            Scanner sc = new Scanner(System.in);
            System.out.print("Ingrese el código del avión que desea seleccionar: ");
            String codAvionSeleccionado = sc.nextLine();

            // Llamar al método para mostrar los detalles del avión y luego permitir la reserva
            mostrarDetallesYReservarAvion(codAvionSeleccionado);

        } catch (SQLException e) {
            System.out.println("Error al consultar los aviones en el hangar: " + e.getMessage());
        }
    }

    public void mostrarDetallesYReservarAvion(String codAvion) throws SQLException {
        String query = "SELECT * FROM Avion WHERE CodAvion = ?";

        try (PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(query)) {
            preparedStatement.setString(1, codAvion);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Verificar si el avión existe
            if (!resultSet.isBeforeFirst()) {
                System.out.println("No se encontró el avión con código: " + codAvion);
                return;
            }

            // Obtener los detalles del avión seleccionado
            resultSet.next();
            String fabricante = resultSet.getString("Fabricante");
            String modelo = resultSet.getString("Modelo");
            double precio = resultSet.getDouble("Precio");
            int capacidad = resultSet.getInt("Capacidad");

            // Mostrar detalles del avión
            System.out.println("\nDetalles del avión seleccionado:");
            System.out.println("Código: " + codAvion);
            System.out.println("Fabricante: " + fabricante);
            System.out.println("Modelo: " + modelo);
            System.out.println("Precio: " + precio);
            System.out.println("Capacidad: " + capacidad);

            // Realizar la reserva
            Scanner sc = new Scanner(System.in);
            System.out.print("Introduce la fecha de ida (YYYY-MM-DD): ");
            String fechaIda = sc.nextLine();
            System.out.print("Introduce la fecha de vuelta (YYYY-MM-DD): ");
            String fechaVuelta = sc.nextLine();

            // Insertar la reserva
            String insertQuery = "INSERT INTO Reserva (CodAvion, FechaIda, FechaVuelta) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatementInsert = ConectorBD.getConexion().prepareStatement(insertQuery)) {
                preparedStatementInsert.setString(1, codAvion);
                preparedStatementInsert.setString(2, fechaIda);
                preparedStatementInsert.setString(3, fechaVuelta);
                preparedStatementInsert.executeUpdate();
            }

            System.out.println("Reserva realizada exitosamente.");
        } catch (SQLException e) {
            System.out.println("Error al consultar los detalles del avión: " + e.getMessage());
        }
    }
}
