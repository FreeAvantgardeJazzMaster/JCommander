package sample;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;


public class Controller {

    private Locale locale;
    private ResourceBundle resourceBundle;

    private String leftCurrentPath;
    private String rightCurrentPath;

    private int leftChoiceBoxLastSelected;
    private int rightChoiceBoxLastSelected;

    private Task copyTask;
    private Task deleteTask;

    private Stage waitingBoxStage;

    @FXML private Menu menuFile;
    @FXML private Menu menuHelp;
    @FXML private MenuItem menuItemClose;
    @FXML private Menu menuHelpLang;
    @FXML private Button leftMenuCopy;
    @FXML private Button leftMenuMove;
    @FXML private Button leftMenuDelete;
    @FXML private Button rightMenuCopy;
    @FXML private Button rightMenuMove;
    @FXML private Button rightMenuDelete;
    @FXML private CheckMenuItem checkMenuItemPL;
    @FXML private CheckMenuItem checkMenuItemEN;
    @FXML private ChoiceBox leftChoiceBox;
    @FXML private ChoiceBox rightChoiceBox;
    @FXML private TableColumn<FileObject, String> leftTableColumnName;
    @FXML private TableColumn<FileObject, Long> leftTableColumnSize;
    @FXML private TableColumn<FileObject, Date> leftTableColumnDate;
    @FXML private TableColumn<FileObject, String> leftTableColumnType;
    @FXML private TableColumn<FileObject, String> rightTableColumnName;
    @FXML private TableColumn<FileObject, Long> rightTableColumnSize;
    @FXML private TableColumn<FileObject, Date> rightTableColumnDate;
    @FXML private TableColumn<FileObject, String> rightTableColumnType;
    @FXML private TableView<FileObject> rightTableView;
    @FXML private TableView<FileObject> leftTableView;
    @FXML private Button leftUpButton;
    @FXML private Button rightUpButton;
    @FXML private TextField leftCurrentPathField;
    @FXML private TextField rightCurrentPathField;


    @FXML
    public void initialize() {
        leftTableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        leftTableColumnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        leftTableColumnSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        leftTableColumnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        rightTableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        rightTableColumnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        rightTableColumnSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        rightTableColumnType.setCellValueFactory(new PropertyValueFactory<>("type"));

        leftTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        rightTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        selectDefaultLanguage();
        listRoots();
    }

    public void menuItemFileClose_onAction(ActionEvent event){
        Platform.exit();
    }

    public void checkMenuItem_onAction(ActionEvent event){
        CheckMenuItem c = (CheckMenuItem) event.getSource();
        if(c.getId().toString().equals("checkMenuItemPL")){
            checkMenuItemEN.setSelected(false);
            checkMenuItemPL.setSelected(true);
            loadLang("PL");
        }
        else{
            checkMenuItemEN.setSelected(true);
            checkMenuItemPL.setSelected(false);
            loadLang("EN");
        }
    }

    private void loadLang(String lang){
        locale = new Locale(lang);
        resourceBundle = ResourceBundle.getBundle("Resource", locale);
        menuFile.setText(resourceBundle.getString("menuFile"));
        menuHelp.setText(resourceBundle.getString("menuHelp"));
        menuItemClose.setText(resourceBundle.getString("menuItemClose"));
        menuHelpLang.setText(resourceBundle.getString("menuHelpLang"));
        leftMenuCopy.setText(resourceBundle.getString("leftMenuCopy"));
        leftMenuMove.setText(resourceBundle.getString("leftMenuMove"));
        leftMenuDelete.setText(resourceBundle.getString("leftMenuDelete"));
        rightMenuCopy.setText(resourceBundle.getString("rightMenuCopy"));
        rightMenuMove.setText(resourceBundle.getString("rightMenuMove"));
        rightMenuDelete.setText(resourceBundle.getString("rightMenuDelete"));
        leftTableColumnName.setText(resourceBundle.getString("tableColumnName"));
        leftTableColumnSize.setText(resourceBundle.getString("tableColumnSize"));
        leftTableColumnDate.setText(resourceBundle.getString("tableColumnDate"));
        leftTableColumnType.setText(resourceBundle.getString("tableColumnType"));
        rightTableColumnName.setText(resourceBundle.getString("tableColumnName"));
        rightTableColumnSize.setText(resourceBundle.getString("tableColumnSize"));
        rightTableColumnDate.setText(resourceBundle.getString("tableColumnDate"));
        rightTableColumnType.setText(resourceBundle.getString("tableColumnType"));
    }

    private void selectDefaultLanguage(){
        if (Locale.getDefault().toString().contains("PL")){
            checkMenuItemPL.setSelected(true);
            loadLang("PL");
        }
        else if (Locale.getDefault().toString().contains("EN")){
            checkMenuItemEN.setSelected(true);
            loadLang("EN");
        }
    }

    private void listRoots(){
        ObservableList<String> rootsList = FXCollections.observableArrayList();
        File[] roots = File.listRoots();
        for (int i = 0; i < roots.length; i++){
            rootsList.add(roots[i].toString());
        }
        leftChoiceBox.setItems(rootsList);
        rightChoiceBox.setItems(rootsList);
        leftChoiceBox.getSelectionModel().selectFirst();
        rightChoiceBox.getSelectionModel().selectFirst();
    }

    private void readAndDisplayPath(String path, TableView<FileObject> tableView){
        List<FileObject> fileObjectList = new ArrayList<>();
        File file = new File(path);
        File[] files = file.listFiles();
        for (File entryFile : files){
            if(!entryFile.isDirectory())
                fileObjectList.add(new FileObject(entryFile.getName(), new Long(entryFile.length()), new Date(entryFile.lastModified() * 1000), FilenameUtils.getExtension(entryFile.getName())));
            else
                fileObjectList.add(new FileObject(entryFile.getName(),  null ,new Date(entryFile.lastModified() * 1000), "<dir>"));
        }
        tableView.getItems().clear();
        for (FileObject fo : fileObjectList){
            tableView.getItems().add(fo);
        }
        try {
            if (tableView == leftTableView) {
                leftCurrentPath = file.getCanonicalPath() + "\\";
                leftCurrentPathField.setText(leftCurrentPath);
            } else {
                rightCurrentPath = file.getCanonicalPath() + "\\";
                rightCurrentPathField.setText(rightCurrentPath);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void leftChoiceBox_OnAction(ActionEvent event){
        try{
            readAndDisplayPath(leftChoiceBox.getSelectionModel().getSelectedItem().toString(), leftTableView);
            leftChoiceBoxLastSelected = leftChoiceBox.getSelectionModel().getSelectedIndex();
        }catch(Exception e) {
            e.printStackTrace();
            try {
                showReadErrorMessage(leftChoiceBox.getSelectionModel().getSelectedItem().toString());
            }catch (Exception ex){
                ex.printStackTrace();
            }
            leftChoiceBox.getSelectionModel().select(leftChoiceBoxLastSelected);
        }
    }

    public void rightChoiceBox_OnAction(ActionEvent event){
        try{
            readAndDisplayPath(rightChoiceBox.getSelectionModel().getSelectedItem().toString(), rightTableView);
            rightChoiceBoxLastSelected = rightChoiceBox.getSelectionModel().getSelectedIndex();
        }catch(Exception e) {
            e.printStackTrace();
            try {
                showReadErrorMessage(rightChoiceBox.getSelectionModel().getSelectedItem().toString());
            }catch (Exception ex){
                ex.printStackTrace();
            }
            rightChoiceBox.getSelectionModel().select(rightChoiceBoxLastSelected);
        }
    }

    private void showReadErrorMessage(String path) throws Exception{
        Stage errorStage = new Stage();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Resource");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("readError.fxml"));
        loader.setResources(resourceBundle);
        loader.load();
        ErrorController errorController = loader.getController();
        errorController.setPathInLabel(path);
        Parent root = loader.getRoot();
        errorStage.setTitle(resourceBundle.getString("error"));
        errorStage.setScene(new Scene(root));
        errorStage.show();
    }

    public void leftTableView_OnMouseClicked(MouseEvent event){
        if(event.getClickCount() == 2){
            String name = leftTableView.getSelectionModel().getSelectedItem().getName();
            String path = leftCurrentPath + name;
            readAndDisplayPath(path, leftTableView);
        }
    }

    public void rightTableView_OnMouseClicked(MouseEvent event){
        if(event.getClickCount() == 2){
            String name = rightTableView.getSelectionModel().getSelectedItem().getName();
            String path = rightCurrentPath + name ;
            readAndDisplayPath(path, rightTableView);
        }
    }

    public void leftUpButton_onAction(){
        String path = leftCurrentPath + "..";
        readAndDisplayPath(path, leftTableView);
    }

    public void rightUpButton_onAction(){
        String path = rightCurrentPath + "..";
        readAndDisplayPath(path, rightTableView);
    }

    public void leftCopyButton_onAction(){
        copyFile(leftTableView, leftCurrentPath, rightCurrentPath);
    }

    public void leftDeleteButton_onAction(){
        deleteFile(leftTableView, leftCurrentPath);
    }

    private void copyFile(TableView tableView, String leftCurrentPath, String rightCurrentPath){
        FileObject fileObject;
        if ((fileObject = (FileObject) tableView.getSelectionModel().getSelectedItem()) != null){
            File source = new File(leftCurrentPath + fileObject.getName());
            File dest = new File(rightCurrentPath + fileObject.getName());
            try {
                if(!source.getCanonicalPath().toString().equals(dest.getCanonicalPath().toString())){
                    if(isFileAlreadyThere(fileObject, new File(rightCurrentPath))) {
                        String result = overwriteOrRename(fileObject);
                        if (result.equals("O")) {
                            copy(source, dest);
                        } else if (result.equals("R")) {
                            dest = getUniqueFilePath(fileObject, rightCurrentPath);
                            copy(source, dest);
                        }
                    }else {
                        copy(source, dest);
                    }
                }
                else{
                    dest = getUniqueFilePath(fileObject, rightCurrentPath);
                    copy(source, dest);
                }
            }catch(IOException e){
              e.printStackTrace();
            }
        }
    }

    private void copy(File source, File dest){
        try {
            showWaitingBox(0);
        }catch (Exception e){
            e.printStackTrace();
        }
        copyTask = new Task() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(2000);
                if (!source.isDirectory()) {
                        FileUtils.copyFile(source, dest, true);
                    } else {
                        FileUtils.copyDirectory(source, dest, true);
                    }
                return null;
            }
        };
        new Thread(copyTask).start();

        copyTask.setOnSucceeded(e -> {
            waitingBoxStage.close();
            refreshTableViews();
        });

        copyTask.setOnCancelled(e -> {
            refreshTableViews();
        });
    }

    private void deleteFile(TableView tableView, String currentPath){
        FileObject fileObject;
        if ((fileObject = (FileObject) tableView.getSelectionModel().getSelectedItem()) != null){
            File source = new File(currentPath + fileObject.getName());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(resourceBundle.getString("alertDeleteTitle"));
            alert.setHeaderText(resourceBundle.getString("alertDeleteHeader"));
            alert.setContentText(resourceBundle.getString("alertDeleteContent") + "\n" + fileObject.getName());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                    delete(source);
            }
        }
    }

    private void delete(File source){
        deleteTask = new Task() {
            @Override
            protected Void call() throws Exception {
                if (!source.isDirectory()) {
                    FileUtils.forceDelete(source);
                } else {
                    FileUtils.deleteDirectory(source);
                }
                Thread.sleep(1000);
                return null;
            }
        };
        new Thread(deleteTask).start();

        deleteTask.setOnSucceeded(e -> {
            refreshTableViews();
        });
    }

    private void refreshTableViews() {
        readAndDisplayPath(leftCurrentPath, leftTableView);
        readAndDisplayPath(rightCurrentPath, rightTableView);
    }

    private File getUniqueFilePath(FileObject fileObject, String destPath){
        String newFileName = fileObject.getName();
        File destFile = new File(destPath);
        File[] files = destFile.listFiles();
        boolean unigue;
        int i = 1;
        do{
            unigue = true;
            for (File file : files){
                if (newFileName.equals(file.getName())){
                    newFileName = "(" + i + ")" + fileObject.getName();
                    unigue = false;
                }
            }
            i++;
        }while(!unigue);
        return new File(destPath + newFileName);
    }

    private String overwriteOrRename(FileObject fileObject){
        String res;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(resourceBundle.getString("overwriteTitle"));
        alert.setHeaderText(resourceBundle.getString("overwriteHeader"));
        alert.setContentText(resourceBundle.getString("overwriteContent") + "\n" + fileObject.getName());

        ButtonType buttonTypeOne = new ButtonType("Overwrite");
        ButtonType buttonTypeTwo = new ButtonType("Rename");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
            res = "O";
        } else if (result.get() == buttonTypeTwo) {
            res = "R";
        } else {
            res = "C";
        }
        return  res;
    }

    private boolean isFileAlreadyThere(FileObject fileObject, File dest){
        boolean isThere = false;
        String fileName = fileObject.getName();
        File[] files = dest.listFiles();
        for (File file : files){
            if (fileName.equals(file.getName())){
                isThere = true;
            }
        }
        return isThere;
    }

    //_____________________
    //taskTypes:
    //0 - copy
    //1 - delete
    //2 - move
    //_____________________
    private void showWaitingBox(int taskType) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("waitingBox.fxml"));
        loader.setResources(resourceBundle);
        loader.load();

        waitingBoxStage = new Stage();
        waitingBoxStage.setTitle(resourceBundle.getString("waitingBoxTitle"));
        waitingBoxStage.setScene(new Scene(loader.getRoot()));
        waitingBoxStage.show();

        WaitingBox waitingBox = loader.getController();

        waitingBox.setController(this);
        waitingBox.setTaskType(taskType);

        String label = null;
        switch (taskType){
            case 0:
                label = resourceBundle.getString("waitingBoxLabelCopy");
                break;
            case 1:
                label = resourceBundle.getString("waitingBoxLabelDelete");
                break;
            case 2:
                label = resourceBundle.getString("waitingBoxLabelMove");
                break;
        }

        waitingBox.getWaitingBoxLabel().setText(label);
        waitingBox.getWaitingBoxCancelButton().setText(resourceBundle.getString("waitingBoxCancelButton"));
    }

    public void cancelTask(int taskType){
        if (taskType == 0)
            copyTask.cancel();
        else if (taskType == 1)
            deleteTask.cancel();
        //else if(taskType == 2)
                //moveTask.cancel();

    }
}
