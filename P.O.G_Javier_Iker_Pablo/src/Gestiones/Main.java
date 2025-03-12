package Gestiones;

import Clases.ConectorBD;
import Menus.MenuRegistro;
import java.sql.*;

public class Main {

    public static void main(String[] args) {
            ConectorBD.conectar(); 
            new MenuRegistro().menuRegistro();  
                ConectorBD.cerrarConexion();  
    }
}