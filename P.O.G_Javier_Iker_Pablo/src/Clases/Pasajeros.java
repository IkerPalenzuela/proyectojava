package Clases;

<<<<<<< HEAD
<<<<<<< HEAD
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
=======
public class Pasajeros {
>>>>>>> branch 'master' of https://github.com/IkerPalenzuela/proyectojava.git
=======
public class Pasajeros extends Aviones{

	 private int plazas;

	    // Constructor
	    public Pasajeros(int codigo, String fabricante, String modelo, double millas, double precio, int plazas) {
	        super(codigo, fabricante, modelo, millas, precio);
	        setPlazas(plazas);
	    }

	    // Getters y Setters
	    public int getPlazas() {
	        return plazas;
	    }

	    public void setPlazas(int plazas) {
	        this.plazas = plazas;
	    }

>>>>>>> branch 'master' of https://github.com/IkerPalenzuela/proyectojava.git

}
