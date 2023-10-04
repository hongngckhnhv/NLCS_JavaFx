module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.demo to javafx.fxml;
    opens SignInUp to javafx.fxml;
    opens main to javafx.fxml;

    exports com.example.demo;
    exports SignInUp;
    exports main;
    exports connect;

    requires com.rabbitmq.client;
    requires mysql.connector.j;
    requires com.oracle.database.jdbc;
    requires java.sql;
}