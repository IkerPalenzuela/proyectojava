package Clases;

public class Usuarios {

    // Atributos
    private String dni;
    private String nombre;
    private String apellido;
    private String empresa;
    private String contrasena;
    private Rol rol;

    // Constructor
    public Usuarios(String dni, String nombre, String apellido, String empresa, String contrasena, Rol rol) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.empresa = empresa;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    // Getters y Setters
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        if (dni == null || !dni.matches("\\d{8}[A-Za-z]")) {
            throw new IllegalArgumentException("El DNI tiene que tener 8 números y una letra de la 'A-Z'.");
        }
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public boolean validarContrasena(String contrasena) {
        return this.contrasena.equals(contrasena);
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    // toString
    @Override
    public String toString() {
        return "Usuarios [dni=" + dni + ", nombre=" + nombre + ", apellido=" + apellido + ", empresa=" + empresa
                + ", rol=" + rol + "]";
    }
}
