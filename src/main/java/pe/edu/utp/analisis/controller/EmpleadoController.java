package pe.edu.utp.analisis.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import pe.edu.utp.analisis.dao.ProductoDAO;
import pe.edu.utp.analisis.dao.impl.ProductoDAOImpl;
import pe.edu.utp.analisis.estructuras.MapProductos;
import pe.edu.utp.analisis.estructuras.ProductoDTO;
import pe.edu.utp.analisis.modelos.Producto;
import pe.edu.utp.analisis.utilidades.ConexionSecu;


import java.util.ArrayList;
import java.util.List;

public class EmpleadoController {

    @FXML
    private Button btn_clientes;  // Botón Clientes

    @FXML
    private Button btn_ventas;

    @FXML
    private AnchorPane JP_clientes;  // Pane de Clientes

    @FXML
    private AnchorPane JP_ventas; // Pane de Ventas
    @FXML
    private Button btn_buscar;
    @FXML
    private TextField codigo_producto;
    @FXML
    private ComboBox tallas_producto;
    @FXML
    private TextField cantidad_producto;
    @FXML
    private TextField precio_producto;
    @FXML
    private TextField nombreProductoTextField;  // Campo de texto para el nombre del producto
    @FXML
    private TextField tallaProductoTextField;   // Campo de texto para la talla del producto
    @FXML
    private Button btn_agregar;
    @FXML
    private TableView<ProductoDTO> carritoTableView;  // Tabla de productos en el carrito
    @FXML
    private TableColumn<ProductoDTO, String> nombreColumn;  // Columna para el nombre
    @FXML
    private TableColumn<ProductoDTO, String> tallaColumn;   // Columna para la talla
    @FXML
    private TableColumn<ProductoDTO, Integer> cantidadColumn;  // Columna para la cantidad
    @FXML
    private Label totalLabel;  // Etiqueta para mostrar el total

    private MapProductos mapProductos;

    private ProductoDAO productoDAO = new ProductoDAOImpl(new ConexionSecu());

    @FXML
    private void initialize() {
        // Inicialización de la vista
        System.out.println("EmpleadoController inicializado.");
        // Inicializar MapProductos
        mapProductos = new MapProductos(productoDAO);
        // Configuración de las columnas de la tabla
        nombreColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getProducto().getNombre()));
        tallaColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getProducto().getTalla()));
        cantidadColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getCantidad()).asObject());

        // Inicializamos el carrito con la lista de productos
        actualizarTablaCarrito();
    }




    @FXML
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    @FXML
    public void mostrarClientes(ActionEvent actionEvent) {
        JP_clientes.setVisible(true);  // Mostrar la vista de clientes
        JP_ventas.setVisible(false);  // Ocultar la vista de ventas
    }
    @FXML
    public void mostrarVentas(ActionEvent actionEvent) {
        JP_ventas.setVisible(true);  // Mostrar la vista de ventas
        JP_clientes.setVisible(false);
    }
    @FXML
    public void buscarproducto(ActionEvent actionEvent) {
        String nombre = codigo_producto.getText();  // Obtener el nombre del producto desde el TextField

        if (nombre != null && !nombre.isEmpty()) {
            // Llamamos al método que obtiene las tallas del producto desde la base de datos
            List<String> tallas = productoDAO.obtenerTallas(nombre);  // Método para obtener las tallas

            // Limpiar el ComboBox antes de agregar las nuevas tallas
            tallas_producto.getItems().clear();

            // Agregar las tallas al ComboBox
            if (tallas != null && !tallas.isEmpty()) {
                tallas_producto.getItems().addAll(tallas);
            } else {
                tallas_producto.getItems().add("No disponible");
            }
        } else {
            // Si no hay código, mostramos un mensaje o limpiamos el ComboBox
            tallas_producto.getItems().clear();
            tallas_producto.getItems().add("Ingrese un nombre válido");
        }
    }

    @FXML
    public void onTallaSeleccionada(ActionEvent actionEvent) {
        String nombre = codigo_producto.getText();  // Obtener el nombre del producto
        Object tallaSeleccionadaObj = tallas_producto.getValue().toString();  // Obtener la talla seleccionada del ComboBox

        if (nombre != null && !nombre.isEmpty() && tallaSeleccionadaObj != null) {
            String tallaSeleccionada = tallaSeleccionadaObj.toString();

            // Obtain the price based on the product name and selected size
            double precio = productoDAO.obtenerPrecioPorTalla(nombre, tallaSeleccionada);

            // Update the price TextField
            precio_producto.setText(String.valueOf(precio));
        }

    }

    @FXML
    public void agregarcarrito(ActionEvent actionEvent) {
        String nombre = codigo_producto.getText();
        Object tallaObj = tallas_producto.getValue();

        // Check if nombre and talla are valid
        if (nombre == null || nombre.isEmpty() || tallaObj == null) {
            mostrarAlerta("Error", "Debe ingresar el nombre y talla del producto.");
            return;
        }

        // Obtener la talla seleccionada
        String talla = tallaObj.toString();

        // Obtener el ID del producto por nombre y talla
        int idProducto = productoDAO.obtenerIDporNombreYTalla(nombre, talla);

        if (idProducto == -1) {
            mostrarAlerta("Error", "Producto no encontrado con ese nombre y talla.");
            return;
        }

        // Obtener la cantidad ingresada en el TextField
        String cantidadStr = cantidad_producto.getText();
        int cantidad;

        try {
            // Intentamos convertir la cantidad a un entero
            cantidad = Integer.parseInt(cantidadStr);

            // Verificamos que la cantidad sea mayor que 0
            if (cantidad <= 0) {
                mostrarAlerta("Error", "La cantidad debe ser un número mayor que 0.");
                return;
            }
        } catch (NumberFormatException e) {
            // Si no es un número válido, mostramos un error
            mostrarAlerta("Error", "La cantidad debe ser un número válido.");
            return;
        }

        // Agregar el producto al carrito con la cantidad obtenida
        mapProductos.agregarProducto(idProducto, cantidad);

        // Actualizar la tabla del carrito
        actualizarTablaCarrito();

        // Actualizar el total del carrito
        double total = mapProductos.calcularPrecioTotal();
        totalLabel.setText("Total: S/ " + total);
    }
    private void actualizarTablaCarrito() {
        // Obtener la lista de productos en el carrito y actualizar la tabla
        List<ProductoDTO> productosCarrito = mapProductos.getCarrito();  // Usa getCarrito() para obtener la lista
        carritoTableView.setItems(FXCollections.observableArrayList(productosCarrito));
    }


    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
