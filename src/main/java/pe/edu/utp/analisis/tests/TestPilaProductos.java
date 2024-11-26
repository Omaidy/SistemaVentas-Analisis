package pe.edu.utp.analisis.tests;

import pe.edu.utp.analisis.dao.impl.ProductoDAOImpl;
import pe.edu.utp.analisis.modelos.PilaProductos;
import pe.edu.utp.analisis.modelos.Producto;
import pe.edu.utp.analisis.utilidades.ConexionSecu;

public class TestPilaProductos {
    public static void main(String[] args) {

        // Simular la conexión a la base de datos
        ConexionSecu conexion = new ConexionSecu();  // Aquí asumimos que tienes una conexión funcional
        ProductoDAOImpl productoDAO = new ProductoDAOImpl(conexion);

        // Crear la pila de productos (carrito de compras)
        PilaProductos carrito = new PilaProductos(productoDAO);

        // Insertar productos de ejemplo a la base de datos (simulando)


        // Agregar productos al carrito
        System.out.println("Agregando productos al carrito...");
        carrito.agregarProducto(1); // Producto con ID 1
        carrito.agregarProducto(2); // Producto con ID 2
        carrito.agregarProducto(3); // Producto con ID 3

        // Ver el carrito
        carrito.verCarrito();

  }


}
