package pe.edu.utp.analisis.estructuras;

import pe.edu.utp.analisis.dao.ProductoDAO;
import pe.edu.utp.analisis.modelos.*;

import java.util.*;

public class MapProductos {
    private Map<Producto, Integer> carrito;
    private ProductoDAO productoDAO;

    public MapProductos(ProductoDAO productoDAO) {
        this.carrito = new HashMap<>();
        this.productoDAO = productoDAO;
    }

    // Método para agregar un producto con cantidad al carrito
    public void agregarProducto(int idProducto, int cantidad) {
        Producto producto = productoDAO.obtenerProductoPorId(idProducto);

        // Verificar si el producto existe y si hay suficiente stock
        if (producto != null && producto.getStock() >= cantidad) {
            // Si el producto ya está en el carrito, sumamos la cantidad
            if (carrito.containsKey(producto)) {
                carrito.put(producto, carrito.get(producto) + cantidad);
            } else {
                carrito.put(producto, cantidad);  // Si no está en el carrito, lo agregamos con la cantidad
            }

            // Actualizar el stock en la base de datos
            productoDAO.actualizarStock(idProducto, producto.getStock() - cantidad);
            System.out.println("Producto agregado al carrito: " + producto.getNombre() + " | Cantidad: " + cantidad);
        } else {
            System.out.println("El producto no está disponible o no hay suficiente stock.");
        }
    }

    // Método para eliminar un producto (reducción de cantidad) del carrito
    public void eliminarProducto(int idProducto, int cantidad) {
        Producto producto = productoDAO.obtenerProductoPorId(idProducto);

        if (producto != null && carrito.containsKey(producto)) {
            int cantidadActual = carrito.get(producto);

            // Si la cantidad a eliminar es menor o igual a la cantidad en el carrito
            if (cantidad <= cantidadActual) {
                int nuevaCantidad = cantidadActual - cantidad;
                if (nuevaCantidad == 0) {
                    carrito.remove(producto);  // Si la cantidad llega a cero, eliminamos el producto del carrito
                } else {
                    carrito.put(producto, nuevaCantidad);  // Si no, actualizamos la cantidad en el carrito
                }

                // Restaurar el stock en la base de datos
                productoDAO.actualizarStock(idProducto, producto.getStock() + cantidad);
                System.out.println("Producto eliminado del carrito: " + producto.getNombre() + " | Cantidad: " + cantidad);
            } else {
                System.out.println("No hay suficiente cantidad del producto para eliminar.");
            }
        } else {
            System.out.println("El producto no está en el carrito.");
        }
    }

    // Método para ver el último producto agregado
    public Producto verUltimoProducto() {
        if (!carrito.isEmpty()) {
            // Retorna el primer producto (el que fue agregado primero, ya que el carrito es un mapa)
            return carrito.keySet().stream().findFirst().orElse(null);
        }
        return null;
    }

    // Método para obtener todos los productos en el carrito
    public void verCarrito() {
        if (!carrito.isEmpty()) {
            System.out.println("Contenido del carrito:");
            for (Map.Entry<Producto, Integer> entry : carrito.entrySet()) {
                Producto producto = entry.getKey();
                Integer cantidad = entry.getValue();
                System.out.println(" - " + producto.getNombre() + " (" + producto.getTalla() + ") | Cantidad: " + cantidad);
            }
        } else {
            System.out.println("El carrito está vacío.");
        }
    }
    public List<ProductoDTO> getCarrito() {
        List<ProductoDTO> carritoList = new ArrayList<>();
        for (Map.Entry<Producto, Integer> entry : carrito.entrySet()) {
            carritoList.add(new ProductoDTO(entry.getKey(), entry.getValue()));
        }
        return carritoList;
    }

    // Método para calcular el precio total del carrito
    public double calcularPrecioTotal() {
        double total = 0.0;
        for (Map.Entry<Producto, Integer> entry : carrito.entrySet()) {
            Producto producto = entry.getKey();
            int cantidad = entry.getValue();
            total += producto.getPrecio() * cantidad;  // Multiplicamos el precio por la cantidad
        }
        return total;
    }
    // Método para procesar la venta
    // Método para procesar la venta, ahora con cliente y empleado
    public Venta procesarVenta(Cliente cliente, Empleado empleado) {
        if (carrito.isEmpty()) {
            System.out.println("El carrito está vacío. No se puede procesar la venta.");
            return null;
        }

        // Crear la lista de detalles de venta
        List<DetalleVenta> detallesVenta = new ArrayList<>();

        // Calcular el precio total
        double total = 0;

        for (Map.Entry<Producto, Integer> entry : carrito.entrySet()) {
            Producto producto = entry.getKey();
            int cantidad = entry.getValue();

            // Crear un detalle de venta para cada producto
            DetalleVenta detalle = new DetalleVenta(null, producto, cantidad, producto.getPrecio()); // 'null' se reemplazará más adelante
            detallesVenta.add(detalle);

            // Acumulamos el total
            total += detalle.getTotal();

            // Actualizar el stock en la base de datos
            productoDAO.actualizarStock(producto.getId(), producto.getStock() - cantidad);
        }

        // Crear la venta con la fecha actual, el total calculado, y los detalles
        Venta venta = new Venta(new Date(), total, cliente, empleado, detallesVenta);

        // Establecer la venta en los detalles, ahora que la venta ha sido creada
        for (DetalleVenta detalle : detallesVenta) {
            detalle.setVenta(venta);  // Asignamos la venta a cada detalle
        }

        // Limpiar el carrito después de procesar la venta
        carrito.clear();

        // Mostrar detalles de la venta
        System.out.println("Venta procesada con éxito:");
        System.out.println(venta);
        for (DetalleVenta detalle : detallesVenta) {
            System.out.println(detalle);
        }

        return venta;
    }
}

