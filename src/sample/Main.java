package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Resource");
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"), resourceBundle);
        primaryStage.setTitle("JCommander");
        primaryStage.setScene(new Scene(root, 1250, 700));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
