package Menus;

import java.sql.*;
import java.util.Scanner;
import Gestiones.GestionUsuarios;

public class MenuRegistro {
	private Scanner sc;
	
	// Constructor
	public MenuRegistro() {
		this.sc = new Scanner(System.in);
	}
	
	// Mostramos el menu de registrarse
	public int mostrarMenuRegistro() {
		System.out.println("\nMenu de registro: ");
		System.out.println("1. Registrate");
		System.out.println("2. Iniciar sesión");
		System.out.println("3. Salir");
		return leerEntero();
	}
	
	public int leerEntero() {
		while(true) {
			try {
				return Integer.parseInt(sc.nextLine());
			}catch(NumberFormatException e) {
				System.out.println("Error: por favor, introduce un número válido: ");
			}
		}
	}
	public void menus() throws SQLException {
	    boolean salir = false;
	    
	    while (!salir) {
	        int opcion = mostrarMenuRegistro();
	        
	        switch (opcion) {
	            case 1:
	                GestionUsuarios.registrarUsuario();
	                break;
	            case 2:
	                GestionUsuarios.iniciarSesion();
	                break;
	            case 3: 
	                salir = true;
	                break;
	            default:
	                System.out.println("Número no válido. Por favor, introduce un número válido: ");
	                break;
	        }
	    }
	}
	
	public void mostrarMensajeSalida() {
		System.out.println("Gracias por usar el programa. ¡Hasta pronto!");
	}

	public void mostrarError(String mensaje) {
		System.out.println("Error: " + mensaje);
	}
}
