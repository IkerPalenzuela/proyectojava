package Clases;

public class Carga extends Aviones{

	 private double capacidad;

	    // Constructor
	    public Carga(int codigo, String fabricante, String modelo, double millas, double precio, double capacidad) {
	        super(codigo, fabricante, modelo, millas, precio, codigo, capacidad, codigo);
	        this.capacidad = capacidad;
	    }

	    // Getters y Setters
	    public double getCapacidad() {
	        return capacidad;
	    }

	    public void setCapacidad(double capacidad) {
	        this.capacidad = capacidad;
	    }
}