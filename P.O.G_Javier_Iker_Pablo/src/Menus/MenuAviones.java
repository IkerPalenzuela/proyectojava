package Menus;

import java.util.Scanner;
import java.sql.*;

public class MenuAviones {

	private static Scanner sc;
	
	// Constructor
	public MenuAviones() {
		this.sc = new Scanner (System.in);
	}
	
	// Menu aviones
	public static int mostrarMenuAviones() {
		System.out.println("\nMenú aviones");
		System.out.println("1. Consultar aviones");
		System.out.println("2. Reservar avion");
		System.out.println("3. Volver atras");
		return leerEntero();
	}
	
	
	public static int leerEntero() {
		while (true) {
			try {
				return Integer.parseInt(sc.nextLine());
			}catch (NumberFormatException e) {
				System.out.println("Error: Por favor, introduce un número válido");
			}
		}
	}
}
