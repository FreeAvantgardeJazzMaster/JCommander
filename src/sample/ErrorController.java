package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

import java.util.ResourceBundle;

public class ErrorController {

    @FXML
    private Button readErrorOkButton;
    @FXML
    private Label detailsLabel;

    public void setReadErrorOkButton_OnAction(ActionEvent event){
        Stage stage = (Stage) readErrorOkButton.getScene().getWindow();
        stage.close();
    }

    public void setPathInLabel(String failedPath){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Resource");
        detailsLabel.setText(resourceBundle.getString("readErrorDetails") + " " + failedPath);

    }
}
