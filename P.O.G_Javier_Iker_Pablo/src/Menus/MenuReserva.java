package Menus;

import java.util.Scanner;

public class MenuReserva {

	private Scanner sc;
	// Constructor
	public MenuReserva(){
		this.sc = new Scanner (System.in);
	}
	
	// Menu de reservas
	public int mostrarMenuReservas() {
		System.out.println("\nMenu reservas");
		System.out.println("1. Hacer reserva");
		System.out.println("2. Consultar reserva");
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
