package pe.edu.utp.analisis.modelos;

public class Cliente {
    private int dni;
    private String nombre;
    private String apellidos;
    private int numero;
    private String correo;
    private String direccion;
    private int idEmpleado;  // Quién registró al cliente

    // Constructor
    public Cliente(int dni, String nombre, String apellidos, String correo, int numero, String direccion, int idEmpleado) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.numero = numero;
        this.correo = correo;
        this.direccion = direccion;
        this.idEmpleado = idEmpleado;  // Guardamos quién registró al cliente
    }

    // Getters y setters
    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
}
