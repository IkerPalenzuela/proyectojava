package Clases;

import java.util.ArrayList;

public class Aviones {

    // Atributos
    protected int codigo;
    protected String fabricante;
    protected String modelo;
    protected double millas;
    protected ArrayList<Reserva> reserva;
    protected double precio;
    protected Hangar hangar;

    // Constructor
    public Aviones(int codigo, String fabricante, String modelo, double precio, double millas) {
        this.codigo = codigo;
        this.fabricante = fabricante;
        this.modelo = modelo;
        this.millas = millas;
        this.reserva = new ArrayList<>();
        this.precio = precio;
        this.hangar = null;
    }

    // Métodos
    public void agregarReserva(Reserva reserva) {
        this.reserva.add(reserva); 
    }

    public void eliminarReserva(Reserva reserva) {
        this.reserva.remove(reserva);
    }

    public ArrayList<Reserva> obtenerReservas() {
        return reserva;
    }

    public void asignarHangar(Hangar hangar) {
        this.hangar = hangar;
    }

    // Getters & Setters
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

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

    public double getPrecio() {
    	return precio;
    }
    
    public void setPrecio(double precio) {
    	this.precio = precio;
    }
    public double getMillas() {
        return millas;
    }

    public void setMillas(double millas) {
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
		return "Aviones [codigo=" + codigo + ", fabricante=" + fabricante + ", modelo=" + modelo + ", millas=" + millas
				+ ", reserva=" + reserva + ", precio=" + precio + ", hangar=" + hangar + ", obtenerReservas()="
				+ obtenerReservas() + ", getCodigo()=" + getCodigo() + ", getFabricante()=" + getFabricante()
				+ ", getModelo()=" + getModelo() + ", getprecio()=" + getPrecio() + ", getMillas()=" + getMillas()
				+ ", getHangar()=" + getHangar() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
}