package pe.edu.utp.analisis.dao;

import pe.edu.utp.analisis.modelos.Empleado;

import java.util.List;

/**
 * Interfaz que define las operaciones para gestionar empleados en el sistema.
 */
public interface EmpleadoDAO {

    /**
     * Crear un nuevo empleado en el sistema.
     *
     * @param empleado El empleado a crear.
     */
    void crearEmpleado(Empleado empleado);

    /**
     * Leer un empleado por su ID.
     *
     * @param id El ID del empleado a leer.
     * @return El empleado correspondiente al ID proporcionado.
     */
    Empleado leerEmpleado(int id);

    /**
     * Leer todos los empleados en el sistema.
     *
     * @return Una lista de todos los empleados.
     */
    List<Empleado> leerTodosEmpleados();

    /**
     * Actualizar los detalles de un empleado.
     *
     * @param empleado El empleado con los datos actualizados.
     */
    void actualizarEmpleado(Empleado empleado);

    /**
     * Eliminar un empleado del sistema (marcar como inactivo).
     *
     * @param id El ID del empleado a eliminar.
     */
    void eliminarEmpleado(int id);

    /**
     * Cambiar el estado de un empleado a inactivo.
     *
     * @param id El ID del empleado cuyo estado se desea cambiar.
     */
    void desconectarEmpleado(int id);
    /**
     * Agregar empleados.
     *
     *  El ID del ultimo  empleado .
     */
    int obtenerUltimoId();
    /**
     * Obtener el cargo (job title) de un empleado por su ID.
     *
     * @param id El ID del empleado.
     * @return El cargo del empleado correspondiente al ID.
     */
    String obtenerCargoEmpleado(int id);
}
