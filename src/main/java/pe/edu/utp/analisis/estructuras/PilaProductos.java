package pe.edu.utp.analisis.modelos;

import pe.edu.utp.analisis.dao.impl.ProductoDAOImpl;
import pe.edu.utp.analisis.dao.ProductoDAO;

import java.util.Stack;

public class PilaProductos {
    private Stack<Producto> carrito;
    private ProductoDAO productoDAO;  // Usaremos ProductoDAOImpl para interactuar con la base de datos

    public PilaProductos(ProductoDAO productoDAO) {
        this.carrito = new Stack<>();
        this.productoDAO = productoDAO;
    }

    // Método para agregar un producto al carrito
    public void agregarProducto(int idProducto) {
        Producto producto = productoDAO.obtenerProductoPorId(idProducto);
        if (producto != null && producto.getStock() > 0) {
            carrito.push(producto);
            // Puedes reducir el stock del producto si se desea, o manejarlo en otro momento
            productoDAO.actualizarStock(idProducto, producto.getStock() - 1);
            System.out.println("Producto agregado al carrito: " + producto.getNombre());
        } else {
            System.out.println("El producto no está disponible o no hay stock.");
        }
    }

    // Método para eliminar el último producto agregado al carrito
    public void eliminarProducto() {
        if (!carrito.isEmpty()) {
            Producto producto = carrito.pop();
            // Se puede restaurar el stock aquí si se desea
            productoDAO.actualizarStock(producto.getId(), producto.getStock() + 1);
            System.out.println("Producto eliminado del carrito: " + producto.getNombre());
        } else {
            System.out.println("El carrito está vacío.");
        }
    }

    // Método para ver el último producto agregado
    public Producto verUltimoProducto() {
        if (!carrito.isEmpty()) {
            return carrito.peek();  // Retorna el último producto sin eliminarlo
        }
        return null;
    }

    // Método para obtener todos los productos en el carrito
    public void verCarrito() {
        if (!carrito.isEmpty()) {
            System.out.println("Contenido del carrito:");
            for (Producto producto : carrito) {
                System.out.println(" - " + producto.getNombre() + " (" + producto.getTalla() + ")");
            }
        } else {
            System.out.println("El carrito está vacío.");
        }
    }
}
