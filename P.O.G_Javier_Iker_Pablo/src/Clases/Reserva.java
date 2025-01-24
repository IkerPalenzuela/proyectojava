package Clases;

public class Reserva {

	private int idReserva;
	private String dni;
	private String codAvion;
	private String fechaIda;
	private String fechaVuelta;
	
	// Constructor
	public Reserva(int idReserva, String dni, String codAvion, String fechaIda, String fechaVuelta) {
		this.idReserva = idReserva;
		this.dni = dni;
		this.codAvion = codAvion;
		this.fechaIda = fechaIda;
		this.fechaVuelta = fechaVuelta;
	}

	// Getters & Setters
	public int getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(int idReserva) {
		this.idReserva = idReserva;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getCodAvion() {
		return codAvion;
	}

	public void setCodAvion(String codAvion) {
		this.codAvion = codAvion;
	}

	public String getFechaIda() {
		return fechaIda;
	}

	public void setFechaIda(String fechaIda) {
		this.fechaIda = fechaIda;
	}

	public String getFechaVuelta() {
		return fechaVuelta;
	}

	public void setFechaVuelta(String fechaVuelta) {
		this.fechaVuelta = fechaVuelta;
	}
	
	
}
