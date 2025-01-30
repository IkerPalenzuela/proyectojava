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
        String query = "SELECT a.CodAvion, a.Fabricante, a.Modelo, " +
                       "CASE"
                       + 	"WHEN r.CodAvion IS NULL THEN 'Disponible'"
                       +	"ELSE 'Reservado'";

        /*SELECT A.CodAvion, A.Fabricante, A.Modelo, 
       CASE 
           WHEN R.CodAvion IS NULL THEN 'Disponible'
           ELSE 'Reservado'
       END AS EstadoReserva
FROM Avion A
LEFT JOIN Reserva R ON A.CodAvion = R.CodAvion;
*/
        try (Statement statement = ConectorBD.getConexion().createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Verificamos si hay aviones disponibles
            if (!resultSet.isBeforeFirst()) {
                System.out.println("No hay aviones disponibles en la base de datos.");
            } else {
                while (resultSet.next()) {
                    int codAvion = resultSet.getInt("CodAvion");
                    String fabricante = resultSet.getString("Fabricante");
                    String modelo = resultSet.getString("Modelo");
                    int estado = resultSet.getInt("EstadoReserva");

                    System.out.println("CodAvion: " + codAvion + 
                                       ", Fabricante: " + fabricante + 
                                       ", Modelo: " + modelo + 
                                       ", Estado: " + (estado == 0 ? "Disponible" : "Reservado"));
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

            // Mostramos los aviones con sus códigos
            System.out.println("Código\tFabricante\tModelo");
            while (resultSet.next()) {
                int codAvion = resultSet.getInt("CodAvion");
                String fabricante = resultSet.getString("Fabricante");
                String modelo = resultSet.getString("Modelo");

                System.out.println("CodAvion: " + codAvion +
                                   ", Fabricante: " + fabricante + 
                                   ", Modelo: " + modelo);
            }

            // Solicitamos al usuario que ponga el código del avión
            System.out.print("\nIntroduce el código del avión que quieres seleccionar: ");
            int codSeleccionado = sc.nextInt();
            sc.nextLine(); // Consumir salto de línea

            resultSet.beforeFirst();  
            boolean avionEncontrado = false;

            while (resultSet.next()) {
                if (resultSet.getInt("CodAvion") == codSeleccionado) {
                    avionEncontrado = true;

                    // Determinamos si es un avión de carga o de pasajeros
                    Aviones avion = null;
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
        } finally {
            sc.close(); // Cerramos el Scanner
        }
    }
}
