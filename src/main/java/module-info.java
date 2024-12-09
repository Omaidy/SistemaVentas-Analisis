module pe.edu.utp.analisis {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires java.desktop;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;

    opens pe.edu.utp.analisis to javafx.fxml;
    exports pe.edu.utp.analisis;
    exports pe.edu.utp.analisis.controller;
    opens pe.edu.utp.analisis.controller to javafx.fxml;
}