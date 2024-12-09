package pe.edu.utp.analisis.utilidades;



import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pe.edu.utp.analisis.modelos.DetalleVenta;
import pe.edu.utp.analisis.modelos.Venta;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class GeneradorFacturaExcel {

    public static void generarFactura(Venta venta) throws IOException {
        // Crear un libro de trabajo de Excel
        Workbook workbook = new XSSFWorkbook();

        // Crear una hoja de trabajo
        Sheet sheet = workbook.createSheet("Factura");

        // Crear una fila para los datos de la factura
        int rowNum = 0;

        // Crear las celdas para el encabezado de la factura
        Row headerRow = sheet.createRow(rowNum++);
        headerRow.createCell(0).setCellValue("Factura ID: " + venta.getIdVenta());
        headerRow.createCell(1).setCellValue("Fecha: " + venta.getFecha());
        headerRow.createCell(2).setCellValue("Total: " + venta.getTotal());

        // Crear una fila con los datos del cliente
        Row clienteRow = sheet.createRow(rowNum++);
        clienteRow.createCell(0).setCellValue("Cliente: " + venta.getCliente().getNombre() + " " + venta.getCliente().getApellidos());
        clienteRow.createCell(1).setCellValue("Correo: " + venta.getCliente().getCorreo());

        // Crear una fila con los datos del empleado que registró la venta
        Row empleadoRow = sheet.createRow(rowNum++);
        empleadoRow.createCell(0).setCellValue("Empleado: " + venta.getEmpleado().getNombre() + " " + venta.getEmpleado().getApellido());

        // Crear una fila de título para la tabla de detalles de la venta
        Row tableHeaderRow = sheet.createRow(rowNum++);
        tableHeaderRow.createCell(0).setCellValue("Producto");
        tableHeaderRow.createCell(1).setCellValue("Cantidad");
        tableHeaderRow.createCell(2).setCellValue("Precio Unitario");
        tableHeaderRow.createCell(3).setCellValue("Total");

        // Agregar los detalles de los productos de la venta
        List<DetalleVenta> detallesVenta = venta.getDetalles();
        for (DetalleVenta detalle : detallesVenta) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(detalle.getProducto().getNombre());
            row.createCell(1).setCellValue(detalle.getCantidad());
            row.createCell(2).setCellValue(detalle.getPrecioUnitario());
            row.createCell(3).setCellValue(detalle.getTotal());
        }

        // Crear el archivo Excel
        try (FileOutputStream fileOut = new FileOutputStream("Factura_" + venta.getIdVenta() + ".xlsx")) {
            workbook.write(fileOut);
        }

        // Cerrar el workbook
        workbook.close();

        System.out.println("Factura generada exitosamente.");
    }
}
