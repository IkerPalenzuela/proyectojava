package Gestiones;

import Menus.MenuPrincipal;
import Clases.ConectorBD;  // Importamos la clase que maneja la conexión

public class Main {

    public static void main(String[] args) {
        try {
            // Conectamos a la base de datos
            ConectorBD.conectar();

            // Iniciamos el menú principal
            MenuPrincipal menu = new MenuPrincipal();
            menu.iniciarMenu();  // Llamamos al método que maneja el menú principal
        } catch (Exception e) {
            // En caso de error con la conexión o el menú, mostramos el mensaje
            System.out.println("Error al conectar con la base de datos o al iniciar el menú: " + e.getMessage());
        }

        // Cerramos la conexión a la base de datos después de que se termine de ejecutar el menú
        try {
            ConectorBD.cerrarConexion();
        } catch (Exception e) {
            // Si hay algún problema al cerrar la conexión, lo mostramos
            System.out.println("Error al cerrar la conexión con la base de datos: " + e.getMessage());
        }
    }
}
