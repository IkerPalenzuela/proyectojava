package Gestiones;

import java.sql.*;
import Clases.ConectorBD;
import java.util.Scanner;

public class GestionAviones {

    public static void consultarAvionesDisponibles() throws SQLException {
        String query = "SELECT a.CodAvion, a.Fabricante, a.Modelo, a.Precio AS Precio_dia, r.FechaIda, r.FechaVuelta, " +
                       "IFNULL(r.FechaIda, 'Disponible') AS EstadoReserva " +
                       "FROM Avion a " +
                       "LEFT JOIN Reserva r ON a.CodAvion = r.CodAvion";

        try (Statement statement = ConectorBD.getConexion().createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

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

    public static void reservarAvion() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Selecciona un avión:");

        // Mostramos los aviones disponibles
        String query = "SELECT * FROM Avion";

        try (Statement statement = ConectorBD.getConexion().createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Verificamos si hay aviones disponibles
            if (!resultSet.isBeforeFirst()) {
                System.out.println("No hay aviones disponibles.");
                return;
            }

            // Obtenemos los datos del avión
            while (resultSet.next()) {
                String codAvion = resultSet.getString("CodAvion");
                String fabricante = resultSet.getString("Fabricante");
                String modelo = resultSet.getString("Modelo");

                // Mostramos los datos del avión
                System.out.println("CodAvion: " + codAvion + ", Fabricante: " + fabricante + ", Modelo: " + modelo);
            }

            // Solicitamos al usuario que ponga el código del avión
            System.out.print("\nIntroduce el código del avion que quieres alquilar: ");
            String codSeleccionado = sc.nextLine();

            resultSet.beforeFirst();
            boolean avionEncontrado = false;

            while (resultSet.next()) {
                if (resultSet.getString("CodAvion").equals(codSeleccionado)) {
                    avionEncontrado = true;

                    // Obtenemos los datos del avión seleccionado
                    String fabricante = resultSet.getString("Fabricante");
                    String modelo = resultSet.getString("Modelo");
                    double precio = resultSet.getDouble("Precio");

                    // Mostramos la información del avión seleccionado
                    System.out.println("\nAvión seleccionado:");
                    System.out.println("Código: " + codSeleccionado);
                    System.out.println("Fabricante: " + fabricante);
                    System.out.println("Modelo: " + modelo);
                    System.out.println("Precio: " + precio);

                    // Realizamos la reserva
                    System.out.print("\nIntroduce la fecha de ida (YYYY-MM-DD): ");
                    String fechaIda = sc.nextLine();
                    System.out.print("Introduce la fecha de vuelta (YYYY-MM-DD): ");
                    String fechaVuelta = sc.nextLine();

                    // Insertamos la reserva en la base de datos
                    String insertQuery = "INSERT INTO Reserva (CodAvion, FechaIda, FechaVuelta) VALUES (?, ?, ?)";
                    try (PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(insertQuery)) {
                        preparedStatement.setString(1, codSeleccionado);
                        preparedStatement.setString(2, fechaIda);
                        preparedStatement.setString(3, fechaVuelta);
                        preparedStatement.executeUpdate();
                    }

                    System.out.println("Reserva realizada exitosamente.");
                    break;
                }
            }

            if (!avionEncontrado) {
                System.out.println("Código del avion no es válido. Intentalo de nuevo.");
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar los aviones: " + e.getMessage());
        }
    }
}
