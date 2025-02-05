package Gestiones;

import Clases.ConectorBD;
import Menus.MenuRegistro;
import java.sql.*;

public class Main {

    public static void main(String[] args) {
        try {
            ConectorBD.conectar(); 
            new MenuRegistro().menuRegistro();  
        } catch (Exception e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        } finally {
            try {
                ConectorBD.cerrarConexion();  
            } catch (Exception e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}