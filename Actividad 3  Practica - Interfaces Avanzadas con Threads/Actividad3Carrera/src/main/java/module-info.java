module com.mycompany.actividad3carrera {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.actividad3carrera to javafx.fxml;
    exports com.mycompany.actividad3carrera;
}
