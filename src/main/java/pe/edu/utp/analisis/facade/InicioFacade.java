package pe.edu.utp.analisis.facade;

import pe.edu.utp.analisis.modelos.Usuario;

public interface InicioFacade {

    /**
     * Método para autenticar al usuario con sus credenciales.
     * @param usuario El nombre de usuario.
     * @param contraseña La contraseña.
     * @return true si la autenticación es exitosa, de lo contrario false.
     */
    boolean autenticarUsuario(String usuario, String contraseña);

    /**
     * Obtener información de usuario después de la autenticación.
     * @param idEmpleado El ID del empleado autenticado.
     * @return Información del usuario autenticado.
     */
    Usuario obtenerInformacionUsuario(int idEmpleado);

    /**
     * Iniciar verificación de actividad para el usuario autenticado.
     * @param idEmpleado El ID del empleado autenticado.
     */
    void iniciarVerificacionActividad(int idEmpleado);
    void logout();
    void abrirPorCargo(int idEmpleado);
}
