package Clases;

public class Carga extends Aviones{

	 private double capacidad;

	    // Constructor
	    public Carga(int codigo, String fabricante, String modelo, double millas, double capacidad) {
	        super(codigo, fabricante, modelo, millas);
	        setCapacidad(capacidad);
	    }

	    // Getters y Setters
	    public double getCapacidad() {
	        return capacidad;
	    }

	    public void setCapacidad(double capacidad) {
	        this.capacidad = capacidad;
	    }
}
