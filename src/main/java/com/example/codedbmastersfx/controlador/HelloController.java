package com.example.codedbmastersfx.controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private TextArea statusTextArea;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onIniciarButtonClick() throws IOException {
        Stage stage = (Stage) welcomeText.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/codedbmastersfx/vista/hello-view.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    public void appendStatusMessage(String message) {
        statusTextArea.appendText(message + "\n");
    }
}