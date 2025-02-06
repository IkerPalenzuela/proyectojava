package Gestiones;

import Clases.ConectorBD;

import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class GestionHangares {
    private Scanner sc = new Scanner(System.in);

    public void mostrarHangaresDisponibles() throws SQLException {
        String query = "SELECT * FROM Hangar";

        try (PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No hay hangares disponibles.");
                return;
            }

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
        ArrayList<String> avionesValidos = new ArrayList<>();

        try (PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(query)) {
            preparedStatement.setString(1, idHangar);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No hay aviones en el hangar " + idHangar);
                return;
            }

            System.out.println("Aviones disponibles en el hangar " + idHangar + ":");
            while (resultSet.next()) {
                String codAvion = resultSet.getString("CodAvion");
                String modelo = resultSet.getString("Modelo");
                double precio = resultSet.getDouble("Precio");

                System.out.println(codAvion + " - Modelo: " + modelo + " - Precio: " + precio);
                avionesValidos.add(codAvion);
                
            }

            String codAvionSeleccionado;
            while (true) {
                System.out.print("Ingrese el código del avión que desea seleccionar: ");
                codAvionSeleccionado = sc.nextLine();

                if (avionesValidos.contains(codAvionSeleccionado)) { 
                    break;
                }
                System.out.println("Código de avión no válido para este hangar. Por favor, intentalo de nuevo.");
            }

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

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No se encontró el avión con código: " + codAvion);
                return;
            }

            resultSet.next();
            String fabricante = resultSet.getString("Fabricante");
            String modelo = resultSet.getString("Modelo");
            double precio = resultSet.getDouble("Precio");

            System.out.println("\nDetalles del avión seleccionado:");
            System.out.println("Código: " + codAvion);
            System.out.println("Fabricante: " + fabricante);
            System.out.println("Modelo: " + modelo);
            System.out.println("Precio: " + precio);

            String dni;
            while (true) {
                System.out.print("Introduce tu DNI para la reserva (8 números + 1 letra): ");
                dni = sc.nextLine();
                if (dni.matches("\\d{8}[A-Za-z]")) {
                    break;
                }
                System.out.println("DNI no válido. Por favor, intentalo de nuevo.");
            }

            String fechaIda;
            while (true) {
                System.out.print("Introduce la fecha de ida (YYYY-MM-DD): ");
                fechaIda = sc.nextLine();
                if (validarFecha(fechaIda)) {
                    break;
                }
                System.out.println("La fecha de ida no es válida. Por favor, intentalo de nuevo.");
            }

            String fechaVuelta;
            while (true) {
                System.out.print("Introduce la fecha de vuelta (YYYY-MM-DD): ");
                fechaVuelta = sc.nextLine();
                if (validarFecha(fechaVuelta)) {
                    break;
                }
                System.out.println("La fecha de vuelta no es válida. Por favor, intentalo de nuevo.");
            }

            if (LocalDate.parse(fechaIda).isAfter(LocalDate.parse(fechaVuelta))) {
                System.out.println("La fecha de ida no puede ser posterior a la fecha de vuelta.");
                return;
            }

            if (verificarDisponibilidadAvion(codAvion, fechaIda, fechaVuelta)) {
                realizarReserva(dni, codAvion, fechaIda, fechaVuelta);
            } else {
                System.out.println("El avión seleccionado ya está reservado en esas fechas. Intenta con otras fechas o escoge otro avión.");
            }
        } catch (SQLException e) {
            System.out.println("Error al realizar la reserva: " + e.getMessage());
        }
    }

    private boolean validarFecha(String fecha) {
        try {
            // Intentar parsear la fecha en el formato YYYY-MM-DD
            LocalDate.parse(fecha);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean validarCodAvion(String codAvion) throws SQLException {
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

    private boolean verificarDisponibilidadAvion(String codAvion, String fechaIda, String fechaVuelta) throws SQLException {
        String query = "SELECT * FROM Reserva WHERE CodAvion = ? AND (FechaIda BETWEEN ? AND ? OR FechaVuelta BETWEEN ? AND ?)";

        try (PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(query)) {
            preparedStatement.setString(1, codAvion);
            preparedStatement.setDate(2, Date.valueOf(fechaIda));
            preparedStatement.setDate(3, Date.valueOf(fechaVuelta));
            preparedStatement.setDate(4, Date.valueOf(fechaIda));
            preparedStatement.setDate(5, Date.valueOf(fechaVuelta));
            ResultSet resultSet = preparedStatement.executeQuery();

            return !resultSet.isBeforeFirst();
        }
    }

    private void realizarReserva(String dni, String codAvion, String fechaIda, String fechaVuelta) throws SQLException {
        String query = "INSERT INTO Reserva (DNI, CodAvion, FechaIda, FechaVuelta) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, dni);
            preparedStatement.setString(2, codAvion);
            preparedStatement.setDate(3, Date.valueOf(fechaIda));
            preparedStatement.setDate(4, Date.valueOf(fechaVuelta));
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int idReserva = generatedKeys.getInt(1);
                mostrarReserva(idReserva);
            }
        }
    }

    private void mostrarReserva(int idReserva) throws SQLException {
        String query = "SELECT u.Nombre, u.Apellido, h.Localidad, a.Fabricante, a.Modelo, a.Precio, r.FechaIda, r.FechaVuelta " +
                       "FROM Reserva r JOIN Usuarios u ON r.DNI = u.DNI " +
                       "JOIN Avion a ON r.CodAvion = a.CodAvion " +
                       "JOIN Hangar h ON a.IdHangar = h.IdHangar " +
                       "WHERE r.IdReserva = ?";

        try (PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(query)) {
            preparedStatement.setInt(1, idReserva);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("\nReserva confirmada:");
                System.out.println("Usuario: " + resultSet.getString("Nombre") + " " + resultSet.getString("Apellido"));
                System.out.println("Hangar: " + resultSet.getString("Localidad"));
                System.out.println("Avión: " + resultSet.getString("Fabricante") + " " + resultSet.getString("Modelo"));
                System.out.println("Fecha de ida: " + resultSet.getDate("FechaIda"));
                System.out.println("Fecha de vuelta: " + resultSet.getDate("FechaVuelta"));
                System.out.println("Precio: " + resultSet.getDouble("Precio"));
            }
        }
    }
}
