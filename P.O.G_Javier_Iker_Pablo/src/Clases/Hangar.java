
package Clases;

import java.util.ArrayList;
public class Hangar {

	// Atributos
	private int idHangar;
	private int capacidadAviones;
	private String localidad;
	private ArrayList<Aviones> aviones;
	
	// Constructor
	public Hangar(int idHangar, int capacidadAviones, String localidad, ArrayList<Aviones> aviones) {
		this.idHangar = idHangar;
		this.capacidadAviones = capacidadAviones;
		this.localidad = localidad;
		this.aviones = aviones;
	}
	
	// Metodos
	public void agregarAvion(Aviones avion) {
		if (aviones.size() < capacidadAviones) {
			aviones.add(avion);
			avion.asignarHangar(this);
		}else {
			System.out.println("El hangar esta lleno, no se pueden añadir mas aviones.");
		}
	}
	
	public void eliminarAvion(Aviones avion) {
		aviones.remove(avion);
		avion.asignarHangar(null);
	}

	// Getters & Setters
	public int getIdHangar() {
		return idHangar;
	}

	public void setIdHangar(int idHangar) {
		this.idHangar = idHangar;
	}

	public int getCapacidadAviones() {
		return capacidadAviones;
	}

	public void setCapacidadAviones(int capacidadAviones) {
		this.capacidadAviones = capacidadAviones;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public ArrayList<Aviones> getAviones() {
		return aviones;
	}

	// toString
	@Override
	public String toString() {
		return "Hangar [idHangar=" + idHangar + ", capacidadAviones=" + capacidadAviones + ", localidad=" + localidad + "]";
	}
}
