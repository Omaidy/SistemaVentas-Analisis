package pe.edu.utp.analisis.modelos;

public class Empleado extends Usuario {

    public Empleado(int id_usuario, String nombre, String apellido,
                    String nombreUsuario, String contrasena,
                    String cargo, Boolean actividad) {
        super(id_usuario, nombre, apellido, nombreUsuario, contrasena, cargo, actividad);
        validarCargo(cargo);
    }
    private void validarCargo(String cargo) {
        if ("administrador".equalsIgnoreCase(cargo)) {
            throw new IllegalArgumentException("El cargo no puede ser 'administrador'.");
        }
    }

}