
package pe.edu.utp.analisis.tests;

import pe.edu.utp.analisis.dao.impl.ProductoDAOImpl;
import pe.edu.utp.analisis.dao.impl.VentaDAOImpl;
import pe.edu.utp.analisis.estructuras.MapProductos;
import pe.edu.utp.analisis.modelos.Cliente;
import pe.edu.utp.analisis.modelos.Empleado;
import pe.edu.utp.analisis.modelos.Producto;
import pe.edu.utp.analisis.modelos.Venta;
import pe.edu.utp.analisis.modelos.DetalleVenta;
import pe.edu.utp.analisis.utilidades.ConexionSecu;

import java.util.Date;
import java.util.List;

public class TestVentaAndMap {
    public static void main(String[] args) {
        // Simular la conexión a la base de datos
        ConexionSecu conexion = new ConexionSecu();
        ProductoDAOImpl productoDAO = new ProductoDAOImpl(conexion);
        VentaDAOImpl ventaDAO = new VentaDAOImpl();

        // Crear la estructura de carrito (MapProductos)
        MapProductos carrito = new MapProductos(productoDAO);

        // Suponiendo que tenemos los productos con ID 1, 2 y 3 en la base de datos
        // Agregar productos al carrito
        carrito.agregarProducto(1, 2); // Producto con ID 1, Cantidad 2
        carrito.agregarProducto(2, 3); // Producto con ID 2, Cantidad 3
        carrito.agregarProducto(3, 1); // Producto con ID 3, Cantidad 1

        // Crear cliente con ID 1 y empleado con ID 2
        Cliente cliente = new Cliente(1, "Juan", "Pérez", "juan.perez@email.com", 987654321, "Av. Siempre Viva", 2);
        Empleado empleado = new Empleado(2, "Ana", "López", "vendedor", null);

        // Procesar la venta
        Venta venta = carrito.procesarVenta(cliente, empleado);

        if (venta != null) {
            // Guardar la venta en la base de datos usando VentaDAOImpl
            ventaDAO.guardarVenta(venta);

            // Guardar los detalles de la venta
            List<DetalleVenta> detallesVenta = venta.getDetalles();
            ventaDAO.guardarDetallesVenta(detallesVenta);

            // Imprimir confirmación
            System.out.println("Venta procesada y almacenada correctamente en la base de datos.");
        } else {
            System.out.println("No se pudo procesar la venta.");
        }
    }
}
