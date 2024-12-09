package pe.edu.utp.analisis.modelos;

public class DetalleVenta {
    private Venta venta;           // Venta a la que pertenece este detalle
    private Producto producto;     // Producto que se ha vendido
    private int cantidad;          // Cantidad del producto vendida
    private double precioUnitario; // Precio unitario del producto en el momento de la venta

    // Constructor
    public DetalleVenta(Venta venta, Producto producto, int cantidad, double precioUnitario) {
        this.venta = venta;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    // Getters y setters
    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    // Método para calcular el total por detalle (cantidad * precio unitario)
    public double getTotal() {
        return cantidad * precioUnitario;
    }

    // Método toString para representar el detalle de venta de manera legible
    @Override
    public String toString() {
        return "Producto: " + producto.getNombre() + " | Cantidad: " + cantidad + " | Precio Unitario: " + precioUnitario + " | Total: " + getTotal();
    }
}
