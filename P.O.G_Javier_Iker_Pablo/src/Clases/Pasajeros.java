package Clases;

public class Pasajeros extends Aviones{

	   private int plazas;

	    // Constructor
	    public Pasajeros(String fabricante, String modelo, Double millas, int plazas) {
	        super(fabricante, modelo, millas);
	        setPlazas(plazas);
	    }

	    // Getters y Setters
	    public int getPlazas() {
	        return plazas;
	    }

	    public void setPlazas(int plazas) {
	        this.plazas = plazas;
	    }

}
