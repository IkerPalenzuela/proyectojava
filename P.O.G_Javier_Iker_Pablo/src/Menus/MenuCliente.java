package Menus;

import java.util.Scanner;
import java.sql.*;
import Clases.Aviones;
import Clases.Hangar;

public class MenuCliente {
	private Scanner sc;
	
	public MenuCliente() {
		this.sc = new Scanner(System.in);
	}
	
	public int mostrarMenuCliente(){
		System.out.println("\nMenuCliente: ");
		System.out.println("1. Hangares");
		System.out.println("2. Aviones");
		System.out.println("3. Salir");
		return leerEntero();
	}
	
	public int leerEntero() {
		while(true) {
			try {
				return Integer.parseInt(sc.nextLine());
			}catch(NumberFormatException e) {
				System.out.println("Número no válido. Por favor introuce un número válido");
			}
		}
	}
	
	public void menus() throws SQLException{
		boolean salir = false;
		
		while(!salir) {
			int opcion = mostrarMenuCliente();
			
			switch(opcion) {
			case 1:
				
				break;
			case 2:
				
				break;
			case 3:
				
				break;
			default:
					 
					break;
			}
		}
	}
}
