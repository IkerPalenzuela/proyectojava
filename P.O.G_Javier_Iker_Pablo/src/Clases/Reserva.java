package Clases;

import java.util.ArrayList;

public class Reserva {

    private int idReserva;
    private String dni;
    private String codAvion;
    private String fechaIda;
    private String fechaVuelta;

    private static ArrayList<Reserva> reservas = new ArrayList<>();

    // Constructor
    public Reserva(int idReserva, String dni, String codAvion, String fechaIda, String fechaVuelta) {
        this.idReserva = idReserva;
        this.dni = dni;
        this.codAvion = codAvion;
        setFechaIda(fechaIda);
        setFechaVuelta(fechaVuelta);
    }

    // Getters & Setters
    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        if (idReserva <= 0) {
            throw new IllegalArgumentException("El ID de reserva debe ser mayor a 0.");
        }
        this.idReserva = idReserva;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        if (dni == null || !dni.matches("\\d{8}[A-Z]")) {
            throw new IllegalArgumentException("El DNI debe tener 8 números seguidos de una letra mayúscula (A-Z).");
        }
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
        validarFormatoFecha(fechaIda); 
        this.fechaIda = fechaIda;
    }

    public String getFechaVuelta() {
        return fechaVuelta;
    }

    public void setFechaVuelta(String fechaVuelta) {
        validarFormatoFecha(fechaVuelta);
        if (fechaIda != null && fechaVuelta.compareTo(fechaIda) <= 0) {
            throw new IllegalArgumentException("La fecha de vuelta debe ser posterior a la fecha de ida.");
        }
        this.fechaVuelta = fechaVuelta;
    }

    // Metodos
    private void validarFormatoFecha(String fecha) {
        if (fecha == null || !fecha.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("La fecha debe estar en el formato 'AAAA-MM-dd'.");
        }
    }

    public static void add(Reserva reserva) {
        reservas.add(reserva);
    }

    public static void remove(Reserva reserva) {
        reservas.remove(reserva);
    }

    public static ArrayList<Reserva> getReservas() {
        return reservas;
    }

    // toString
    @Override
	public String toString() {
		return "Reserva [idReserva=" + idReserva + ", dni=" + dni + ", codAvion=" + codAvion + ", fechaIda=" + fechaIda
				+ ", fechaVuelta=" + fechaVuelta + "]";
	}
}
