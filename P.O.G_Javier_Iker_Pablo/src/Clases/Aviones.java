package Clases;

import java.util.ArrayList;

public class Aviones {

	// Atributos
	protected String fabricante;
	protected String  modelo;
	protected int millas;
	protected ArrayList<Reserva> reserva;
	protected Hangar hangar;
	
	// Constructor 
	public Aviones(String fabricante, String modelo, int millas) {
		this.fabricante = fabricante;
		this.modelo = modelo;
		this.millas = millas;
		this.reserva = new ArrayList <>();
		this.hangar = null;	
	}
	
	// Metodos
	public void agregarReserva(Reserva reserva) {
		Reserva.add(reserva);
	}
	
	public void eliminarReserva(Reserva reserva) {
		Reserva.remove(reserva);
	}
	
	public ArrayList<Reserva> obtenerReservas(){
		return reserva;
	}
	
	 public void asignarHangar(Hangar hangar) {
		 this.hangar = hangar;
	 }

	 // Getters & Setters
	 public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public int getMillas() {
		return millas;
	}

	public void setMillas(int millas) {
		this.millas = millas;
	}

	public Hangar getHangar() {
		return hangar;
	}

	public void setHangar(Hangar hangar) {
		this.hangar = hangar;
	}

	// toString
	@Override
	public String toString() {
		return "Aviones [fabricante=" + fabricante + ", modelo=" + modelo + ", millas=" + millas + ", hangar=" + hangar
				+ "]";
	}
		
	
}
