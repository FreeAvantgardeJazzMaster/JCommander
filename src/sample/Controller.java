package sample;


import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuBar;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;


public class Controller {

    private Locale locale;
    private ResourceBundle resourceBundle;

    @FXML private Menu menuFile;
    @FXML private Menu menuEdit;
    @FXML private Menu menuHelp;
    @FXML private MenuItem menuItemClose;
    @FXML private Menu menuHelpLang;
    @FXML private CheckMenuItem checkMenuItemPL;
    @FXML private CheckMenuItem checkMenuItemEN;


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
        if (Locale.getDefault().toString().contains("PL"))
            checkMenuItemPL.setSelected(true);
        else if (Locale.getDefault().toString().contains("EN"))
            checkMenuItemEN.setSelected(true);
    }
    private void loadLang(String lang){
        locale = new Locale(lang);
        resourceBundle = ResourceBundle.getBundle("Resource", locale);
        menuFile.setText(resourceBundle.getString("menuFile"));
        menuEdit.setText(resourceBundle.getString("menuEdit"));
        menuHelp.setText(resourceBundle.getString("menuHelp"));
        menuItemClose.setText(resourceBundle.getString("menuItemClose"));
        menuHelpLang.setText(resourceBundle.getString("menuHelpLang"));


    }

}
