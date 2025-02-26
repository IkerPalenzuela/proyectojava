package Gestiones;

import Clases.ConectorBD;

import Clases.Hangar;

import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import java.util.Scanner;


public class GestionHangares implements InterfazRepositorios<Hangar>{
    private Scanner sc = new Scanner(System.in);


    // Metodo para mostrar los hangares disponibles
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

    // Metodo para mostrar los aviones que hay en el hangar seleccionado
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

    // Metodo para mostrar los detalles de la reserva del avion
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

                // Verificamos que el DNI este bien escrito
                if (dni.matches("\\d{8}[A-Za-z]") && verificarDniEnBaseDatos(dni)) {
                    break;
                }
                System.out.println("DNI no válido o no coincide con el registrado. Por favor, intentalo de nuevo.");
            }

            String fechaIda;
            String fechaVuelta;
            boolean fechasValidas = false;
            
            while (!fechasValidas) {
                while (true) {
                    System.out.print("Introduce la fecha de ida (YYYY-MM-DD): ");
                    fechaIda = sc.nextLine();
                    if (validarFecha(fechaIda)) {
                        break;
                    }
                    System.out.println("La fecha de ida no es válida. Por favor, intentalo de nuevo.");
                }

                while (true) {
                    System.out.print("Introduce la fecha de vuelta (YYYY-MM-DD): ");
                    fechaVuelta = sc.nextLine();
                    if (validarFecha(fechaVuelta)) {
                        break;
                    }
                    System.out.println("La fecha de vuelta no es válida. Por favor, intentalo de nuevo.");
                }

                // Verificamos que la fecha de ida no sea posterior a la de vuelta
                if (compararFechas(fechaIda, fechaVuelta)) {
                    System.out.println("La fecha de ida no puede ser posterior a la fecha de vuelta. Intenta nuevamente.");
                    continue;
                }

                // Verificamos la disponivilidad del avion
                if (verificarDisponibilidadAvion(codAvion, fechaIda, fechaVuelta)) {
                    realizarReserva(dni, codAvion, fechaIda, fechaVuelta);
                    System.out.println("Reserva realizada con éxito.");
                    mostrarReserva(dni, codAvion, fechaIda, fechaVuelta);
                    System.exit(0);

                } else {
                    System.out.println("El avión seleccionado ya está reservado en esas fechas. Por favor, ingresa otras fechas.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al realizar la reserva: " + e.getMessage());
        }
    }

    // Metodo para verificar el dni en la BD
    private boolean verificarDniEnBaseDatos(String dni) throws SQLException {
        String query = "SELECT DNI FROM Usuarios WHERE DNI = ?";
        try (PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(query)) {
            preparedStatement.setString(1, dni);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
    }

    // Metodo para ver si las fechas son validas
    private boolean validarFecha(String fecha) {
        try {
            LocalDate.parse(fecha);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    // Metodo para comparar las fechas
    private boolean compararFechas(String fechaIda, String fechaVuelta) {
        LocalDate fechaIdaDate = LocalDate.parse(fechaIda);
        LocalDate fechaVueltaDate = LocalDate.parse(fechaVuelta);
        return fechaIdaDate.isAfter(fechaVueltaDate);
    }

    // Metodo para ver si el avion esta disponible
    private boolean verificarDisponibilidadAvion(String codAvion, String fechaIda, String fechaVuelta) throws SQLException {
        return GestionAviones.verificarDisponibilidadAvion(codAvion, fechaIda, fechaVuelta);
    }

    // Metodo para realizar la reserva
    public static void realizarReserva(String dni, String codAvion, String fechaIda, String fechaVuelta) throws SQLException {
        GestionReservas.realizarReserva(dni, codAvion, fechaIda, fechaVuelta);
    }
    
    // Metodo para mostrar los datos de la reserva realizada
    private void mostrarReserva(String dni, String codAvion, String fechaIda, String fechaVuelta) throws SQLException {
    	GestionReservas.mostrarReserva(dni, codAvion, fechaIda, fechaVuelta);
    }

    // Metodo para consultar las reservas de un usuario
    public void consultarReservaUsuario(String dni) throws SQLException {
        GestionReservas.consultarReservaUsuario(dni);
    }

	@Override
	public void insertar(Hangar hangar) {
		// TODO Auto-generated method stub
		
	}
}

