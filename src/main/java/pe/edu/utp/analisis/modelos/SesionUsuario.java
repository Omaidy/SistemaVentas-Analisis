package pe.edu.utp.analisis.modelos;

import java.util.Date;

public class SesionUsuario {

    private static SesionUsuario instancia;

    private int idSesion;
    private int idEmpleado;
    private String nombreUsuario;
    private Date fechaInicio;
    private Date fechaCierre;
    private long duracion;  // Duración en segundos

    // Constructor privado para el patrón Singleton
    private SesionUsuario() {
        this.duracion = 0;  // Inicializamos la duración en 0 en el momento de la creación
    }

    public static SesionUsuario getInstance() {
        if (instancia == null) {
            instancia = new SesionUsuario();
        }
        return instancia;
    }

    // Iniciar sesión
    public void iniciarSesion(int idEmpleado, String nombreUsuario, int idSesion) {
        this.idEmpleado = idEmpleado;
        this.nombreUsuario = nombreUsuario;
        this.idSesion = idSesion;
        this.fechaInicio = new Date();  // Establecemos la fecha de inicio al momento de la creación de la sesión
        this.fechaCierre = null;  // La fecha de cierre es null hasta que la sesión se cierre
        this.duracion = 0;  // Se reinicia la duración a 0
    }

    // Cerrar sesión
    public void cerrarSesion() {
        if (this.fechaInicio != null && this.fechaCierre == null) { // Verificar que no sea null
            this.fechaCierre = new Date();  // Establecer la fecha de cierre al momento de cerrar la sesión
            this.duracion = calcularDuracion();  // Calcular la duración en segundos
        }
    }

    // Calcular la duración en segundos
    public long calcularDuracion() {
        // Asegurarse de que las fechas no sean nulas antes de calcular la duración
        if (fechaInicio != null && fechaCierre != null) {
            long diffInMillis = fechaCierre.getTime() - fechaInicio.getTime();  // Diferencia en milisegundos
            return diffInMillis / 1000;  // Convertimos la diferencia en milisegundos a segundos
        }
        return 0;  // Si no hay fechas válidas, la duración es 0
    }

    // Getters y setters
    public int getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(int idSesion) {
        this.idSesion = idSesion;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public long getDuracion() {
        return duracion;
    }

    public boolean isSesionActiva() {
        return fechaCierre == null;  // Si la fecha de cierre es null, la sesión sigue activa
    }
}
