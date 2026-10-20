package com.mycompany.actividad3carrera;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.util.concurrent.atomic.AtomicInteger;

public class PrimaryController {

    @FXML
    private ProgressBar progressLiebre;

    @FXML
    private ProgressBar progressTortuga;

    @FXML
    private Label lblLiebre;

    @FXML
    private Label lblTortuga;

    @FXML
    private Label lblEstado;

    @FXML
    private Button btnIniciar;

    private volatile boolean carreraEnCurso = false;
    private final AtomicInteger terminados = new AtomicInteger(0);

    @FXML
    public void initialize() {
        progressLiebre.setProgress(0);
        progressTortuga.setProgress(0);

        lblLiebre.setText("Liebre: 0 %");
        lblTortuga.setText("Tortuga: 0 %");
        lblEstado.setText("¡Listos para la carrera!");
    }

    @FXML
    private void iniciarCarrera() {
        if (carreraEnCurso) {
            return;
        }

        carreraEnCurso = true;
        terminados.set(0);
        btnIniciar.setDisable(true);

        progressLiebre.setProgress(0);
        progressTortuga.setProgress(0);

        lblLiebre.setText("Liebre: 0 %");
        lblTortuga.setText("Tortuga: 0 %");
        lblEstado.setText("¡Comienza la carrera!");

        Thread hiloLiebre = new Thread(() -> {
            try {
                for (int i = 1; i <= 100; i++) {

                    if (i == 36) {
                        Platform.runLater(() ->
                                lblEstado.setText(
                                        "¡La liebre se duerme!"
                                )
                        );

                        Thread.sleep(7000);
                    }

                    Thread.sleep(25);

                    final int avance = i;

                    Platform.runLater(() -> {
                        progressLiebre.setProgress(avance / 100.0);
                        lblLiebre.setText("Liebre: " + avance + " %");
                    });
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            finalizarCarrera();
        });

        Thread hiloTortuga = new Thread(() -> {
            try {
                for (int i = 1; i <= 100; i++) {
                    Thread.sleep(85);

                    final int avance = i;

                    Platform.runLater(() -> {
                        progressTortuga.setProgress(avance / 100.0);
                        lblTortuga.setText("Tortuga: " + avance + " %");
                    });
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            finalizarCarrera();
        });

        hiloLiebre.setDaemon(true);
        hiloTortuga.setDaemon(true);

        hiloLiebre.start();
        hiloTortuga.start();
    }

    private void finalizarCarrera() {
        if (terminados.incrementAndGet() == 2) {
            Platform.runLater(() -> {
                progressLiebre.setProgress(1);
                progressTortuga.setProgress(1);

                lblLiebre.setText("Liebre: 100 %");
                lblTortuga.setText("Tortuga: 100 %");

                lblEstado.setText(
                        "¡La tortuga ganó! La liebre se quedó dormida."
                );

                carreraEnCurso = false;
                btnIniciar.setDisable(false);
            });
        }
    }
}
