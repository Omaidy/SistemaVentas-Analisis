package pe.edu.utp.analisis.modelos;
// Tabla: usuarios
public class Usuario {
    private int id_usuario;        // Corrección de "id_usario" a "id_usuario"
    private String nombre;
    private String apellido;
    private String nombreUsuario;
    private String contrasena;
    private String cargo;
    private Boolean actividad;

    // Constructor Vacio
    public Usuario() {
    }

    // Constructor
    public Usuario(int id_usuario, String nombre, String apellido, String nombreUsuario, String contrasena,String cargo, Boolean actividad) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.cargo = cargo;
        this.actividad = actividad;
    }

    // Getters
    public int getId_usuario() {
        return id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getCargo() {
        return cargo;
    }

    public Boolean getActividad() {
        return actividad;
    }

    // Setters
    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setActividad(Boolean actividad) {
        this.actividad = actividad;
    }
}
