package Menus;

import java.util.Scanner;

public class MenuPrincipal {

	private Scanner sc;
	
	// Constructor
	public MenuPrincipal() {
		this.sc = new Scanner (System.in);
	}
	
	
	// Menu principal
	public int mostrarMenuPrincipal() {
		System.out.println("\nMenú principal");
		System.out.println("1. Registrar usuario");
		System.out.println("2. Iniciar sesión");
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
