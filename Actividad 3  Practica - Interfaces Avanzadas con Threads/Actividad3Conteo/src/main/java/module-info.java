module com.mycompany.actividad3conteo {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.actividad3conteo to javafx.fxml;
    exports com.mycompany.actividad3conteo;
}
