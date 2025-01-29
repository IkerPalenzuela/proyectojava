package Menus;

import java.util.Scanner;
public class MenuAviones {

	private Scanner sc;
	
	// Constructor
	public MenuAviones() {
		this.sc = new Scanner (System.in);
	}
	
	// Menu aviones
	public int mostrarMenuAviones() {
		System.out.println("\nMenú aviones");
		System.out.println("1. Consultar aviones");
		System.out.println("2. Seleccionar avion");
		System.out.println("3. Salir");
		return leerEntero();
	}
	
	public int leerEntero() {
		while (true) {
			try {
				return Integer.parseInt(sc.nextLine());
			}catch (NumberFormatException e) {
				System.out.println("Error: Por favor, introduce un número válido");
			}
		}
	}
}
