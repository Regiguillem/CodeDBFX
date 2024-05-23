package com.example.codedbmastersfx;

import com.example.codedbmastersfx.controlador.ExcursionesController;
import com.example.codedbmastersfx.controlador.MainController;
import com.example.codedbmastersfx.modelo.ExcursionesModeloDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.example.codedbmastersfx.util.HibernateUtil;

import java.io.IOException;

public class HelloApplication extends Application {
    private Session session;

    @Override
    public void start(Stage stage) throws IOException {
        // Iniciar sesión de Hibernate
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/codedbmastersfx/vista/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        // Pasar sesión al controlador principal
        MainController mainController = fxmlLoader.getController();
        mainController.setSession(session);

        // Pasar sesión al controlador de Excursiones
        ExcursionesController excursionesController = new ExcursionesController(session);
        excursionesController.setSession(session);

        ExcursionesModeloDAO excursionesModeloDAO = new ExcursionesModeloDAO(session);
        excursionesModeloDAO.setSession(session);

        stage.setTitle("CodeDBFX");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        // Cerrar la sesión de Hibernate cuando se cierra la aplicación
        if (session != null) {
            session.close();
        }
        HibernateUtil.shutdown();
    }

    public static void main(String[] args) {
        launch();
    }
}
