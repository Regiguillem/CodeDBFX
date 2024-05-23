package com.example.codedbmastersfx.controlador;

import com.example.codedbmastersfx.modelo.ExcursionesModelo;
import com.example.codedbmastersfx.modelo.ExcursionesModeloDAO;
import com.example.codedbmastersfx.util.HibernateUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Node;
import org.hibernate.Session;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ExcursionesController {

    private static ExcursionesModeloDAO exDAO;
    private Session session;

    @FXML
    private TextField codigoField;

    @FXML
    private TextField descripcionField;

    @FXML
    private TextField fechaField;

    @FXML
    private TextField nDiasField;

    @FXML
    private TextField precioField;

    @FXML
    private Button volverButton;

    @FXML
    private DatePicker fechaInicioPicker;

    @FXML
    private DatePicker fechaFinPicker;

    @FXML
    private TextArea resultadosTextArea;

    public ExcursionesController(Session session) {
        this.session = session = HibernateUtil.getSessionFactory().openSession();
        this.exDAO = new ExcursionesModeloDAO(session);
    }

    public ExcursionesController() {
    }

    @FXML
    private void onAnadirExcursionClick(ActionEvent event) {
        cambiarEscena(event, "/com/example/codedbmastersfx/vista/addexcursion-view.fxml");
    }

    @FXML
    private void onMostrarExcursionesClick(ActionEvent event) {
        cambiarEscena(event, "/com/example/codedbmastersfx/vista/mostrarex-view.fxml");
        LocalDate fechaInicio = fechaInicioPicker.getValue();
        LocalDate fechaFin = fechaFinPicker.getValue();
        // Verificar que se han seleccionado ambas fechas
        if (fechaInicio != null && fechaFin != null) {
            // Filtrar las excursiones y mostrarlas en el TextArea
            List<ExcursionesModelo> excursiones = exDAO.filtrarExcursion(fechaInicio, fechaFin);
            mostrarExcursionesEnTextArea(excursiones);
        } else {
            mostrarAlerta("Error", "Por favor selecciona ambas fechas.");
        }
    }

    @FXML
    private void onVolverClick(ActionEvent event) {
        cambiarEscena(event, "/com/example/codedbmastersfx/vista/excursiones-view.fxml");
    }

    @FXML
    private void guardarExcursion() {
        String codigo = codigoField.getText();
        String descripcion = descripcionField.getText();
        LocalDate fecha = LocalDate.parse(fechaField.getText());
        int nDias = Integer.parseInt(nDiasField.getText());
        double precio = Double.parseDouble(precioField.getText());

        try {
            exDAO.agregarExcursion(new ExcursionesModelo(codigo, descripcion, fecha, nDias, precio));
            mostrarAlerta("Éxito", "Excursión añadida correctamente.");
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al añadir la excursión: " + e.getMessage());
        }
    }

    private void mostrarExcursionesEnTextArea(List<ExcursionesModelo> excursiones) {
        resultadosTextArea.clear(); // Limpiar el área de texto antes de agregar nuevos datos
        for (ExcursionesModelo excursion : excursiones) {
            System.out.println(excursion);
            resultadosTextArea.appendText(
                    "Código: " + excursion.getCodigo() + "\n" +
                            "Descripción: " + excursion.getDescripcion() + "\n" +
                            "Fecha: " + excursion.getFecha() + "\n" +
                            "Número de días: " + excursion.getN_dias() + "\n" +
                            "Precio: " + excursion.getPrecio() + "\n\n"
            );
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void cambiarEscena(ActionEvent event, String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
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

    public void setSession(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }
}
