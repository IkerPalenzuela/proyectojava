package Clases;

<<<<<<< HEAD
<<<<<<< HEAD
public class Carga extends Aviones{

	private double capacidad;

    // Constructor
    public Carga(String fabricante, String modelo, Double millas, double capacidad) {
        super(fabricante, modelo, millas);
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

=======
public class Carga {
=======
public class Carga extends Aviones{
>>>>>>> branch 'master' of https://github.com/IkerPalenzuela/proyectojava.git

	 private double capacidad;

	    // Constructor
	    public Carga(int codigo, String fabricante, String modelo, double millas, double precio, double capacidad) {
	        super(codigo, fabricante, modelo, millas, precio);
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
>>>>>>> branch 'master' of https://github.com/IkerPalenzuela/proyectojava.git
