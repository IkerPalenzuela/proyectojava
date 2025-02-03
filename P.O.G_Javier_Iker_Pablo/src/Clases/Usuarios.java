package Clases;

public class Usuarios {

    // Atributos
    private static String dni; 
    private static String nombre;
    private static String apellido;
    private static String empresa;
    private static String contrasena;
    private static Rol rol;

    // Constructor
    public Usuarios(String dni, String nombre, String apellido, String empresa, String contrasena, Rol rol) {
        setDni(dni);
        Usuarios.nombre = nombre;
        Usuarios.apellido = apellido;
        Usuarios.empresa = empresa;
        Usuarios.contrasena = contrasena;
        Usuarios.rol = rol;
    }

	// Getters y Setters
    public static String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        if (dni == null || !dni.matches("\\d{8}[A-Za-z]")) {
            throw new IllegalArgumentException("El DNI tiene que tener 8 números y una letra de la 'A-Z'.");
        }
        Usuarios.dni = dni;
    }

    public static String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        Usuarios.nombre = nombre;
    }

    public static String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        Usuarios.apellido = apellido;
    }

    public static String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        Usuarios.empresa = empresa;
    }

    public static Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        Usuarios.rol = rol;
    }

    public boolean validarContrasena(String contrasena) {
        return Usuarios.contrasena.equals(contrasena);
    }

    public static String getContrasena() {
    	return contrasena;
    }
    // toString
    @Override
    public String toString() {
        return "Usuarios [dni=" + dni + ", nombre=" + nombre + ", apellido=" + apellido + ", empresa=" + empresa
                + ", rol=" + rol + "]";
    }
}
