package Gestiones;
import Clases.ConectorBD;
import java.sql.*;
import java.util.Scanner;

public class GestionHangares {

    // Método para mostrar los hangares y permitir al usuario seleccionar uno
    public void seleccionarHangarYMostrarAviones() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        String query = "SELECT * FROM " + "hangar";

        try (Statement statement = ConectorBD.getConexion().createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Verificar si hay hangares
            if (!resultSet.isBeforeFirst()) {
                System.out.println("No hay ningún hangar para mostrar.");
                return;
            }

            // Mostrar hangares disponibles
            System.out.println("Hangares disponibles:");
            while (resultSet.next()) {
                String idHangar = resultSet.getString("idHangar");
                int capacidadAviones = resultSet.getInt("CapacidadAviones");
                String localidad = resultSet.getString("Localidad");

                System.out.println(idHangar + " - Capacidad: " + capacidadAviones + ", Localidad: " + localidad);
            }

            // Solicitar al usuario que seleccione un hangar
            System.out.print("Ingrese el ID del hangar que desea ver: ");
            String idSeleccionado = scanner.nextLine();

            // Llamar al método para mostrar aviones del hangar seleccionado
            mostrarAvionesEnHangar(idSeleccionado);

        } catch (SQLException e) {
            System.out.println("Error al consultar los hangares: " + e.getMessage());
        }
    }

    // Método para mostrar los aviones en un hangar específico
    public void mostrarAvionesEnHangar(String idHangar) throws SQLException {
        String query = "SELECT * FROM " + "avion" + " WHERE idHangar = ?";

        try (PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(query)) {
            preparedStatement.setString(1, idHangar);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Verificar si hay aviones en el hangar
            if (!resultSet.isBeforeFirst()) {
                System.out.println("No hay aviones en el hangar " + idHangar);
                return;
            }

            // Mostrar los aviones en el hangar
            System.out.println("Aviones en el hangar " + idHangar + ":");
            while (resultSet.next()) {
                String idAvion = resultSet.getString("idAvion");
                String modelo = resultSet.getString("Modelo");
                int capacidad = resultSet.getInt("Capacidad");

                System.out.println("ID: " + idAvion + ", Modelo: " + modelo + ", Capacidad: " + capacidad);
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar los aviones: " + e.getMessage());
        }
    }
}
