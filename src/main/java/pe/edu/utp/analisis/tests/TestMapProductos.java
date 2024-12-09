package pe.edu.utp.analisis.tests;

import pe.edu.utp.analisis.dao.impl.ProductoDAOImpl;
import pe.edu.utp.analisis.estructuras.MapProductos;
import pe.edu.utp.analisis.modelos.Producto;
import pe.edu.utp.analisis.utilidades.ConexionSecu;

public class TestMapProductos {
    public static void main(String[] args) {

        // Simular la conexión a la base de datos
        ConexionSecu conexion = new ConexionSecu();  // Asumimos que tienes una conexión funcional
        ProductoDAOImpl productoDAO = new ProductoDAOImpl(conexion);

        // Crear el carrito de productos (MapProductos)
        MapProductos carrito = new MapProductos(productoDAO);

        // Insertar productos de ejemplo en la base de datos (simulación)
        // Para fines de prueba, podemos asumir que estos productos existen en la base de datos

        // Agregar productos al carrito con diferentes cantidades
        System.out.println("Agregando productos al carrito...");
        carrito.agregarProducto(1, 2);  // Producto con ID 1, cantidad 2
        carrito.agregarProducto(2, 3);  // Producto con ID 2, cantidad 3

        // Ver el carrito después de agregar los productos
        carrito.verCarrito();

        // Calcular el precio total
        double precioTotal = carrito.calcularPrecioTotal();
        System.out.println("Precio total del carrito: S/" + precioTotal);

        // Eliminar productos del carrito
        System.out.println("Eliminando productos del carrito...");
        carrito.eliminarProducto(2, 3);  // Eliminar 1 unidad del producto con ID 2
        carrito.eliminarProducto(1, 1);  // Eliminar todas las unidades del producto con ID 1

        // Ver el carrito después de eliminar productos
        carrito.verCarrito();

        // Calcular el precio total después de eliminar productos
        precioTotal = carrito.calcularPrecioTotal();
        System.out.println("Precio total del carrito después de eliminar productos: S/" + precioTotal);
    }
}
