package Gestiones;

import java.sql.*;
import java.util.Scanner;
import Clases.Aviones;
import Clases.Carga;
import Clases.ConectorBD;
import Clases.Pasajeros;

public class GestionAviones {

    // Método para consultar los aviones disponibles
    public static void consultarAvionesDisponibles() throws SQLException {
        String query = "SELECT a.CodAvion, a.Fabricante, a.Modelo, a.Precio AS precio_dia, r.fechaIda, r.fechaVuelta," +
		        	    	"CASE" +
		                		"WHEN r.CodAvion IS NULL THEN 'Disponible'" +
		                		"ELSE 'Reservado'" +
		                	"END AS EstadoReserva" +
		                "FROM Avion a" +
		                "LEFT JOIN Reserva r ON a.CodAvion = r.CodAvion";
;

        try (Statement statement = ConectorBD.getConexion().createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Verificamos si hay aviones disponibles
            if (!resultSet.isBeforeFirst()) {
                System.out.println("No hay aviones disponibles en la base de datos.");
            } else {
                while (resultSet.next()) {
                    // Obtenemos los datos del avión
                    int codAvion = resultSet.getInt("CodAvion");
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

    // Método para reservar aviones
    public static void reservarAviones() throws SQLException {
        Scanner sc = new Scanner(System.in);

        // Mostramos los aviones disponibles
        System.out.println("\nSelecciona un avión:");
        String query = "SELECT CodAvion, Fabricante, Modelo, Plazas, Capacidad_kg, Rango_millas, Precio FROM Avion";

        try (Statement statement = ConectorBD.getConexion().createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Verificamos si hay aviones disponibles
            if (!resultSet.isBeforeFirst()) {
                System.out.println("No hay aviones disponibles.");
                return;
            }

            // Obtenemos los datos del avión
            while (resultSet.next()) {
                int codAvion = resultSet.getInt("CodAvion");
                String fabricante = resultSet.getString("Fabricante");
                String modelo = resultSet.getString("Modelo");

                // Mostramos los datos del avión
                System.out.println("CodAvion: " + codAvion +
                                   ", Fabricante: " + fabricante + 
                                   ", Modelo: " + modelo);
            }

            // Solicitamos al usuario que ponga el código del avión
            System.out.print("\nIntroduce el código del avión que quieres seleccionar: ");
            int codSeleccionado = sc.nextInt();
            sc.nextLine();

            resultSet.beforeFirst();  
            boolean avionEncontrado = false;

            while (resultSet.next()) {
                if (resultSet.getInt("CodAvion") == codSeleccionado) {
                    avionEncontrado = true;

                    // Miramos si es un avión de carga o de pasajeros
                    Aviones avion;
                    String fabricante = resultSet.getString("Fabricante");
                    String modelo = resultSet.getString("Modelo");
                    double millas = resultSet.getDouble("Rango_millas");
                    double precio = resultSet.getDouble("Precio");

                    if (resultSet.getInt("Plazas") > 0) {
                        int plazas = resultSet.getInt("Plazas");
                        avion = new Pasajeros(codSeleccionado, fabricante, modelo, precio, millas, plazas);
                    } else {
                        double capacidad = resultSet.getDouble("Capacidad_kg");
                        avion = new Carga(codSeleccionado, fabricante, modelo, precio, millas, capacidad);
                    }

                    // Mostramos la información del avión seleccionado
                    System.out.println("\nAvión seleccionado:");
                    System.out.println("Código: " + avion.getCodigo());
                    System.out.println("Fabricante: " + avion.getFabricante());
                    System.out.println("Modelo: " + avion.getModelo());
                    System.out.println("Rango de Millas: " + avion.getMillas());
                    System.out.println("Precio: " + avion.getPrecio());

                    if (avion instanceof Pasajeros) {
                        System.out.println("Plazas: " + ((Pasajeros) avion).getPlazas());
                    } else if (avion instanceof Carga) {
                        System.out.println("Capacidad de carga: " + ((Carga) avion).getCapacidad());
                    }

                    break;
                }
            }

            if (!avionEncontrado) {
                System.out.println("No se encontró un avión con el código proporcionado.");
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar los aviones: " + e.getMessage());
        }
    }
}
