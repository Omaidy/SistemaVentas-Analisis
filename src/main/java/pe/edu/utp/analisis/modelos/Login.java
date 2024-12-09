package pe.edu.utp.analisis.modelos;

import java.time.LocalDateTime;

public class Login {
    private int id;
    private int id_empleado;
    private String usuario;
    private String contrasena;
    private int actividad;
    private LocalDateTime fecha_creacion;

    public Login() {
    }

    public Login(int id, int id_empleado, String usuario, String contrasena, int actividad, LocalDateTime fecha_creacion) {
        this.id = id;
        this.id_empleado = id_empleado;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.actividad = actividad;
        this.fecha_creacion = fecha_creacion;
    }

    public int getId() {
        return id;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public int getActividad() {
        return actividad;
    }

    public LocalDateTime getFecha_creacion() {
        return fecha_creacion;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setActividad(int actividad) {
        this.actividad = actividad;
    }

    public void setFecha_creacion(LocalDateTime fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

}
