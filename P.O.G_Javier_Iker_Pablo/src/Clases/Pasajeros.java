package Clases;

public class Pasajeros extends Aviones{

	 private int plazas;

	    // Constructor
	    public Pasajeros(int codigo, String fabricante, String modelo, double millas, double precio, int plazas) {
	        super(codigo, fabricante, modelo, millas, precio, plazas, precio, plazas);
	        this.plazas= plazas;
	    }

	    // Getters y Setters
	    public int getPlazas() {
	        return plazas;
	    }

	    public void setPlazas(int plazas) {
	        this.plazas = plazas;
	    }
}

