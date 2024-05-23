package com.example.codedbmastersfx.controlador;

import org.hibernate.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

import java.io.IOException;

public class MainController {

    private Session session;

    public void setSession(Session session) {
        this.session = session;
    }

    @FXML
    private Button volverButton;

    @FXML
    private void onVolverClick(ActionEvent event) {
        cambiarEscena(event, "/com/example/codedbmastersfx/vista/main-view.fxml");
    }

    @FXML
    private void onExcursionesClick(ActionEvent event) throws IOException {
        cambiarEscena(event, "/com/example/codedbmastersfx/vista/excursiones-view.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/codedbmastersfx/vista/excursiones-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

    }

    @FXML
    private void onSociosClick() {
        showAlert("Controlador Socios", "Funcionalidad para el controlador de Socios.");
        // Aquí puedes agregar el código para cambiar a la escena del controlador de Socios si es necesario
    }

    @FXML
    private void onInscripcionesClick() {
        showAlert("Controlador Socios", "Funcionalidad para el controlador de Socios.");
        // Aquí puedes agregar el código para cambiar a la escena del controlador de Socios si es necesario
    }

    private void cambiarEscena(ActionEvent event, String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);

            // Pasar la sesión al nuevo controlador si es necesario
            Object controller = fxmlLoader.getController();
            if (controller instanceof ExcursionesController) {
                ((ExcursionesController) controller).setSession(session);
            }

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo cargar la vista: " + fxmlFile);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
