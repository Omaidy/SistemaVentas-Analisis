package pe.edu.utp.analisis.modelos;

import java.time.LocalDateTime;
import java.time.Duration;

public class SesionUsuario {
    private static SesionUsuario instance; // Instancia única (Singleton)
    private int idUsuario; // ID del usuario
    private String nombreUsuario; // Nombre del usuario
    private String cargo; // Cargo del usuario
    private LocalDateTime inicioSesion; // Hora de inicio de la sesión

    // Constructor privado para el patrón Singleton
    private SesionUsuario() {
        this.inicioSesion = LocalDateTime.now(); // Establece la hora de inicio al crear la sesión
    }

    // Método para obtener la instancia única
    public static SesionUsuario getInstance() {
        if (instance == null) {
            instance = new SesionUsuario();
        }
        return instance;
    }

    // Getters y Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public LocalDateTime getInicioSesion() {
        return inicioSesion;
    }

    public Duration getDuracionSesion() {
        return Duration.between(inicioSesion, LocalDateTime.now());
    }
}
