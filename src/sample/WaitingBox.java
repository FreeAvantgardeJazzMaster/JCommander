package sample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;


public class WaitingBox {


    @FXML
    private ProgressBar waitingBoxBar;

    @FXML
    private Button waitingBoxCancelButton;

    @FXML
    private Label waitingBoxLabel;

    private Controller controller;

    private int taskType;

    @FXML
    void initialize() {

    }

    public Label getWaitingBoxLabel() {
        return waitingBoxLabel;
    }

    public Button getWaitingBoxCancelButton() {
        return waitingBoxCancelButton;
    }

    public ProgressBar getWaitingBoxBar() {
        return waitingBoxBar;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }


    public void setWaitingBoxCancelButton_onAction(ActionEvent actionEvent){
        getController().cancelTask(getTaskType());
        Stage stage = (Stage) waitingBoxCancelButton.getScene().getWindow();
        stage.close();
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }
}
