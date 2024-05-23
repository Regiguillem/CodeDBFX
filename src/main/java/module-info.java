module com.example.codedbmastersfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.naming;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.codedbmastersfx.controlador to javafx.fxml, org.hibernate.orm.core;
    opens com.example.codedbmastersfx.vista to javafx.fxml;
    opens com.example.codedbmastersfx.modelo to org.hibernate.orm.core;

    exports com.example.codedbmastersfx.modelo;
    exports com.example.codedbmastersfx.controlador;
    exports com.example.codedbmastersfx.vista;
    exports com.example.codedbmastersfx;
    opens com.example.codedbmastersfx to javafx.fxml;
}