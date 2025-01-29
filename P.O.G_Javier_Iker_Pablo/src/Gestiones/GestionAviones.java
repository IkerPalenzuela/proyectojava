package Gestiones;

import java.sql.*;
import java.util.Scanner;
import Clases.Aviones;
import Clases.ConectorBD;

public class GestionAviones {

    // Método para consultar aviones
    public static void consultarTodosLosAviones(Aviones avion) throws SQLException {
        System.out.println("\nLista de todos los aviones");
        String query = "SELECT * FROM Avion";

        try (Statement statement = ConectorBD.getConexion().createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
            // Verificamos si hay aviones para mostrar
            if (!resultSet.isBeforeFirst()) {
                System.out.println("No se encontraron aviones para mostrar en la base de datos");
            } else {
                while (resultSet.next()) {
                    String fabricante = resultSet.getString("Fabricante");
                    String modelo = resultSet.getString("Modelo");
                    double millas = resultSet.getDouble("Rango_millas");
                    String hangar = resultSet.getString("IdHangar");

                    // Imprimimos la información del avión
                    System.out.println("Fabricante: " + fabricante +
                            ", Modelo: " + modelo +
                            ", Millas: " + millas +
                            ", Hangar: " + hangar);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar los aviones: " + e.getMessage());
        }
    }

    // Método para seleccionar un avión
    public static void seleccionarAvion(Aviones avion) throws SQLException {
        Scanner sc = new Scanner(System.in);

        // Mostramos los aviones disponibles
        System.out.println("\nSelecciona un avión:");
        String query = "SELECT CodAvion, Fabricante, Modelo FROM Avion";

        try (Statement statement = ConectorBD.getConexion().createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
            // Verificamos si hay aviones disponibles
            if (!resultSet.isBeforeFirst()) {
                System.out.println("No hay aviones disponibles.");
                return;
            }

            // Mostramos los aviones con sus codigos
            System.out.println("Código\tFabricante\tModelo");
            while (resultSet.next()) {
                int codAvion = resultSet.getInt("CodAvion"); 
                String fabricante = resultSet.getString("Fabricante");
                String modelo = resultSet.getString("Modelo");

                System.out.println(codAvion + "\t" + fabricante + "\t" + modelo);
            }

            // Solicitamos al usuario que inserte el codigo del avion
            System.out.print("\nIntroduce el código del avión que quieres seleccionar: ");
            int codSeleccionado = sc.nextInt();

            // Consultamos si existe un avión con el código proporcionado
            String checkQuery = "SELECT * FROM Avion WHERE CodAvion = ?";
            try (PreparedStatement preparedStatement = ConectorBD.getConexion().prepareStatement(checkQuery)) {
                preparedStatement.setInt(1, codSeleccionado);
                ResultSet checkResultSet = preparedStatement.executeQuery();

                // Verificamos si el avión existe
                if (checkResultSet.next()) {
                    // Aquí usamos el objeto 'avion'
                    avion.setCodigo(codSeleccionado);
                    avion.setFabricante(checkResultSet.getString("Fabricante"));
                    avion.setModelo(checkResultSet.getString("Modelo"));
                    avion.setMillas(checkResultSet.getDouble("Rango_millas"));
                    avion.setHangar(null); // Por si necesitamos insertar el hangar en el que esta (no es necesario)

                    System.out.println("\nAvión seleccionado:");
                    System.out.println("Código: " + avion.getCodigo());
                    System.out.println("Fabricante: " + avion.getFabricante());
                    System.out.println("Modelo: " + avion.getModelo());
                    System.out.println("Rango de Millas: " + avion.getMillas());
                    System.out.println("Hangar: " + avion.getHangar());
                } else {
                    System.out.println("No se encontró un avión con el código proporcionado.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar los aviones: " + e.getMessage());
        }
    }
}
