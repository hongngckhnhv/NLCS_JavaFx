package View;

import com.example.demo.HelloApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Sign extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Sign.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            primaryStage.setTitle("CAFE SHOP MANAGEMENT SYSTEM");
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setResizable(false);
//            database db = new database();
//            db.connectDB();

        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
