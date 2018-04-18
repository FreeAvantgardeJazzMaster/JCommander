package sample;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.util.*;


public class Controller {

    private Locale locale;
    private ResourceBundle resourceBundle;

    private String leftCurrentPath;
    private String rightCurrentPath;

    private int leftChoiceBoxLastSelected;
    private int rightChoiceBoxLastSelected;

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
    @FXML private TableColumn leftTableColumnName;
    @FXML private TableColumn leftTableColumnSize;
    @FXML private TableColumn leftTableColumnDate;
    @FXML private TableColumn rightTableColumnName;
    @FXML private TableColumn rightTableColumnSize;
    @FXML private TableColumn rightTableColumnDate;
    @FXML private TableView<FileObject> rightTableView;
    @FXML private TableView<FileObject> leftTableView;

    public void MenuItemFileClose_OnClick(ActionEvent event){
        Platform.exit();
    }

    public void setLanguage(ActionEvent event){
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

    @FXML
    public void initialize() {
        selectLanguage();
        listRoots();
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
        rightTableColumnName.setText(resourceBundle.getString("tableColumnName"));
        rightTableColumnSize.setText(resourceBundle.getString("tableColumnSize"));
        rightTableColumnDate.setText(resourceBundle.getString("tableColumnDate"));
    }

    private void selectLanguage(){
        if (Locale.getDefault().toString().contains("PL"))
            checkMenuItemPL.setSelected(true);
        else if (Locale.getDefault().toString().contains("EN"))
            checkMenuItemEN.setSelected(true);
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

    private void readAndDisplayPath(String root, TableView<FileObject> tableView){
        List<FileObject> fileObjectList = new ArrayList<>();
        File file = new File(root);
        File[] files = file.listFiles();
        for (File enrtyFile : files){
            if(!enrtyFile.isDirectory())
                fileObjectList.add(new FileObject(enrtyFile.getName(), enrtyFile.length()+"", new Date(enrtyFile.lastModified() * 1000)));
            else
                fileObjectList.add(new FileObject(enrtyFile.getName(), "<dir>", new Date(enrtyFile.lastModified() * 1000)));
        }
        tableView.getItems().clear();
        for (FileObject fo : fileObjectList){
            tableView.getItems().add(fo);
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

    public void leftTableView_OnMouseClicked(){
        leftTableView.getSelectionModel().getSelectedItem();
    }

}
