package pe.edu.utp.analisis.tests;

import pe.edu.utp.analisis.servicios.LoginService;
import pe.edu.utp.analisis.modelos.Usuario;
import pe.edu.utp.analisis.modelos.SesionUsuario;
import pe.edu.utp.analisis.utilidades.ActivityChecker;

import java.sql.SQLException;
import java.util.Scanner;

public class Login {

    public static void main(String[] args) throws SQLException {
        // Crear una instancia del servicio de login
        LoginService loginService = new LoginService();
        Scanner scanner = new Scanner(System.in);

        // Simulación del proceso de login
        System.out.println("¡Bienvenido al sistema de Login!");

        // Solicitar usuario y contraseña
        System.out.print("Ingresa tu usuario: ");
        String usuario = scanner.nextLine();

        System.out.print("Ingresa tu contraseña: ");
        String contrasena = scanner.nextLine();

        // Intentar hacer login con las credenciales proporcionadas
        if (loginService.login(usuario, contrasena)) {
            System.out.println("Login exitoso. ¡Bienvenido!");

            // Obtener el id_empleado desde la sesión activa
            SesionUsuario sesion = SesionUsuario.getInstance();
            int idEmpleado = sesion.getIdEmpleado(); // Obtener el id_empleado del objeto de sesión

            // Crear y comenzar un hilo que se encargará de verificar la actividad cada 5 segundos
            Thread actividadCheckerThread = new Thread(new ActivityChecker(idEmpleado, loginService));
            actividadCheckerThread.start();  // Iniciar el hilo de verificación de actividad

            // Luego de iniciar sesión, pedir al usuario que cierre sesión o realice otra acción
            boolean sesionActiva = true;  // Variable para saber si la sesión sigue activa
            while (sesionActiva) {
                // Mostrar opciones al usuario
                System.out.println("¿Qué deseas hacer?");
                System.out.println("1. Consultar mi información");
                System.out.println("2. Cerrar sesión");
                System.out.println("3. Salir del sistema");

                // Leer la opción del usuario
                int opcion = scanner.nextInt();
                scanner.nextLine();  // Consumir el salto de línea restante

                switch (opcion) {
                    case 1:
                        // Consultar información del usuario usando el id_empleado obtenido
                        Usuario usuarioInfo = loginService.obtenerInfoUsuario(idEmpleado);
                        if (usuarioInfo != null) {
                            System.out.println("Información del Usuario:");
                            System.out.println("ID Usuario: " + usuarioInfo.getId_usuario());
                            System.out.println("Nombre: " + usuarioInfo.getNombre());
                            System.out.println("Apellido: " + usuarioInfo.getApellido());
                            System.out.println("Nombre de Usuario: " + usuarioInfo.getNombreUsuario());
                            System.out.println("Cargo: " + usuarioInfo.getCargo());
                            System.out.println("Actividad: " + (usuarioInfo.getActividad() ? "Activo" : "Inactivo"));
                        } else {
                            System.out.println("No se pudo obtener la información del usuario.");
                        }
                        break;

                    case 2:
                        loginService.logout();  // Llamamos al método logout() para cerrar sesión
                        System.out.println("Has cerrado sesión exitosamente.");
                        sesionActiva = false;  // La sesión se cierra
                        break;

                    case 3:
                        System.out.println("Saliendo del sistema...");
                        sesionActiva = false;  // Salir del ciclo
                        break;

                    default:
                        System.out.println("Opción no válida. Por favor, elige una opción válida.");
                        break;
                }
            }
        } else {
            System.out.println("Credenciales incorrectas o el usuario no está activo.");
        }

        scanner.close();
    }
}
