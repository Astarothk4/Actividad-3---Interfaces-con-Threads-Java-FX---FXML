package com.mycompany.actividad3conteo;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

public class PrimaryController {

    @FXML
    private TextField txtClicks;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Button btnEsperar;

    private int contador = 0;
    private boolean contando = false;

    @FXML
    public void initialize() {
        txtClicks.setText("0");
        progressBar.setProgress(0);
    }

    @FXML
    private void contarClick() {
        contador++;
        txtClicks.setText(String.valueOf(contador));
    }

    @FXML
    private void esperar() {
        if (contando) {
            return;
        }

        contando = true;
        btnEsperar.setDisable(true);
        progressBar.setProgress(0);

        Thread hilo = new Thread(() -> {
            try {
                for (int i = 1; i <= 100; i++) {
                    Thread.sleep(100);

                    final double progreso = i / 100.0;

                    Platform.runLater(() ->
                            progressBar.setProgress(progreso)
                    );
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                Platform.runLater(() -> {
                    progressBar.setProgress(1);
                    btnEsperar.setDisable(false);
                    contando = false;
                });
            }
        });

        hilo.setDaemon(true);
        hilo.start();
    }
}
