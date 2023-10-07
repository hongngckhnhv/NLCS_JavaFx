module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.demo to javafx.fxml;
    opens Controller to javafx.fxml;
    opens Model to javafx.fxml;
    opens View to javafx.fxml;

    exports com.example.demo;
    exports Model;
    exports Controller;
    exports View;


    requires com.rabbitmq.client;
    requires mysql.connector.j;
    requires com.oracle.database.jdbc;
    requires java.sql;
}